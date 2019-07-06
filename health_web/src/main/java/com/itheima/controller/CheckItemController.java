package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")//权限校验
    @PostMapping("add")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")//权限校验
    @PostMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            return checkItemService.findPage(queryPageBean);
        } catch (Exception e) {
            System.out.println(e);
            return new PageResult(0L, null);
        }

    }

    @GetMapping("findAll")
    public Result findAll() {
        try {
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemService.findAll());
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }

    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")//权限校验
    @DeleteMapping("delete")
    public Result delete(Integer id) {
        try {
            checkItemService.delete(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")//权限校验
    @PutMapping("edit")
    public Result edit(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.edit(checkItem);
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

}
