package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.StringUtil;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Reference
    private UserService userService;

    //获取当前登录用户的用户名
    @GetMapping("getUsername")
    public Result getUsername() throws Exception {
        try {
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User)
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, user.getUsername());
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    @GetMapping("findAll")
    public Result findAll() {
        try {
            return new Result(true, MessageConstant.QUERY_USER_SUCCESS, userService.findAll());
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.QUERY_USER_FAIL);
        }
    }

    @PostMapping("add")
    public Result add(@RequestBody User user, Integer[] roleIds) {
        try {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userService.add(user, roleIds);
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.ADD_USER_FAIL);
        }
        return new Result(true, MessageConstant.ADD_USER_SUCCESS);
    }

    @PostMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            return userService.findPage(queryPageBean);
        } catch (Exception e) {
            System.out.println(e);
            return new PageResult(0L, null);
        }

    }

    @DeleteMapping("delete")
    public Result delete(Integer id) {
        try {
            userService.delete(id);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_USER_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
    }

    @PutMapping("edit")
    public Result edit(@RequestBody User user, Integer[] roleIds) {
        try {
            if (StringUtil.isNotEmpty(user.getPassword())) {
                user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            }
            userService.edit(user, roleIds);
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.EDIT_USER_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_USER_SUCCESS);
    }

    @GetMapping("findRoleIdsByUserId")
    public Result findRoleIdsByUserId(Integer id) {
        try {
            return new Result(true, MessageConstant.QUERY_USER_SUCCESS, userService.findRoleIdsByUserId(id));
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_USER_FAIL);
        }

    }
}