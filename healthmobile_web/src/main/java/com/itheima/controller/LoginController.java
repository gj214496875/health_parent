package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.StringUtil;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private MemberService memberService;

    @PostMapping("check")
    public Result check(HttpServletResponse response, @RequestBody Map map) {
        //验证验证码是否正确
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        String code = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (StringUtil.isEmpty(code) || !code.equals(code)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        } else {
            //根据手机号查询用户
            Member member = memberService.findByTelephone(telephone);
            if (member == null) {
                //当前用户不是会员，注册成会员
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.add(member);
            }
        }

        //登录成功
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cookie);
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
