package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.PageResult;
import com.warm.entity.R;
import com.warm.entity.query.QueryPersonalTask;
import com.warm.entity.requre.GetNoEntity;
import com.warm.system.entity.*;
import com.warm.system.service.db1.PersonalNoPassageClickRecordService;
import com.warm.system.service.db1.PersonalNoRoadService;
import com.warm.system.service.db1.PersonalNoService;
import com.warm.system.service.db1.PersonalNoTaskService;
import com.warm.system.service.db3.PersonalNoGroupCategoryService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@CrossOrigin //跨域
@Api(description = "任务管理")
@RestController
@RequestMapping("/personal/noTask")
public class PersonalNoTaskController {
    private static Log log = LogFactory.getLog(PersonalNoTaskController.class);
    @Autowired
    private PersonalNoTaskService noTaskService;
    @Autowired
    private PersonalNoService noService;
    @Autowired
    private PersonalNoRoadService roadService;
    @Autowired
    private PersonalNoPassageClickRecordService passageClickRecordService;
    @Autowired
    private PersonalNoGroupCategoryService groupCategoryService;

    @ApiOperation(value = "查询所有任务")
    @GetMapping
    public R listAll(){
        try {
            List<PersonalNoTask> list = noTaskService.selectList(null);
            return R.ok().data(list);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "查询所有任务名字")
    @GetMapping("listAllTaskName")
    public R listAllTaskName(){
        try {
            List<String> list = noTaskService.listAllTaskName(null);
            return R.ok().data(list);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据通道id查询所有任务")
    @GetMapping("getByRoadId/{roadId}/")
    public R list(
            @ApiParam(name = "roadId", value = "通道id", required = true)
            @PathVariable("roadId")Integer roadId,
            HttpServletRequest request
    ){
        try {
            log.info("根据通道id查询所有的任务");
            Map<String, Object> map = new HashMap<>();
            PersonalNoRoad road = roadService.selectById(roadId);
            List<PersonalNoTask> list = noTaskService.listByRoadId(roadId);
            map.put("road", road);
            map.put("taskList", list);
            log.info("根据通道id查询所有的任务结束");
            return R.ok().data(map);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新后重试。。。");
        }
    }

    @ApiOperation(value = "添加任务")
    @PostMapping(value = "addNoTask")
    public R addNoTask(
            @ApiParam(name = "noTask", value = "要添加的任务信息", required = true)
            @RequestBody PersonalNoTask noTask
    ){
        try {
            log.info("开始添加任务");
            if(VerifyUtils.isEmpty(noTask)){
                log.info("要添加的任务信息为空");
                return R.error().message("要添加的任务信息为空");
            }
            String s = veryTask(noTask);
            if(!"true".equals(s)){
                return R.error().message(s);
            }
            log.info("从session取用户信息，插入superUserId");
            //第一次添加，将状态设置为进行中  0
            if(VerifyUtils.isEmpty(noTask.getActivityType())){
                noTask.setActivityType(0);
            }
            boolean b = noTaskService.addPersonalTask(noTask);
            if(!b){
                log.info("添加任务失败");
                return R.error().message("添加任务失败");
            }
            log.info("添加任务成功");
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "分页查找任务列表")
    @PostMapping(value = "{pageNum}/{size}/")
    public R listNoTask(
            @ApiParam(name = "pageNum", value = "当前页码", required = true)
            @PathVariable Long pageNum,

            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable Long size,

            @ApiParam(name = "queryPersonalTask", value = "查询任务参数", required = true)
            @RequestBody QueryPersonalTask queryPersonalTask
    ){
        try {
            log.info("开始条件分页查询个人号任务");
            Page<PersonalNoTask> page = new Page<>(VerifyUtils.setPageNum(pageNum) , VerifyUtils.setSize(size));
            page = noTaskService.pageQuery(page , queryPersonalTask);
            PageResult<PersonalNoTask> pageResult = new PageResult<>(page.getTotal() , page.getRecords());
            //查询所有的数据集合
            List<PersonalNoTask> resultRows = pageResult.getRows();
            //返回数据的集合
            List<PersonalNoTask> resultList = new ArrayList<>();
            log.info("开始查找任务的标签列表,个人号数，参与任务渠道数");
            for (PersonalNoTask resultRow : resultRows) {
                PersonalNoTask taskById = noTaskService.getTaskInfoById(resultRow.getId());
                resultList.add(taskById);
            }
            log.info("条件分页查找个人号任务完成，返回数据");
            page.setRecords(resultList);
            return R.ok().data(page);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "根据任务id结束任务")
    @DeleteMapping(value = "stopTask/{taskId}/")
    public R stopTask(
            @ApiParam(name = "taskId", value = "任务id", required = true)
            @PathVariable Integer taskId
    ){
        try {
            boolean b = noTaskService.stopTaskById(taskId);
            if(!b){
                return R.error().message("停止任务失败");
            }
            return R.ok().data("");
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据任务id删除任务")
    @DeleteMapping(value = "/{taskId}/")
    public R deleteTask(
            @ApiParam(name = "taskId", value = "任务id", required = true)
            @PathVariable Integer taskId
    ){
        try {
            boolean b = noTaskService.deleteById(taskId);
            if(!b){
                return R.error().message("删除失败");
            }
            return R.ok().data("");
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据任务id查询任务")
    @GetMapping(value = "/{taskId}/")
    public R getTaskById(
            @ApiParam(name = "taskId", value = "任务id", required = true)
            @PathVariable Integer taskId
    ){
        try {
            log.info("根据任务id查询任务开始");
            if(VerifyUtils.isEmpty(taskId)){
                log.info("taskId为空");
                return R.error().message("taskId为空");
            }
            PersonalNoTask taskById = noTaskService.getTaskById(taskId);
            List<PersonalNoTaskReplyContent> noTaskReplyContentList = taskById.getNoTaskReplyContentList();
            for (PersonalNoTaskReplyContent personalNoTaskReplyContent : noTaskReplyContentList) {
                if("邀请入群".equals(personalNoTaskReplyContent.getContentType())){
                    String[] split = personalNoTaskReplyContent.getContent().split("/");
                    PersonalNoGroupCategory groupCategory = getPersonalNoGroupCategory(split);
                    taskById.setCategoryName1(groupCategory.getCname());
                }
            }
            List<PersonalNoTaskBeginRemind> noTaskBeginRemindList = taskById.getNoTaskBeginRemindList();
            for (PersonalNoTaskBeginRemind personalNoTaskBeginRemind : noTaskBeginRemindList) {
                if("邀请入群".equals(personalNoTaskBeginRemind.getContentType())){
                    String[] split = personalNoTaskBeginRemind.getContent().split("/");
                    PersonalNoGroupCategory personalNoGroupCategory = getPersonalNoGroupCategory(split);
                    taskById.setCategoryName2(personalNoGroupCategory.getCname());
                }
            }
            log.info("根据任务id查询任务结束");
            return R.ok().data(taskById);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }
    //获取群类别信息
    private PersonalNoGroupCategory getPersonalNoGroupCategory(String[] split) {
        PersonalNoGroupCategory groupCategory = null;
        switch (Integer.parseInt(split[0])){
            case 0:
                groupCategory = groupCategoryService.getById(Integer.parseInt(split[1]));
                break;
            case 1:
                groupCategory = groupCategoryService.getByIdFromQunLie01(Integer.parseInt(split[1]));
                break;
        }
        return groupCategory;
    }

    @ApiOperation(value = "根据任务随机获取个人号")
    @PostMapping("getPersonalByTaskId")
    public R getPersonalByTaskId(
            @ApiParam(name = "getNoEntity", value = "根据任务id请求个人号信息", required = true)
            @RequestBody GetNoEntity getNoEntity
    ){
        try {
            log.info("根据任务粉丝表的任务id随机获取一个个人号");
            if(VerifyUtils.isEmpty(getNoEntity)){
                return R.error().message("请求参数为空");
            }
            Map<String, Object> map = noService.getPersonalByTaskId(getNoEntity);
            return R.ok().data(map);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新后重试。。。");
        }
    }
    @ApiOperation(value = "根据任务id获取任务数据信息")
    @GetMapping("getPersonalDataByTaskId/{taskId}/")
    public R getPersonalDataByTaskId(
            @ApiParam(name = "taskId", value = "任务id", required = true)
            @PathVariable("taskId")Integer taskId
    ){
        try {
            log.info("根据任务id获取任务数据信息");
            Map<String, Object> map = noTaskService.getPersonalDataByTaskId(taskId);
            if(!VerifyUtils.isEmpty(map.get("NO"))){
                return R.error().message("无数据");
            }
            return R.ok().data(map);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }
    //验证任务参数
    private String veryTask(PersonalNoTask task){
        if(VerifyUtils.isEmpty(task.getTaskName())){
            return "任务名称不能为空";
        }
        if(VerifyUtils.isEmpty(task.getTaskTheme())){
            return "任务主题不能为空";
        }
        if(VerifyUtils.isEmpty(task.getPopularizeCategory())){
            return "推广类型不能为空";
        }
        if(VerifyUtils.collectionIsEmpty(task.getRecommendedReasonsList())){
            return "推荐理由不能为空";
        }
        if(VerifyUtils.isEmpty(task.getSubjectName())){
            return "课程名称不能为空";
        }
        if(VerifyUtils.isEmpty(task.getRoadId())){
            return "任务通道不能为空";
        }
        if(VerifyUtils.isEmpty(task.getNoLableList())){
            return "标签不能为空";
        }
        if(VerifyUtils.isEmpty(task.getNoList())){
            return "个人号不能为空";
        }
        if(VerifyUtils.isEmpty(task.getRoadId())){
            return "任务通道不能为空";
        }
        return "true";
    }

}

