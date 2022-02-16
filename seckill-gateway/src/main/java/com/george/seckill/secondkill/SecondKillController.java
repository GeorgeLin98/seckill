package com.george.seckill.secondkill;

import com.george.seckill.api.cache.service.IRedisService;
import com.george.seckill.api.cache.util.CacheUtil;
import com.george.seckill.api.good.pojo.GoodVO;
import com.george.seckill.api.good.service.IGoodService;
import com.george.seckill.api.good.util.GoodUtil;
import com.george.seckill.api.mq.pojo.SeckillMessageVO;
import com.george.seckill.api.mq.service.IMqProviderService;
import com.george.seckill.api.order.pojo.OrderPO;
import com.george.seckill.api.order.service.IOrderService;
import com.george.seckill.api.order.util.OrderUtil;
import com.george.seckill.api.secondkill.service.ISecondKillService;
import com.george.seckill.api.user.pojo.UserPO;
import com.george.seckill.exception.GlobalException;
import com.george.seckill.pojo.ResponseVO;
import com.george.seckill.util.JsonUtil;
import com.george.seckill.util.MD5Util;
import com.george.seckill.util.MsgAndCodeEnum;
import com.george.seckill.util.UUIDUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

/**
 * @description 商品controller
 * @author linzhuangze
 * @date 2021.01.13
 */
@Controller
@RequestMapping("/seckill/")
public class SecondKillController implements InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(SecondKillController.class);

    @Reference(interfaceClass = IRedisService.class)
    IRedisService redisService;

    @Reference(interfaceClass = IGoodService.class)
    IGoodService goodsService;

    @Reference(interfaceClass = ISecondKillService.class)
    ISecondKillService secondKillService;

    @Reference(interfaceClass = IOrderService.class)
    IOrderService orderService;

    @Reference(interfaceClass = IMqProviderService.class)
    IMqProviderService mqProviderService;

    /**
     * @description 秒杀接口
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/{path}/doSeckill")
    @ResponseBody
    public ResponseVO<Integer> doSeckill(UserPO user, long goodsId,@PathVariable("path") String path,String verifyCode) {
        if(null == user){
            return ResponseVO.fail();
        }
        //验证地址是否正确
        boolean isPathTrue = secondKillService.checkPath(user,goodsId,path);
        if(!isPathTrue){
            throw new GlobalException(MsgAndCodeEnum.ERROR_PATH.getCode(),MsgAndCodeEnum.ERROR_PATH.getMsg());
        }
        //验证验证码是否正确
        boolean isCodeTrue = secondKillService.checkVerifyCode(user,goodsId,verifyCode);
        if(!isCodeTrue){
            throw new GlobalException(MsgAndCodeEnum.ERROR_CODE.getCode(),MsgAndCodeEnum.ERROR_CODE.getMsg());
        }
        //预减库存
        long stock = redisService.decr(String.format(GoodUtil.GOOD_STOCK_KEY, goodsId));
//        GoodVO goodVO = goodsService.getGoodsVoByGoodsId(goodsId);
//        if(goodVO.getStockCount() < 1){
//            throw new GlobalException(MsgAndCodeEnum.EMPTY_STOCK.getCode(),MsgAndCodeEnum.EMPTY_STOCK.getMsg());
//        }
        //如果库存已无，则加回去
        if(stock < 0){
            redisService.incr(String.format(GoodUtil.GOOD_STOCK_KEY, goodsId));
            throw new GlobalException(MsgAndCodeEnum.EMPTY_STOCK.getCode(),MsgAndCodeEnum.EMPTY_STOCK.getMsg());
        }
         //查看是否重复购买
//         boolean repeatBuy = orderService.judgeRepeatBuy(goodsId, user.getPhone());
        Integer isRepeat = redisService.get(String.format(OrderUtil.ORDER_KEY, user.getPhone(), goodsId), Integer.class);
        if(isRepeat!=null){
            throw new GlobalException(MsgAndCodeEnum.REPEAT_ERROR.getCode(),MsgAndCodeEnum.REPEAT_ERROR.getMsg());
        }
        //生产消息
        SeckillMessageVO seckillMessageVO = new SeckillMessageVO();
        seckillMessageVO.setUser(user);
        seckillMessageVO.setGoodsId(goodsId);
        String json = JsonUtil.beanToString(seckillMessageVO);
        mqProviderService.sendSeckillMessage(json);
        //秒杀
//        OrderPO orderPO = secondKillService.seckill(user,goodVO);
//        return ResponseVO.success(String.valueOf(orderPO.getId()));
        return ResponseVO.success(0);
    }

    /**
     * @description 返回秒杀结果： orderId：成功， -1：失败，0：排队中
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "result")
    @ResponseBody
    public ResponseVO<Long> getResult(UserPO user, long goodsId) {
        if(null == user){
            return ResponseVO.fail();
        }
        Long orderId = secondKillService.getResult(user,goodsId);
        return ResponseVO.success(orderId);
    }

    /**
     * @description 获取秒杀地址
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "path", method = RequestMethod.GET)
    @ResponseBody
    public ResponseVO<String> getPath(UserPO user, long goodsId) {
        if(null == user){
            return ResponseVO.fail();
        }
        String path = secondKillService.createPath(user, goodsId);
        // 向客户端回传随机生成的秒杀地址
        return ResponseVO.success(path);
    }

    /**
     * @description 获取验证码
     * @param response
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public void getVerifyCode(HttpServletResponse response, UserPO user,long goodsId) {
        if(null == user || goodsId < 0){
            throw new GlobalException(MsgAndCodeEnum.FAIL.getCode(),MsgAndCodeEnum.FAIL.getMsg());
        }
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        //数字类型,三个参数分别为宽、高、位数
        ArithmeticCaptcha specCaptcha = new ArithmeticCaptcha(130, 48,5);
        // 验证码存入redis
        redisService.set(String.format(GoodUtil.SECKILL_CAPTCHA_KEY,user.getPhone(),goodsId),specCaptcha.text(),CacheUtil.DEFAULT_CACHE_TIME);
        // 输出图片流
        try {
            specCaptcha.out(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 初始化库存
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodVO> goodVOS = goodsService.listGoodsVo();
        for(GoodVO vo:goodVOS){
            redisService.set(String.format(GoodUtil.GOOD_STOCK_KEY,vo.getId()),vo.getStockCount(), CacheUtil.DEFAULT_CACHE_TIME);
        }
    }
}
