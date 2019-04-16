package com.warm.system.controller;


import com.warm.entity.R;
import com.warm.system.entity.PersonalNoValueTable;
import com.warm.system.service.db1.PersonalNoValueTableService;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@CrossOrigin //跨域
@Api(description = "常量管理")
@RestController
@RequestMapping("/valueTable")
public class PersonalNoValueTableController {
    private static Log log = LogFactory.getLog(PersonalNoValueTableController.class);
    @Autowired
    private PersonalNoValueTableService valueTableService;

    @ApiOperation(value = "查询所有的技术人员信息列表")
    @GetMapping("listSuperUser")
    public R listSuperUser(){
        try {
            return R.ok().data(valueTableService.listSuperUser(0));
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "查询检测手机是否请求任务的个人号列表")
    @GetMapping("listUser")
    public R listUser(){
        try {
            return R.ok().data(valueTableService.listSuperUser(2));
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "查询检测手机是否请求任务的个人号列表")
    @GetMapping("listValue")
    public R listValue(){
        try {
            return R.ok().data(valueTableService.listSuperUser(1));
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }


    @ApiOperation(value = "添加成员")
    @PostMapping("addMember")
    public R addMember(
            @ApiParam(name = "valueTable", value = "要添加的成员信息", required = true)
            @RequestBody PersonalNoValueTable valueTable
    ){
        try {
            log.info("添加条件成员");
            if(VerifyUtils.isEmpty(valueTable)){
                return R.error().message("添加数据为空");
            }
            boolean save = valueTableService.insert(valueTable);
            if(!save){
                return R.error().message("添加失败");
            }
            return R.ok().data("");
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据id查找")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "要查询的id", required = true)
            @PathVariable("id") Integer id
    ){
        try {
            return R.ok().data(valueTableService.selectById(id));
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据id删除成员")
    @DeleteMapping("{id}")
    public R deleteById(
            @ApiParam(name = "id", value = "要查询的id", required = true)
            @PathVariable("id") Integer id
    ){
        try {
            return R.ok().data(valueTableService.deleteById(id));
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }
}

