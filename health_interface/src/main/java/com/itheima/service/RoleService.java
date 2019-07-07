package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    void add(Role role, Integer[] permissionIds);

    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    void edit(Role role, Integer[] permissionIds);

    List findPermissionIdsByRoleId(Integer id);
}
