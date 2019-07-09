package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;
    ;

    @Override
    public Result order(Map orderInfo) throws Exception {
        /*
         *1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
         *2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
         *3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
         *4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
         *5、预约成功，更新当日的已预约人数
         */
        //取出参数
        String date = (String) orderInfo.get("orderDate");
        Date orderDate = DateUtils.parseString2Date(date);
        int setmealId = Integer.parseInt((String) orderInfo.get("setmealId"));
        String telephone = (String) orderInfo.get("telephone");
        //判断是否能预约
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(orderDate);
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        if (orderSetting.getNumber() <= orderSetting.getReservations()) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        //判断是否为重复预约
        Member member = memberDao.findByTelephone(telephone);
        if (member != null) {
            Order order = new Order(member.getId(), orderDate, null, null, setmealId);
            List<Order> list = orderDao.findByCondition(order);
            if (list != null && list.size() > 0) {
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }
        //可以预约，设置预约人数加一
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);

        //当前用户不是会员，需要添加到会员表
        if (member == null) {
            member = new Member();
            member.setName((String) orderInfo.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) orderInfo.get("idCard"));
            member.setSex((String) orderInfo.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);
        }

        //保存预约信息到预约表
        Order order = new Order(member.getId(), orderDate, (String) orderInfo.get("orderType"), Order.ORDERSTATUS_NO, setmealId);
        orderDao.add(order);

        return new Result(true, MessageConstant.ORDER_SUCCESS, order);
    }

    @Override
    public Result findById4Detail(Integer id) throws Exception {
        Map map = orderDao.findById4Detail(id);
        if (map != null) {
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate", DateUtils.parseDate2String(orderDate));
            return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, map);
        }
        return new Result(false, MessageConstant.QUERY_ORDER_FAIL);
    }

    @Override
    public Integer findOrderCountBySetmealId(Integer id) {
        return orderDao.findOrderCountBySetmealId(id);
    }
}
