package com.warm.system.controller;


import com.warm.entity.R;
import com.warm.system.entity.PersonalNoCategory;
import com.warm.system.service.db1.PersonalNoCategoryService;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "查询所有个人号类别列表")
    @GetMapping
    public R list(){
        List<PersonalNoCategory> personalCategoryList = noCategoryService.listAll();
        log.info("查找个人号类别完成");
        return R.ok().data(personalCategoryList);
    }

    @ApiOperation(value = "添加个人号类别")
    @PostMapping("addCategory")
    public R addCategory(
            @ApiParam(name = "noCategory", value = "添加个人号类别对象", required = true)
            @RequestBody PersonalNoCategory noCategory,
            HttpServletRequest request
    ) throws Exception {
        try {
            log.info("添加个人号类别任务开始");
            if(VerifyUtils.isEmpty(noCategory) || VerifyUtils.isEmpty(noCategory.getPersonalNoCategory())){
                log.info("传入的插入数据为空,不能插入");
                return R.error().message("待添加的个人号类别信息为空");
            }
            //如果存在id则修改,id不存在则添加
            if(VerifyUtils.isEmpty(noCategory.getId())){
                log.info("id不存在,插入数据到数据库");
                boolean b = noCategoryService.insert(noCategory);
                if(!b){
                    return R.error().message("添加个人号类别信息到数据库失败");
                }
            }else{
                log.info("id存在,修改数据库数据");
                boolean b = noCategoryService.updateById(noCategory);
                if(!b){
                    return R.error().message("更新个人号类别信息到数据库失败");
                }
            }
            log.info("更新个人号类别到数据库成功");
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "根据id删除类别")
    @DeleteMapping("{categoryId}")
    public R deleteChannelById(
            @ApiParam(name = "categoryId", value = "待删除类别对象id", required = true)
            @PathVariable("categoryId")Long categoryId) throws Exception {
        try {
            log.info("根据id删除类别开始");
            if(VerifyUtils.isEmpty(categoryId)){
                log.info("传入的id为空,删除失败");
                return R.error().message("待删除类别id为空");
            }
            boolean b = noCategoryService.deleteById(categoryId.intValue());
            if(!b){
                log.info("数据库根据id删除类别成功");
                return R.error().message("删除类别数据库数据失败");
            }
            log.info("根据id删除类别成功");
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }
}

