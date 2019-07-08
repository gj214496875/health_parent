package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("role")
public class RoleController {
    @Reference
    private RoleService roleService;

    @GetMapping("findAll")
    public Result findAll() {
        try {
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, roleService.findAll());
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
        }
    }

    @PostMapping("add")
    public Result add(@RequestBody Role role, Integer[] permissionIds, Integer[] menuIds) {
        try {
            roleService.add(role, permissionIds, menuIds);
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.ADD_ROLE_FAIL);
        }
        return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
    }

    @PostMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            return roleService.findPage(queryPageBean);
        } catch (Exception e) {
            System.out.println(e);
            return new PageResult(0L, null);
        }

    }

    @DeleteMapping("delete")
    public Result delete(Integer id) {
        try {
            roleService.delete(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_ROLE_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
    }

    @PutMapping("edit")
    public Result edit(@RequestBody Role role, Integer[] permissionIds) {
        try {
            roleService.edit(role, permissionIds);
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.EDIT_ROLE_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
    }

    @GetMapping("findPermissionIdsByRoleId")
    public Result findPermissionIdsByRoleId(Integer id) {
        try {
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, roleService.findPermissionIdsByRoleId(id));
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
        }

    }
}
