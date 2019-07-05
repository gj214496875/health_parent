package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderSettingDao {
    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(String date);

    OrderSetting findByOrderDate(Date orderDate);

    void editReservationsByOrderDate(OrderSetting orderSetting);
}
