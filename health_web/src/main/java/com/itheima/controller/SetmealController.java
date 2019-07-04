package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;

    @PostMapping("add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setmealService.add(setmeal, checkgroupIds);
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @PostMapping("findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            return setmealService.findPage(queryPageBean);
        } catch (Exception e) {
            System.out.println(e);
            return new PageResult(0L, null);
        }

    }

    @DeleteMapping("delete")
    public Result delete(Integer id) {
        try {
            setmealService.delete(id);
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
    }

    @PutMapping("edit")
    public Result edit(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        try {
            setmealService.edit(setmeal, checkgroupIds);
        } catch (Exception e) {
            System.out.println(e);
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
    }

    @GetMapping("findCheckGroupIdsBySetmealId")
    public Result findCheckGroupIdsBySetmealId(Integer id) {
        try {
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmealService.findCheckGroupIdsBySetmealId(id));
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }

    }

}
