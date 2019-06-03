package com.warm.system.controller;


import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoValueTable;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
import com.warm.system.service.db1.PersonalNoValueTableService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.JsonObjectUtils;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

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
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private String DBValueTable = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_value_table);

    @ApiOperation(value = "查询所有的技术人员信息列表")
    @GetMapping("listSuperUser")
    public R listSuperUser(HttpServletRequest request){
        try {
            String getSql = DaoGetSql.getSql("select * from "+DBValueTable+" where type = ?",0);
            return R.ok().data(valueTableService.listBySql(new Sql(getSql)));
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, "", "查询所有的技术人员信息列表异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "查询检测手机是否请求任务的个人号列表")
    @GetMapping("listUser")
    public R listUser(HttpServletRequest request){
        try {
            String getSql = DaoGetSql.getSql("select * from "+DBValueTable+" where type = ?",2);
            return R.ok().data(valueTableService.listBySql(new Sql(getSql)));
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, "", "查询检测手机是否请求任务的个人号列表异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "查询检测手机是否请求任务的个人号列表")
    @GetMapping("listValue")
    public R listValue(HttpServletRequest request){
        try {
            String getSql = DaoGetSql.getSql("select * from "+DBValueTable+" where type = ?",1);
            return R.ok().data(valueTableService.listBySql(new Sql(getSql)));
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, "", "查询检测手机是否请求任务的个人号列表异常", "", 1,e);
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
            valueTable.setDb(DBValueTable);
            boolean save = valueTableService.add(valueTable)>0;
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
            String getSql = DaoGetSql.getById(DBValueTable, id);
            return R.ok().data(valueTableService.getBySql(new Sql(getSql)));
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据id删除成员")
    @DeleteMapping("{id}")
    public R deleteById(
            @ApiParam(name = "id", value = "要删除的id", required = true)
            @PathVariable("id") Integer id
    ){
        try {
            String getSql = DaoGetSql.deleteById(DBValueTable, id);
            return R.ok().data(valueTableService.delteBySql(new Sql(getSql)));
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }
}

