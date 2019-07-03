package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckItemDao {

    void add(CheckItem checkItem);

    Page<CheckItem> findList(String queryString);

    void delete(Integer id);

    void edit(CheckItem checkItem);

    Long findCountByCheckItemId(Integer checkitem_id);
}
