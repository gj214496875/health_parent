package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Role;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleDao {
    Set<Role> findByUserId(Integer userId);

    List<Role> findAll();

    void add(Role role);

    void setRoleAndPermission(Map<String, Integer> map);

    Page<Role> findPage(String queryString);

    Long findCountByRoleId(Integer id);

    void delete(Integer id);

    void deleteByRoleId(Integer id);

    void edit(Role role);

    List<Integer> findPermissionIdsByRoleId(Integer id);
}
