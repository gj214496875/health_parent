package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.github.pagehelper.StringUtil;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    @PostMapping("submit")
    public Result submit(@RequestBody Map orderInfo) {
        //比对验证码
        String telephone = (String) orderInfo.get("telephone");
        String code = (String) orderInfo.get("validateCode");
        String s = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        if (StringUtil.isEmpty(s) || !s.equals(code)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        //预约服务
        Result result = null;
        try {
            orderInfo.put("orderType", Order.ORDERTYPE_WEIXIN);
            result = orderService.order(orderInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        if (result.isFlag()) {
            //预约成功，发送短信通知
            String orderDate = (String) orderInfo.get("orderDate");
            try {
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE, telephone, orderDate);
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @GetMapping("findById")
    public Result findById(Integer id) {
        Result result =null;
        try{
            result = orderService.findById4Detail(id);
            //查询预约信息成功
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,result.getData());
        }catch (Exception e){
            e.printStackTrace();
            //查询预约信息失败
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
