package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.RoleDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public void add(Role role, Integer[] permissionIds) {
        roleDao.add(role);
        setRoleAndPermission(role.getId(), permissionIds);
    }

    private void setRoleAndPermission(Integer id, Integer[] permissionIds) {
        if (permissionIds != null && permissionIds.length > 0) {
            for (Integer permissionId : permissionIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("role_id", id);
                map.put("permission_id", permissionId);
                roleDao.setRoleAndPermission(map);
            }
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Role> list = roleDao.findPage(queryPageBean.getQueryString());
        return new PageResult(list.getTotal(), list.getResult());
    }

    @Override
    public void delete(Integer id) {
        Long countByRoleId = roleDao.findCountByRoleId(id);
        if (countByRoleId > 0) {
            //当前检查项被引用，不能删除
            throw new RuntimeException("当前角色被引用，不能删除");
        }
        roleDao.delete(id);
        roleDao.deleteByRoleId(id);
    }

    @Override
    public void edit(Role role, Integer[] permissionIds) {
        roleDao.deleteByRoleId(role.getId());
        roleDao.edit(role);
        setRoleAndPermission(role.getId(), permissionIds);
    }

    @Override
    public List<Integer> findPermissionIdsByRoleId(Integer id) {
        List<Integer> list = roleDao.findPermissionIdsByRoleId(id);
        return list;
    }
}
