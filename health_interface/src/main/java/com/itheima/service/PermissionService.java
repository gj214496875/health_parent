package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Permission;

import java.util.List;

public interface PermissionService {
    PageResult findPage(QueryPageBean queryPageBean);

    void add(Permission permission);

    void edit(Permission permission);

    void delete(Integer id);

    List<Permission> findAll();
}
