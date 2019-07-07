package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Menu;

import java.util.List;

public interface MenuService {
    PageResult findPage(QueryPageBean queryPageBean);

    List<Menu> findAll();

    List<String> findIcon();

    void add(Menu menu);

    void delete(Integer id,Integer parentMenuId);

    void edit(Menu menu);
}
