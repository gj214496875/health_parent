package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        Page<Setmeal> page = setmealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void delete(Integer id) {
        setmealDao.deleteBySetmealId(id);
        setmealDao.delete(id);
    }

    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        Integer id = setmeal.getId();
        setmealDao.deleteBySetmealId(id);
        setCheckGroupAndSetMeal(id, checkgroupIds);
        setmealDao.edit(setmeal);
    }

    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(Integer id) {
        return setmealDao.findCheckGroupIdsBySetmealId(id);
    }

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        setCheckGroupAndSetMeal(setmeal.getId(), checkgroupIds);
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findById(String id) {
        return setmealDao.findById(id);
    }

    //设置检查组合和检查项的关联关系
    public void setCheckGroupAndSetMeal(Integer setmeal_id, Integer[] checkGroupIds) {
        if (checkGroupIds != null && checkGroupIds.length > 0) {
            for (Integer checkGroupId : checkGroupIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkgroup_id", checkGroupId);
                map.put("setmeal_id", setmeal_id);
                setmealDao.setCheckGroupAndSetMeal(map);
            }
        }
    }
}
