package com.george.seckill.good;

import com.george.seckill.api.user.pojo.UserPO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @description 商品controller
 * @author linzhuangze
 * @date 2021.01.13
 */
@Controller
@RequestMapping("/goods/")
public class GoodController {
    /**
     * @description 商品首页
     * @param user  通过自定义参数解析器UserArgumentResolver解析的 UserPO 对象
     * @return
     */
    @RequestMapping(value = "goodsList")
    @ResponseBody
    public String goodsList(UserPO user,Model model) {
        model.addAttribute("user", user);
        return "goodList";
    }
}
