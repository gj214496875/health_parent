package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    @PostMapping("upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) {
        try {
            //读取Excel文件数据
            List<String[]> list = POIUtils.readExcel(excelFile);
            if (list != null && list.size() > 0) {
                List<OrderSetting> orderSettingList = new ArrayList<>();
                for (String[] strings : list) {
                    OrderSetting orderSetting =
                            new OrderSetting(new Date(strings[0]), Integer.parseInt(strings[1]));
                    orderSettingList.add(orderSetting);
                }
                orderSettingService.add(orderSettingList);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }

    @GetMapping("getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date) {
        try {
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, orderSettingService.getOrderSettingByMonth(date));
        } catch (Exception e) {
            return new Result(true, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    @PostMapping("editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting) {
        try {
            orderSettingService.editNumberByDate(orderSetting);
        } catch (Exception e) {
            return new Result(true, MessageConstant.ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }
}
