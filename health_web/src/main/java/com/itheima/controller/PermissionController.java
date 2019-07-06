package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Permission;
import com.itheima.service.PermissionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("permission")
public class PermissionController {
    @Reference
    private PermissionService permissionService;

    @PostMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            return permissionService.findPage(queryPageBean);
        } catch (Exception e) {
            System.out.println(e);
            return new PageResult(0L, null);
        }
    }

    @GetMapping("findAll")
    public Result findAll() {
        try {
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, permissionService.findAll());
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
        }

    }

    @DeleteMapping("delete")
    public Result delete(Integer id) {
        try {
            permissionService.delete(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_PERMISSION_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_PERMISSION_SUCCESS);
    }

    @PutMapping("edit")
    public Result edit(@RequestBody Permission permission) {
        try {
            permissionService.edit(permission);
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.EDIT_PERMISSION_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_PERMISSION_SUCCESS);
    }
    @PostMapping("add")
    public Result add(@RequestBody Permission permission) {
        try {
            permissionService.add(permission);
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.ADD_PERMISSION_FAIL);
        }
        return new Result(true, MessageConstant.ADD_PERMISSION_SUCCESS);
    }
}
