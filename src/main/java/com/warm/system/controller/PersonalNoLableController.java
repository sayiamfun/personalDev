package com.warm.system.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.requre.BatchUpdateObject;
import com.warm.entity.result.LableManager;
import com.warm.entity.robot.G;
import com.warm.system.entity.*;
import com.warm.system.service.db1.*;
import com.warm.utils.DaoGetSql;
import com.warm.utils.JsonObjectUtils;
import com.warm.utils.VerifyUtils;
import com.warm.utils.WebConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@CrossOrigin //跨域
@Api(description = "粉丝标签管理")
@RestController
@RequestMapping("/lableManager")
public class PersonalNoLableController {
    private static Log log = LogFactory.getLog(PersonalNoLableController.class);
    @Autowired
    private PersonalNoLableService noLableService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;
    @Autowired
    private PersonalNoTaskLableService taskLableService;
    @Autowired
    private PersonalNoPeopleService peopleService;
    @Autowired
    private PersonalNoLableCategoryService lableCategoryService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private String DBLable = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_lable);
    private String DBTaskLable = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_task_lable);
    private String DBPeople = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_people);
    private String DBLableCategory = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_lable_category);

    @ApiOperation(value = "根据个人号查找粉丝标签列表")
    @PostMapping("listByPersonal")
    public R listLableByPersonal(
            @ApiParam(name = "list", value = "个人号集合", required = true)
            @RequestBody List<PersonalNoOperationStockWechatAccount> list,HttpServletRequest request
    ) {
        try {
            if (VerifyUtils.collectionIsEmpty(list)) {
                return R.error().message("查询的参数为空");
            }
            log.info("根据个人号查找粉丝标签列表开始");
            List<String> lableSet = noLableService.listByPersonal(list);
            log.info("根据个人号查找粉丝标签列表结束");
            return R.ok().data(lableSet);
        } catch (Exception e) {
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(list), "根据个人号查找粉丝标签列表异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新后重试。。。");
        }
    }

    @ApiOperation(value = "查找粉丝标签列表")
    @GetMapping
    public R listLable(HttpServletRequest request) {
        try {
            log.info("查找粉丝标签列表开始");
            String getSql = DaoGetSql.getSql("SELECT * FROM "+DBLable+" WHERE `deleted` = 0 ORDER BY id DESC");
            List<PersonalNoLable> list = noLableService.list(getSql);
            log.info("查找粉丝标签列表成功,返回数据");
            return R.ok().data(list);
        } catch (Exception e) {
            G.requestException(DBRequestException, requestExceptionService, request, "", "查找粉丝标签列表异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "根据名称查找标签")
    @GetMapping(value = "getLableByName/{name}/")
    public R listByName(
            @ApiParam(name = "name", value = "要查找的标签类别", required = true)
            @PathVariable("name") String name,HttpServletRequest request
    ) {
        try {
            log.info("根据名称查找标签");
            String sql = "select * from " + DBLable + " where lable_name like '%" + name + "%' and deleted = 0";
            List<PersonalNoLable> noLables = noLableService.list(sql);
            log.info("根据名称查找标签结束");
            return R.ok().data(noLables);
        } catch (Exception e) {
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(name), "根据个人号查找粉丝标签列表异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新后重试。。。");
        }
    }

    @ApiOperation(value = "根据类别查找标签")
    @GetMapping(value = "getLableByCategory/{category}/")
    public R listLableByCategory(
            @ApiParam(name = "category", value = "要查找的标签类别", required = true)
            @PathVariable("category") String category,HttpServletRequest request
    ) {
        try {
            log.info("根据类别查找标签开始");
            String sql = DaoGetSql.getSql("select * from " + DBLable + " where lable_category = ? and deleted = 0", category);
            List<PersonalNoLable> list = noLableService.list(sql);
            log.info("开始将  标签类型   转换成   任务标签类型");
            List<PersonalNoTaskLable> personalNoTaskLableList = new ArrayList<>();
            if (!VerifyUtils.collectionIsEmpty(list)) {
                for (PersonalNoLable noLable : list) {
                    PersonalNoTaskLable personalNoTaskLable = new PersonalNoTaskLable();
                    personalNoTaskLable.setLableId(noLable.getId());
                    personalNoTaskLable.setLableName(noLable.getLableName());
                    personalNoTaskLableList.add(personalNoTaskLable);
                }
            }
            log.info("任务页面查找粉丝标签列表成功,返回数据");
            log.info("根据类别查找标签成功,返回数据");
            return R.ok().data(personalNoTaskLableList);
        } catch (Exception e) {
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(category), "根据类别查找标签异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "添加粉丝标签")
    @PostMapping(value = "addLable")
    @Transactional
    public R addLable(
            @ApiParam(name = "noLable", value = "要添加的标签对象", required = true)
            @RequestBody @Valid PersonalNoLable noLable, BindingResult bindingResult,HttpServletRequest request
            ) {
        try {
            log.info("添加粉丝标签对象开始");
            log.info("Valid参数验证");
            if(!VerifyUtils.collectionIsEmpty(bindingResult.getAllErrors())){
            	return R.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage().toString());
            }
            String getSql = DaoGetSql.getSql("SELECT * FROM "+DBLable+" WHERE `lable_name` = ? AND `deleted` = 0 limit 0,1",noLable.getLableName());
            PersonalNoLable one = noLableService.getOne(getSql);
            if(!VerifyUtils.isEmpty(one)){
                if(VerifyUtils.isEmpty(noLable.getId())) {
                    return R.error().message("此标签已经存在了");
                }else if(!(""+one.getId()).equals(""+noLable.getId())){
                    return R.error().message("此标签已经存在了");
                }else if(one.getLableCategory().equals(noLable.getLableCategory())){
                    return R.error().message("您未作任何修改。。。");
                }
            }
            if(!VerifyUtils.isEmpty(noLable.getId())){
                log.info("获取旧标签");
                getSql = DaoGetSql.getSql("SELECT * FROM "+DBLable+" WHERE id = ? AND `deleted` = 0 limit 0,1",noLable.getId());
                PersonalNoLable oldLabel = noLableService.getOne(getSql);
                log.info("修改任务标签表的标签名称");
                getSql = DaoGetSql.getSql(" UPDATE "+DBTaskLable+" SET `lable_name` = ? WHERE `lable_id` = ?",noLable.getLableName(),noLable.getId());
                Sql sql = new Sql(getSql);
                taskLableService.updateBySql(sql);
                log.info("给任务好友表新加一个标签，旧标签不删除");
                getSql = DaoGetSql.getSql(" UPDATE "+DBPeople+" SET `lable` = CONCAT(`lable`,?) WHERE `lable` LIKE ?",noLable.getLableName()+"|","%|"+oldLabel.getLableName()+"|%");
                sql.setSql(getSql);
                peopleService.updateBySql(sql);
            }
            getSql = DaoGetSql.getSql("SELECT * FROM "+DBLableCategory+" WHERE `category_name` = ? AND deleted = 0 LIMIT 0,1",noLable.getLableCategory());
            PersonalNoLableCategory one1 = lableCategoryService.getOne(getSql);
            noLable.setLableCategoryId(VerifyUtils.isEmpty(one1)?-1:one1.getId());
            PersonalNoSuperuesr superuesr = (PersonalNoSuperuesr) request.getAttribute(WebConst.SUPERUSER);
            noLable.setSuperId(superuesr.getId());
            noLable.setDeleted(0);
            noLable.setDb(DBLable);
            boolean b = noLableService.add(noLable) > 0;
            if (!b) {
                return R.error().message("添加标签到数据库失败");
            }
            log.info("插入标签成功");
            return R.ok();
        } catch (Exception e) {
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(noLable), "添加粉丝标签异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "标签管理")
    @PostMapping(value = "manager/{pageNum}/{size}/{lableName}/")
    public R lableManager(
            @ApiParam(name = "pageNum", value = "查询第几页", required = true)
            @PathVariable("pageNum") Long pageNum,

            @ApiParam(name = "size", value = "每页显示的条目数", required = true)
            @PathVariable("size") Long size,

            @ApiParam(name = "lableName", value = "标签名称模糊查询", required = true)
            @PathVariable("lableName") String lableName,
            HttpServletRequest request
    ) {
        try {
            log.info("开始分页查询标签管理");
            Page<PersonalNoLable> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            page = noLableService.pageQuery(page, lableName);
            log.info("分页查询标签成功，将集合传入下一个方法，开始统计数据");
            List<LableManager> lableManagerList = noLableService.getNumData(page.getRecords());
            Page<LableManager> resultPage = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            resultPage.setTotal(page.getTotal());
            resultPage.setRecords(lableManagerList);
            log.info("统计数据成功");
            return R.ok().data(resultPage);
        } catch (Exception e) {
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(lableName)+" "+pageNum+" "+size, "标签管理异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "批量修改标签类别")
    @PutMapping(value = "batchUpdateLableCategory")
    public R batchUpdateLableCategory(
            @ApiParam(name = "batchUpdateObject", value = "要批量需改的请求参数", required = true)
            @RequestBody BatchUpdateObject batchUpdateObject,HttpServletRequest request
    ) {
        try {
            log.info("批量修改标签类别");
            if (VerifyUtils.isEmpty(batchUpdateObject)) {
                log.info("请求参数为空");
                return R.error().message("请求参数为空");
            }
            boolean b = noLableService.batchUpdateCategory(batchUpdateObject);
            if (!b) {
                log.info("批量修改标签类别失败");
                return R.error().message("批量修改标签数据失败");
            }
            log.info("批量修改标签类别成功");
            return R.ok();
        } catch (Exception e) {
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(batchUpdateObject), "批量修改标签类别异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }
}

