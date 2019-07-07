package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    List<User> findAll();

    void add(User user, Integer[] roleIds);

    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    void edit(User user, Integer[] roleIds);

    List<Integer> findRoleIdsByUserId(Integer id);

}
