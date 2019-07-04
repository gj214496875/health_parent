package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    void edit(Setmeal setmeal, Integer[] checkgroupIds);

    List<Integer> findCheckGroupIdsBySetmealId(Integer id);

    void add(Setmeal setmeal, Integer[] checkgroupIds);
}
