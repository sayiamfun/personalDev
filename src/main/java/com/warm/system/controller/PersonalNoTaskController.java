package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.query.QueryPersonalTask;
import com.warm.entity.requre.GetNoEntity;
import com.warm.entity.robot.G;
import com.warm.system.entity.*;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
import com.warm.system.service.db1.PersonalNoRoadService;
import com.warm.system.service.db1.PersonalNoTaskService;
import com.warm.system.service.db2.PersonalNoOperationStockWechatAccountService;
import com.warm.system.service.db3.PersonalNoGroupCategoryService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.JsonObjectUtils;
import com.warm.utils.VerifyUtils;
import com.warm.utils.WebConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    private PersonalNoRoadService roadService;
    @Autowired
    private PersonalNoGroupCategoryService groupCategoryService;
    @Autowired
    private PersonalNoOperationStockWechatAccountService wechatAccountService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private String DBRoad = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_road);
    private String DBTask = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task);

    @ApiOperation(value = "查询所有任务")
    @GetMapping("")
    public R listTask(HttpServletRequest request){
        try {
            String getSql = DaoGetSql.getSql("SELECT * FROM " + DBTask + " where deleted = 0 order by id desc");
            List<PersonalNoTask> list = noTaskService.listBySql(new Sql(getSql));
            return R.ok().data(list);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, "", "查询所有任务异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "查询所有任务名字")
    @GetMapping("listAllTaskName")
    public R listAllTaskName(HttpServletRequest request){
        try {
            String getSql = DaoGetSql.getSql("SELECT task_name FROM " + DBTask + " where deleted = 0 order by id desc");
            List<String> list = noTaskService.listtaskNamesBySql(new Sql(getSql));
            return R.ok().data(list);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, "", "查询所有任务名字异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据通道id查询所有任务")
    @GetMapping("getByRoadId/{roadId}/")
    public R list(
            @ApiParam(name = "roadId", value = "通道id", required = true)
            @PathVariable("roadId")Integer roadId,HttpServletRequest request
    ){
        try {
            log.info("根据通道id查询所有的任务");
            Map<String, Object> map = new HashMap<>();
            String getSql = DaoGetSql.getById(DBRoad, roadId);
            PersonalNoRoad road = roadService.getBySql(new Sql(getSql));
            getSql = DaoGetSql.getSql("SELECT * FROM "+DBTask+" where road_id = ? and deleted = 0",roadId);
            List<PersonalNoTask> list = noTaskService.listBySql(new Sql(getSql));
            map.put("road", road);
            map.put("taskList", list);
            log.info("根据通道id查询所有的任务结束");
            return R.ok().data(map);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(roadId), "查询所有任务名字异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新后重试。。。");
        }
    }

    @ApiOperation(value = "添加任务")
    @PostMapping(value = "addNoTask")
    public R addNoTask(
            @ApiParam(name = "noTask", value = "要添加的任务信息", required = true)
            @RequestBody @Valid PersonalNoTask noTask, BindingResult bindingResult,HttpServletRequest request
            ){
        try {
            log.info("Valid参数验证");
            if(!VerifyUtils.collectionIsEmpty(bindingResult.getAllErrors())){
            	return R.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage().toString());
            }
            String s = veryTask(noTask);
            if(!"true".equals(s)){
                return R.error().message(s);
            }
            if(VerifyUtils.isEmpty(noTask.getId())) {
                String getSql = DaoGetSql.getSql("SELECT * FROM " + DBTask + " WHERE `task_name` = ? AND deleted = 0 LIMIT 0,1", noTask.getTaskName());
                PersonalNoTask one = noTaskService.getBySql(new Sql(getSql));
                if (!VerifyUtils.isEmpty(one)) {
                    return R.error().message("此任务已经存在");
                }
            }
            PersonalNoSuperuesr superuesr = (PersonalNoSuperuesr) request.getAttribute(WebConst.SUPERUSER);
            noTask.setSuperId(superuesr.getId());
            boolean b = noTaskService.addPersonalTask(noTask);
            if(!b){
                log.info("添加任务失败");
                return R.error().message("添加任务失败");
            }
            log.info("添加任务成功");
            return R.ok();
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(noTask), "添加任务异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
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
            @RequestBody QueryPersonalTask queryPersonalTask,
            HttpServletRequest request
    ){
        try {
            log.info("开始条件分页查询个人号任务");
            Page<PersonalNoTask> page = new Page<>(VerifyUtils.setPageNum(pageNum) , VerifyUtils.setSize(size));
            page = noTaskService.pageQuery(page , queryPersonalTask);
            //查询所有的数据集合
            List<PersonalNoTask> resultRows = page.getRecords();
            //返回数据的集合
            List<PersonalNoTask> resultList = new ArrayList<>();
            log.info("开始查找任务的标签列表,个人号数，参与任务渠道数");
            for (PersonalNoTask resultRow : resultRows) {
                PersonalNoTask taskById = noTaskService.getTaskInfoById(resultRow);
                resultList.add(taskById);
            }
            log.info("条件分页查找个人号任务完成，返回数据");
            page.setRecords(resultList);
            return R.ok().data(page);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(queryPersonalTask)+" "+pageNum+" "+size, "分页查找任务列表异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "根据任务id结束任务")
    @DeleteMapping(value = "stopTask/{taskId}/")
    public R stopTask(
            @ApiParam(name = "taskId", value = "任务id", required = true)
            @PathVariable Integer taskId,HttpServletRequest request
    ){
        try {
            String updateSql = DaoGetSql.getSql("UPDATE " + DBTask + " SET activity_type = 1 where id = ?", taskId);
            boolean b = noTaskService.updateBySql(new Sql(updateSql));
            if(!b){
                return R.error().message("停止任务失败");
            }
            return R.ok().data("");
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(taskId), "根据任务id结束任务异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据任务id删除任务")
    @DeleteMapping(value = "/{taskId}/")
    public R deleteTask(
            @ApiParam(name = "taskId", value = "任务id", required = true)
            @PathVariable Integer taskId,HttpServletRequest request
    ){
        try {
            boolean b = noTaskService.deleteById(taskId);
            if(!b){
                return R.error().message("删除失败");
            }
            return R.ok().data("");
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(taskId), "根据任务id删除任务异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据任务id查询任务")
    @GetMapping(value = "/{taskId}/")
    public R getTaskById(
            @ApiParam(name = "taskId", value = "任务id", required = true)
            @PathVariable Integer taskId,HttpServletRequest request
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
                    PersonalNoGroupCategory groupCategory = groupCategoryService.getPersonalNoGroupCategory(personalNoTaskReplyContent.getContent());
                    taskById.setCategoryName1(groupCategory.getCname());
                }
            }
            List<PersonalNoTaskBeginRemind> noTaskBeginRemindList = taskById.getNoTaskBeginRemindList();
            for (PersonalNoTaskBeginRemind personalNoTaskBeginRemind : noTaskBeginRemindList) {
                if("邀请入群".equals(personalNoTaskBeginRemind.getContentType())){
                    PersonalNoGroupCategory personalNoGroupCategory = groupCategoryService.getPersonalNoGroupCategory(personalNoTaskBeginRemind.getContent());
                    taskById.setCategoryName2(personalNoGroupCategory==null?"":personalNoGroupCategory.getCname());
                }
            }
            log.info("根据任务id查询任务结束");
            return R.ok().data(taskById);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(taskId), "根据任务id查询任务异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "根据任务随机获取个人号")
    @PostMapping("getPersonalByTaskId")
    public R getPersonalByTaskId(
            @ApiParam(name = "getNoEntity", value = "根据任务id请求个人号信息", required = true)
            @RequestBody GetNoEntity getNoEntity,HttpServletRequest request
    ){
        try {
            log.info("根据任务粉丝表的任务id随机获取一个个人号");
            if(VerifyUtils.isEmpty(getNoEntity)){
                return R.error().message("请求参数为空");
            }
            Map<String, Object> map = wechatAccountService.getPersonalByTaskId(getNoEntity);
            return R.ok().data(map);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(getNoEntity), "根据任务随机获取个人号异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新后重试。。。");
        }
    }
    @ApiOperation(value = "根据任务id获取任务数据信息")
    @GetMapping("getPersonalDataByTaskId/{taskId}/")
    public R getPersonalDataByTaskId(
            @ApiParam(name = "taskId", value = "任务id", required = true)
            @PathVariable("taskId")Integer taskId,HttpServletRequest request
    ){
        try {
            log.info("根据任务id获取任务数据信息");
            Map<String, Object> map = noTaskService.getPersonalDataByTaskId(taskId);
            if(!VerifyUtils.isEmpty(map.get("NO"))){
                return R.error().message("无数据");
            }
            return R.ok().data(map);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(taskId), "根据任务id获取任务数据信息异常", "", 1,e);
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
        if(VerifyUtils.collectionIsEmpty(task.getRecommendedReasonsList())){
            return "推荐理由不能为空";
        }
        if(VerifyUtils.isEmpty(task.getRoadId())){
            return "任务通道不能为空";
        }
        if(VerifyUtils.isEmpty(task.getNoLableList())){
            return "标签不能为空";
        }
        if(VerifyUtils.collectionIsEmpty(task.getNoList())){
            return "请选择个人号";
        }
        if(VerifyUtils.collectionIsEmpty(task.getChannelNameList())){
            return "请选择渠道";
        }
        List<PersonalNoTaskReplyContent> noTaskReplyContentList = task.getNoTaskReplyContentList();
        for (PersonalNoTaskReplyContent personalNoTaskReplyContent : noTaskReplyContentList) {
            if(VerifyUtils.isEmpty(personalNoTaskReplyContent.getContent())){
                return "自动回复内容为空";
            }
        }
        List<PersonalNoTaskBeginRemind> noTaskBeginRemindList = task.getNoTaskBeginRemindList();
        for (PersonalNoTaskBeginRemind personalNoTaskBeginRemind : noTaskBeginRemindList) {
            if(VerifyUtils.isEmpty(personalNoTaskBeginRemind.getContent())){
                return "开课提醒内容为空";
            }
        }
        return "true";
    }

}

