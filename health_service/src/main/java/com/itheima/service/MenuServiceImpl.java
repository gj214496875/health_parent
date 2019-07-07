package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MenuDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Menu> list = menuDao.findPage(queryPageBean.getQueryString());
        return new PageResult(list.getTotal(), list.getResult());
    }

    @Override
    public List<Menu> findAll() {

        return menuDao.findAll();
    }

    @Override
    public List<String> findIcon() {
        return menuDao.findIcon();
    }

    @Override
    public void add(Menu menu) {
        menuDao.add(menu);
    }

    @Override
    public void delete(Integer id, Integer parentMenuId) {
        if (parentMenuId == 0) {
            long count = menuDao.findCountById(id);
            if (count > 0) {
                throw new RuntimeException("当前菜单有子菜单，不能删除");
            }
        }
        menuDao.deleteByMenuId(id);
        menuDao.delete(id);
    }

    @Override
    public void edit(Menu menu) {
        menuDao.edit(menu);
    }
}
