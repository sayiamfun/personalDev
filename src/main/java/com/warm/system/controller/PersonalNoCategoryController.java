package com.warm.system.controller;


import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoCategory;
import com.warm.system.service.db1.PersonalNoCategoryAndGroupService;
import com.warm.system.service.db1.PersonalNoCategoryService;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.JsonObjectUtils;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@CrossOrigin //跨域
@Api(description = "个人号类别管理")
@RestController
@RequestMapping("/personalCategoryManager")
public class PersonalNoCategoryController {
    private static Log log = LogFactory.getLog(PersonalNoCategoryController.class);
    @Autowired
    private PersonalNoCategoryService noCategoryService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;
    @Autowired
    private PersonalNoCategoryAndGroupService categoryAndGroupService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private String DBNoCategory = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_category);
    private String DBNoCategoryAndGroup = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_category_and_group);


    @ApiOperation(value = "查询所有个人号类别列表")
    @GetMapping
    public R list(HttpServletRequest request){
        try {
            String sql = DaoGetSql.getSql("select * from " + DBNoCategory + " where deleted = 0");
            List<PersonalNoCategory> personalCategoryList = noCategoryService.list(sql);
            log.info("查找个人号类别完成");
            return R.ok().data(personalCategoryList);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, "", "查询个人号类别异常", "", 1,e);
            return R.error().message("网页走丢了。。。请返回重试");
        }

    }

    @ApiOperation(value = "添加个人号类别")
    @PostMapping("addCategory")
    @Transactional
    public R addCategory(
            @ApiParam(name = "noCategory", value = "添加个人号类别对象", required = true)
            @RequestBody @Valid PersonalNoCategory noCategory, BindingResult bindingResult,HttpServletRequest request
    ){
        try {
            log.info("添加个人号类别任务开始");
            if(!VerifyUtils.collectionIsEmpty(bindingResult.getAllErrors())){
                return R.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage().toString());
            }
            String getSql = DaoGetSql.getSql("SELECT * FROM "+DBNoCategory+" WHERE `personal_no_category` = ? AND `deleted` = 0 LIMIT 0,1",noCategory.getPersonalNoCategory());
            PersonalNoCategory one = noCategoryService.getOne(getSql);
            if(!VerifyUtils.isEmpty(one)){
                return R.error().message("此类别已经存在");
            }
            if(!VerifyUtils.isEmpty(noCategory.getId())){
                if(noCategory.getId() == 1){
                    return R.error().message("默认类别不能修改");
                }
                log.info("修改个人号类别和任务组表的类别名称");
                getSql = DaoGetSql.getSql("UPDATE "+DBNoCategoryAndGroup+"  SET `category` = ? WHERE `category_id` = ?",noCategory.getPersonalNoCategory(),noCategory.getId());
                Sql sql = new Sql(getSql);
                categoryAndGroupService.updateBySql(sql);
            }
            noCategory.setDeleted(0);
            noCategory.setDb(DBNoCategory);
            noCategoryService.add(noCategory);
            log.info("更新个人号类别到数据库成功");
            return R.ok();
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(noCategory), "添加个人号类别异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "根据id删除类别")
    @DeleteMapping("{categoryId}")
    @Transactional
    public R deleteChannelById(
            @ApiParam(name = "categoryId", value = "待删除类别对象id", required = true)
            @PathVariable("categoryId")Long categoryId,HttpServletRequest request){
        try {
            log.info("根据id删除类别开始");
            if(VerifyUtils.isEmpty(categoryId)){
                log.info("传入的id为空,删除失败");
                return R.error().message("待删除类别id为空");
            }
            if(categoryId == 1){
                log.info("默认类别不能删除");
                return R.error().message("默认类别不能删除");
            }
            String getSql = DaoGetSql.getSql("SELECT COUNT(*) FROM "+DBNoCategoryAndGroup+" WHERE `category_id` = ?",categoryId);
            Sql sql = new Sql(getSql);
            Long count = categoryAndGroupService.countBySql(sql);
            if(count.intValue()>0){
                return R.error().message("当前类别下存在个人号,不能删除哦");
            }
            getSql = DaoGetSql.getSql("UPDATE " + DBNoCategory +" set deleted = 1 where id = ?", categoryId.intValue());
            sql.setSql(getSql);
            noCategoryService.deleteBySql(sql);
            log.info("根据id删除类别成功");
            return R.ok();
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(categoryId), "根据id查询个人号类别异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }
}

