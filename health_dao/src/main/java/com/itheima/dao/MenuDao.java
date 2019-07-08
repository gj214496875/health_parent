package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Menu;

import java.util.List;

public interface MenuDao {
    Page<Menu> findPage(String queryString);

    List<Menu> findAll();

    List<String> findIcon();

    void add(Menu menu);

    void edit(Menu menu);

    long findCountById(Integer id);

    void deleteByMenuId(Integer id);

    void delete(Integer id);

    List<String> findUserMenu(String username);

    List<Integer> findMenuIdsByRoleId(Integer id);
}
