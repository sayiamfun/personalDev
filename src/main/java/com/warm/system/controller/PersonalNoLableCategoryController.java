package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoLableCategory;
import com.warm.system.service.db1.PersonalNoLableCategoryService;
import com.warm.system.service.db1.PersonalNoLableService;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.JsonObjectUtils;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
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
@Api(description = "粉丝标签类别管理")
@RestController
@RequestMapping("/personalLableCategoryManager")
public class PersonalNoLableCategoryController {
    private static Log log = LogFactory.getLog(PersonalNoLableCategoryController.class);
    @Autowired
    private PersonalNoLableCategoryService noLableCategoryService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;
    @Autowired
    private PersonalNoLableService lableService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private String DBLableCategory = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_lable_category);
    private String DBLable = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_lable);

    @ApiOperation(value = "根据id删除标签类别信息")
    @DeleteMapping("deleteById/{id}/")
    public R delete(
            @ApiParam(name = "id", value = "标签类别id", required = true)
            @PathVariable("id") Integer id,HttpServletRequest request
    ){
        try {
            String getSql = DaoGetSql.getSql("SELECT COUNT(*) FROM "+DBLable+" WHERE `lable_category_id` = ?",id);
            Long count = lableService.getCount(getSql);
            if(count.intValue()>0){
                return R.error().message("此类别下存在标签，不能删除哦");
            }
            String sql = DaoGetSql.getSql("UPDATE "+DBLableCategory+" SET `deleted` = 1 WHERE `id` = ?",id);
            int b = noLableCategoryService.delete(sql);
            if(b<0){
                return R.error().message("删除标签类别失败");
            }
            return R.ok().data(null);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(id), "根据id删除标签类别信息异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新后重试。。。");
        }
    }

    @ApiOperation(value = "根据id查询标签类别信息")
    @GetMapping("getById/{id}/")
    public R getById(
            @ApiParam(name = "id", value = "标签类别id", required = true)
            @PathVariable("id") Integer id,HttpServletRequest request
    ){
        try {
            log.info("根据id查询类别信息");
            String sql = DaoGetSql.getSql("SELECT * FROM "+DBLableCategory+"  WHERE id = ? AND `deleted` = 0", id);
            PersonalNoLableCategory byId = noLableCategoryService.getOne(sql);
            log.info("根据id查询类别信息结束");
            return R.ok().data(byId);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(id), "根据id查询标签类别信息异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新后重试。。。");
        }
    }

    @ApiOperation(value = "添加标签类别信息")
    @PostMapping("addLableCategory")
    public R addLableCategory(
            @ApiParam(name = "lableCategory", value = "标签类别信息", required = true)
            @RequestBody @Valid PersonalNoLableCategory lableCategory, BindingResult bindingResult,HttpServletRequest request
    ){
        try {
            log.info("Valid参数验证");
            if(!VerifyUtils.collectionIsEmpty(bindingResult.getAllErrors())){
            	return R.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage().toString());
            }
            String getSql = DaoGetSql.getSql("SELECT * FROM "+DBLableCategory+"  WHERE `category_name` = ? AND `deleted` = 0 LIMIT 0,1",lableCategory.getCategoryName());
            PersonalNoLableCategory one = noLableCategoryService.getOne(getSql);
            if(!VerifyUtils.isEmpty(one)){
                if(VerifyUtils.isEmpty(lableCategory.getId())) {
                    return R.error().message("此标签类别已经存在");
                }else if(!(""+one.getId()).equals(""+lableCategory.getId())){
                    return R.error().message("此标签类别已经存在");
                }else if(!VerifyUtils.isEmpty(one.getRemarks()) && one.getRemarks().equals(lableCategory.getRemarks())){
                    return R.error().message("您未做任何修改");
                }
            }
            log.info("修改标签表的类别名称");
            getSql = DaoGetSql.getSql("UPDATE "+DBLable+" SET `lable_category` = ? WHERE `lable_category_id` = ?",lableCategory.getCategoryName(),lableCategory.getId());
            lableService.updateBySql(new Sql(getSql));
            lableCategory.setCreateTime(new Date());
            lableCategory.setDeleted(0);
            lableCategory.setDb(DBLableCategory);
            Integer add = noLableCategoryService.add(lableCategory);
            if(add<0){
                log.info("添加标签类别失败");
                return R.error().message("添加标签类别失败");
            }
            log.info("添加标签类别成功");
            return R.ok().data(null);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(lableCategory), "添加标签类别信息异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新后重试。。。");
        }
    }

    @ApiOperation(value = "查询所有粉丝标签类别列表")
    @GetMapping()
    public R list(HttpServletRequest request){
        try {
            log.info("查询所有粉丝标签类别列表开始");
            String sql = DaoGetSql.getSql("SELECT * FROM "+DBLableCategory+"  WHERE `deleted` = 0 ORDER BY id DESC");
            List<PersonalNoLableCategory> personalList = noLableCategoryService.list(sql);
            log.info("查询标签类别列表成功,返回数据");
            return R.ok().data(personalList);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, "", "查询所有粉丝标签类别列表异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "分页查询粉丝标签类别列表")
    @GetMapping("{pageNum}/{size}/{categoryname}/")
    public R pageQuery(
            @ApiParam(name = "pageNum", value = "当前页码", required = true)
            @PathVariable("pageNum") Long pageNum,

            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable("size") Long size,

            @ApiParam(name = "categoryname", value = "标签类别名称", required = true)
            @PathVariable("categoryname") String categoryname,
            HttpServletRequest request
    ){
        try {
            log.info("分页查询标签类别开始");
            Page<PersonalNoLableCategory> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            page = noLableCategoryService.pageList(page, categoryname);
            log.info("分页查询标签类别结束");
            return R.ok().data(page);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, ""+pageNum+" "+size+" "+categoryname, "分页查询粉丝标签类别列表异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新后重试。。。");
        }
    }

}

