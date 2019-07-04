package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SetmealDao {
    void setCheckGroupAndSetMeal(Map<String, Integer> map);

    void add(Setmeal setmeal);

    List<Integer> findCheckGroupIdsBySetmealId(Integer id);

    void deleteBySetmealId(Integer id);

    void edit(Setmeal setmeal);

    void delete(Integer id);

    Page<Setmeal> findPage(String queryString);
}
