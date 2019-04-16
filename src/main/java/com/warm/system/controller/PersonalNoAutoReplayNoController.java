package com.warm.system.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.R;
import com.warm.entity.query.QueryPersonal;
import com.warm.system.entity.PersonalNo;
import com.warm.system.entity.PersonalNoAutoReplayNo;
import com.warm.system.service.db1.PersonalNoAutoReplayNoService;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dgd123
 * @since 2019-04-03
 */
@CrossOrigin //跨域
@Api(description = "不走通道自动回复个人号管理")
@RestController
@RequestMapping("/personalNoAutoReplayNo")
public class PersonalNoAutoReplayNoController {
    @Autowired
    private PersonalNoAutoReplayNoService autoReplayNoService;

    @ApiOperation("分页查询列表(nickName为-1查询所有")
    @GetMapping("listAutoNo/{pageNum}/{size}/{nickName}/")
    public R listAutoNo(
            @ApiParam(name = "pageNum", value = "当前页码", required = true)
            @PathVariable Long pageNum,

            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable Long size,

            @ApiParam(name = "nickName", value = "查询条件", required = true)
            @PathVariable String nickName
    ){
        try {
            Page<PersonalNoAutoReplayNo> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            page = autoReplayNoService.pageQuery(page, nickName);
            return R.ok().data(page);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation("添加")
    @PostMapping("add")
    public R add(@RequestBody PersonalNo no){
        try {
            boolean b = autoReplayNoService.insertInfo(no);
            return R.ok().data("");
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation("删除")
    @DeleteMapping("delete/{id}/")
    public R deleteById(@PathVariable("id") Integer id){
        try {
            autoReplayNoService.deleteById(id);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }
}

