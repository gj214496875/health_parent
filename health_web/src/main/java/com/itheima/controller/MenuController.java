package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("menu")
public class MenuController {
    @Reference
    private MenuService menuService;

    @PostMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            return menuService.findPage(queryPageBean);
        } catch (Exception e) {
            System.out.println(e);
            return new PageResult(0L, null);
        }

    }

    @GetMapping("findAll")
    public Result findAll() {
        try {
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menuService.findAll());
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.QUERY_MENU_FAIL);
        }
    }
    @GetMapping("findUserMenu")
    public Result findUserMenu() {
        try {
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User)
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menuService.findUserMenu(user.getUsername()));
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.QUERY_MENU_FAIL);
        }
    }

    @GetMapping("findIcon")
    public Result findIcon() {
        try {
            return new Result(true, MessageConstant.QUERY_ICON_SUCCESS, menuService.findIcon());
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.QUERY_ICON_FAIL);
        }
    }
    @GetMapping("findMenuIdsByRoleId")
    public Result findMenuIdsByRoleId(Integer id) {
        try {
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menuService.findMenuIdsByRoleId(id));
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.QUERY_MENU_FAIL);
        }
    }

    @PostMapping("add")
    public Result add(@RequestBody Menu menu) {
        try {
            menuService.add(menu);
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.ADD_MENU_FAIL);
        }
        return new Result(true, MessageConstant.ADD_MENU_SUCCESS);
    }

    @DeleteMapping("delete")
    public Result delete(Integer id, Integer parentMenuId) {
        try {
            menuService.delete(id,parentMenuId);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_MENU_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_MENU_SUCCESS);
    }

    @PutMapping("edit")
    public Result edit(@RequestBody Menu menu) {
        try {
            menuService.edit(menu);
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.EDIT_MENU_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_MENU_SUCCESS);
    }
}
