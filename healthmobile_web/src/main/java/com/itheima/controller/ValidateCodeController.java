package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @PostMapping("send4Order")
    public Result send4Order(String telephone) {
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        Jedis resource = jedisPool.getResource();
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code.toString());
            System.out.println("发送的手机验证码为：" + code);
            //将生成的验证码缓存到redis
            resource.setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 5 * 60, code.toString());
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        } finally {
            resource.close();
        }
    }

    @PostMapping("send4Login")
    public Result send4Login(String telephone) {
        Integer code = ValidateCodeUtils.generateValidateCode(4);
        Jedis resource = jedisPool.getResource();
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code.toString());
            System.out.println("发送的手机验证码为：" + code);
            //将生成的验证码缓存到redis
            resource.setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN, 5 * 60, code.toString());
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        } finally {
            resource.close();
        }
    }
}
