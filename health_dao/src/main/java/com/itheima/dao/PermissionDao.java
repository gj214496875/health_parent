package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionDao {
    Set<Permission> findByRoleId(Integer roleId);

    Page<Permission> findPage(String queryString);

    List<Permission> findAll();

    void edit(Permission permission);

    void add(Permission permission);

    Long findCountByPermissionId(Integer id);

    void delete(Integer id);
}
