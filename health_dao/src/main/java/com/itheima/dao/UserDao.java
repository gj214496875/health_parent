package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User findByUsername(String username);

    List<Integer> findRoleIdsByUserId(Integer id);

    void edit(User user);

    void deleteByUserId(Integer id);

    Page<User> findPage(String queryString);

    void setRoleAndUser(Map<String, Integer> map);

    void add(User user);

    List<User> findAll();

    void delete(Integer id);
}
