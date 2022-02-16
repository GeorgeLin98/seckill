package com.george.seckill.good;

import com.george.seckill.api.cache.service.IRedisService;
import com.george.seckill.api.cache.util.CacheUtil;
import com.george.seckill.api.good.util.GoodUtil;
import com.george.seckill.api.good.pojo.GoodDetailVO;
import com.george.seckill.api.good.pojo.GoodVO;
import com.george.seckill.api.good.service.IGoodService;
import com.george.seckill.api.user.pojo.UserPO;
import com.george.seckill.api.user.service.IUserService;
import com.george.seckill.pojo.ResponseVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * @description 商品controller
 * @author linzhuangze
 * @date 2021.01.13
 */
@Controller
@RequestMapping("/goods/")
public class GoodController {
    //RPC调用
    @Reference(interfaceClass = IUserService.class)
    private IUserService userService;
    //RPC调用
    @Reference(interfaceClass = IGoodService.class)
    private IGoodService goodService;
    //因为在redis缓存中不存页面缓存时需要手动渲染，所以注入一个视图解析器，自定义渲染
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;
    @Reference(interfaceClass = IRedisService.class)
    IRedisService redisService;

    /**
     * @description 商品首页
     * @param user  通过自定义参数解析器UserArgumentResolver解析的 UserPO 对象
     * @return
     */
    @RequestMapping(value = "goodsList",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String goodsList(HttpServletRequest request,HttpServletResponse response, UserPO user, Model model) {
        // 从redis缓存中取html
        String html = redisService.get(GoodUtil.GOOD_LIST_KEY,String.class);
        if (!StringUtils.isEmpty(html)){
            return html;
        }
        // 查询商品列表，用于手动渲染时将商品数据填充到页面
        List<GoodVO> goodsVoList = goodService.listGoodsVo();
        model.addAttribute("goodsList", goodsVoList);
        model.addAttribute("user", user);
        //渲染html
        WebContext webContext = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        // (第一个参数为渲染的html文件名，第二个为web上下文：里面封装了web应用的上下文)
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", webContext);
        // 如果html文件不为空，则将页面缓存在redis中
        if (!StringUtils.isEmpty(html))
            redisService.set(GoodUtil.GOOD_LIST_KEY, html, CacheUtil.DEFAULT_CACHE_TIME);
        return html;
    }
    /**
     * @description 处理商品详情页
     * @param user
     * @param goodId
     * @return
     */
    @RequestMapping(value = "getDetails/{goodsId}")
    @ResponseBody
    public ResponseVO<GoodDetailVO> getDetails(UserPO user, @PathVariable("goodsId") long goodId) {
        // 通过商品id在数据库查询
        GoodVO goods = goodService.getGoodsVoByGoodsId(goodId);
        // 获取商品的秒杀开始与结束的时间
        long startDate = goods.getStartDate().getTime();
        long endDate = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        // 秒杀状态; 0: 秒杀未开始，1: 秒杀进行中，2: 秒杀已结束
        int skStatus = 0;
        // 秒杀剩余时间
        int remainSeconds = 0;
        // 秒杀未开始
        if (now < startDate) {
            skStatus = GoodUtil.SECOND_KILL_NOT_START;
            remainSeconds = (int) ((startDate - now) / 1000);
        // 秒杀已结束
        } else if (now > endDate) {
            skStatus = GoodUtil.SECOND_KILL_END;
            remainSeconds = GoodUtil.SECOND_KILL_END_TIME;
        // 秒杀进行中
        } else {
            skStatus = GoodUtil.SECOND_KILLING;
            remainSeconds = GoodUtil.SECOND_KILLING_TIME;
        }
        // 服务端封装商品数据直接传递给客户端，而不用渲染页面
        GoodDetailVO goodDetailVO = new GoodDetailVO();
        goodDetailVO.setGoods(goods);
        goodDetailVO.setUser(user);
        goodDetailVO.setRemainSeconds(remainSeconds);
        goodDetailVO.setSeckillStatus(skStatus);
        return ResponseVO.success(goodDetailVO);
    }

}
