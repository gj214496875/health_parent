package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult findPage(QueryPageBean queryPageBean);

    void delete(Integer id);

    void edit(CheckGroup checkGroup,Integer[] checkitemIds);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    List<CheckGroup> findAll();
}
