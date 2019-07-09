package com.itheima.service;

import com.itheima.entity.Result;

import java.util.Map;

public interface OrderService {
    Result order(Map orderInfo) throws Exception;

    Result findById4Detail(Integer id) throws Exception;

    Integer findOrderCountBySetmealId(Integer id);
}
