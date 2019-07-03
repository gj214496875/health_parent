package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckGroup;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(Map<String, Integer> map);

    Page<CheckGroup> findPage(String queryString);

    void delete(Integer id);

    void edit(CheckGroup checkGroup);

    void deleteByCheckGroupId(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
}
