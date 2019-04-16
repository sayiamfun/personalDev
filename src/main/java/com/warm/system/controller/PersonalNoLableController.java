package com.warm.system.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.R;
import com.warm.entity.requre.BatchUpdateObject;
import com.warm.entity.result.LableManager;
import com.warm.system.entity.PersonalNo;
import com.warm.system.entity.PersonalNoLable;
import com.warm.system.entity.PersonalNoTaskLable;
import com.warm.system.service.db1.PersonalNoLableService;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  前端控制器
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

    @ApiOperation(value = "根据个人号查找粉丝标签列表")
    @PostMapping("listByPersonal")
    public R listLableByPersonal(
            @ApiParam(name = "name", value = "要查找的标签类别", required = true)
            @RequestBody List<PersonalNo> list
    ){
        try {
            if(VerifyUtils.collectionIsEmpty(list)){
                return R.error().message("查询的参数为空");
            }
            log.info("根据个人号查找粉丝标签列表开始");
            Set<String> lableSet = noLableService.listByPersonal(list);
            log.info("根据个人号查找粉丝标签列表结束");
            return R.ok().data(lableSet);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新后重试。。。");
        }
    }

    @ApiOperation(value = "查找粉丝标签列表")
    @GetMapping
    public R listLable(){
        try {
            log.info("查找粉丝标签列表开始");
            EntityWrapper<PersonalNoLable> entityWrapper = new EntityWrapper<>();
            entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
            List<PersonalNoLable> list = noLableService.selectList(entityWrapper);
            log.info("查找粉丝标签列表成功,返回数据");
            return R.ok().data(list);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "根据名称查找标签")
    @GetMapping(value = "getLableByName/{name}/")
    public R listByName(
            @ApiParam(name = "name", value = "要查找的标签类别", required = true)
            @PathVariable("name") String name
    ){
        try {
            log.info("根据名称查找标签");
            List<PersonalNoLable> noLables = noLableService.listByLableName(name);
            log.info("根据名称查找标签结束");
            return R.ok().data(noLables);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新后重试。。。");
        }
    }

    @ApiOperation(value = "根据类别查找标签")
    @GetMapping(value = "getLableByCategory/{category}/")
    public R listLableByCategory(
            @ApiParam(name = "category", value = "要查找的标签类别", required = true)
            @PathVariable("category") String category
    ){
        try {
            log.info("根据类别查找标签开始");
            List<PersonalNoLable> list = noLableService.listByCategory(category);
            log.info("开始将  标签类型   转换成   任务标签类型");
            List<PersonalNoTaskLable> personalNoTaskLableList = new ArrayList<>();
            if(!VerifyUtils.collectionIsEmpty(list)){
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
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "添加粉丝标签")
    @PostMapping(value = "addLable")
    public R addLable(
            @ApiParam(name = "noLable", value = "要添加的标签对象", required = true)
            @RequestBody PersonalNoLable noLable,
            HttpServletRequest request
    ){
        try {
            log.info("添加粉丝标签对象开始");
            if(VerifyUtils.isEmpty(noLable)){
                log.info("要插入的对象数据为空,插入失败");
                return R.error().message("要添加的标签对象为空");
            }
            if(VerifyUtils.isEmpty(noLable.getId())){
                log.info("id为空,插入数据到数据库");
                boolean b = noLableService.insert(noLable);
                if(!b){
                    return R.error().message("添加标签到数据库失败");
                }
            }else{
                log.info("id不为空,更新数据到数据库");
                boolean b = noLableService.updateById(noLable);
                if(!b){
                    return R.error().message("更新标签到数据库失败");
                }
            }
            log.info("插入标签成功");
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
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
            @PathVariable("lableName") String lableName
    ){
        try {
            log.info("开始分页查询标签管理");
            Page<PersonalNoLable> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            page = noLableService.pageQuery(page , lableName);
            log.info("分页查询标签成功，将集合传入下一个方法，开始统计数据");
            List<LableManager> lableManagerList =  noLableService.getNumData(page.getRecords());
            log.info("统计数据成功");
            return R.ok().data(lableManagerList);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "批量修改标签类别")
    @PutMapping(value = "batchUpdateLableCategory")
    public R batchUpdateLableCategory(
            @ApiParam(name = "batchUpdateObject", value = "要批量需改的请求参数", required = true)
            @RequestBody BatchUpdateObject batchUpdateObject
    ){
        try {
            log.info("批量修改标签类别");
            if(VerifyUtils.isEmpty(batchUpdateObject)){
                log.info("请求参数为空");
                return R.error().message("请求参数为空");
            }
            boolean b = noLableService.batchUpdateCategory(batchUpdateObject);
            if(!b){
                log.info("批量修改标签类别失败");
                return R.error().message("批量修改标签数据失败");
            }
            log.info("批量修改标签类别成功");
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }
}

