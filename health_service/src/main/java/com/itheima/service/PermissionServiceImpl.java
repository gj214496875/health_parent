package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PermissionDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Permission> list = permissionDao.findPage(queryPageBean.getQueryString());
        return new PageResult(list.getTotal(), list.getResult());
    }

    @Override
    public void add(Permission permission) {
        permissionDao.add(permission);
    }

    @Override
    public void edit(Permission permission) {
        permissionDao.edit(permission);
    }

    @Override
    public void delete(Integer id) {
        Long countByCheckItemId = permissionDao.findCountByPermissionId(id);
        if (countByCheckItemId > 0) {
            //当前检查项被引用，不能删除
            throw new RuntimeException("当前检查项被引用，不能删除");
        }
        permissionDao.delete(id);
    }

    @Override
    public List<Permission> findAll() {
        return permissionDao.findAll();
    }
}
