package com.warm.system.controller;


import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.query.QueryPersonalData;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoChannel;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskChannel;
import com.warm.system.service.db1.*;
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

import javax.servlet.http.HttpServletRequest;
import java.net.DatagramPacket;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liwenjie
 * @since 2019-02-13
 */
@CrossOrigin //跨域
@Api(description = "任务渠道管理")
@RestController
@RequestMapping("/personalNoTaskChannel")
public class PersonalNoTaskChannelController {

    private static Log log = LogFactory.getLog(PersonalNoTaskChannelController.class);
    @Autowired
    private PersonalNoTaskChannelService taskChannelService;
    @Autowired
    private PersonalNoTaskService noTaskService;
    @Autowired
    private PersonalNoChannelService channelService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;
    @Autowired
    private PersonalNoRoadService roadService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private String DBTaskChannel = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_channel);
    private String DBTask = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task);
    private String DBChannel = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_channel);
    private String DBRoad = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_road);

    @ApiOperation(value = "根据任务查询渠道")
    @GetMapping("listByTaskId/{taskId}/")
    public R listByTaskId(
            @ApiParam(name = "taskId", value = "根据任务查询渠道", required = true)
            @PathVariable("taskId")Long taskId,HttpServletRequest request
    ){
        try {
            String getSql = DaoGetSql.getSql("select * from " + DBTaskChannel + " where personal_no_task_id = ? and deleted = 0", taskId.intValue());
            List<PersonalNoTaskChannel> listByTaskId = taskChannelService.listBySql(new Sql(getSql));
            return R.ok().data(listByTaskId);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(taskId), "根据任务查询渠道异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "根据通道查询渠道")
    @GetMapping("listByRoadId/{roadId}/")
    public R listByRoadId(
            @ApiParam(name = "roadId", value = "根据任务查询渠道", required = true)
            @PathVariable("roadId")Long roadId, HttpServletRequest request
            ){
        try {
            log.info("根据任务id查询渠道开始");
            Set<String> channelIdList = new HashSet<>();
            List<PersonalNoTaskChannel> personalNoTaskChannelList = new ArrayList<>();
            List<PersonalNoTaskChannel> resultPersonalNoTaskChannelList = new ArrayList<>();
            String getSql = DaoGetSql.getSql("SELECT * FROM " + DBTask + " where road_id = ? and deleted = 0", roadId);
            Sql sql = new Sql(getSql);
            List<PersonalNoTask> personalNoTasks = noTaskService.listBySql(sql);
            for (PersonalNoTask personalNoTask : personalNoTasks) {
                getSql = DaoGetSql.getSql("SELECT * FROM "+DBTaskChannel+" where personal_no_task_id = ? and deleted = 0 and (road_or_task IS NULL OR road_or_task = 0) GROUP BY `channel_id` ",personalNoTask.getId());
                sql.setSql(getSql);
                List<PersonalNoTaskChannel> listByTaskId = taskChannelService.listBySql(sql);
                for (PersonalNoTaskChannel personalNoTaskChannel : listByTaskId) {
                    if(!channelIdList.contains(""+personalNoTaskChannel.getChannelId())) {
                        personalNoTaskChannel.setPersonalNoTaskId(roadId.intValue());
                        channelIdList.add(""+personalNoTaskChannel.getChannelId());
                        personalNoTaskChannel.setUrl(null);
                        personalNoTaskChannelList.add(personalNoTaskChannel);
                    }
                }
            }
            for (PersonalNoTaskChannel personalNoTaskChannel : personalNoTaskChannelList) {
                getSql = DaoGetSql.getSql("SELECT * FROM "+DBTaskChannel+" where personal_no_task_id = ? and channel_id = ? and deleted = 0 and road_or_task = '1' limit 0,1",personalNoTaskChannel.getPersonalNoTaskId(),personalNoTaskChannel.getChannelId());
                sql.setSql(getSql);
                PersonalNoTaskChannel one =  taskChannelService.getBySql(sql);
                if(VerifyUtils.isEmpty(one)){
                    personalNoTaskChannel.setId(null);
                    personalNoTaskChannel.setRoadOrTask("1");
                    personalNoTaskChannel.setDb(DBTaskChannel);
                    taskChannelService.add(personalNoTaskChannel);
                    resultPersonalNoTaskChannelList.add(personalNoTaskChannel);
                }else {
                    resultPersonalNoTaskChannelList.add(one);
                }
            }
            log.info("根据任务id查询渠道渠道成功");
            return R.ok().data(resultPersonalNoTaskChannelList);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(roadId), "根据通道查询渠道异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation("根据任务名称集合查询渠道集合")
    @PostMapping("listChannelByTaskNames")
    public R listChannelByTaskNames(
            @ApiParam(name = "queryPersonalData", value = "请求参数", required = true)
            @RequestBody QueryPersonalData queryPersonalData,HttpServletRequest request
    ){
        try {
            log.info("");
            List<PersonalNoChannel> channelList = new ArrayList<>();
            if(!VerifyUtils.collectionIsEmpty(queryPersonalData.getNoTaskName())){
                String ids = DaoGetSql.getIds(queryPersonalData.getNoTaskName());
                String sql1 = "select DISTINCT channel_id from "+DBTask+" as task\n" +
                        "left join "+DBTaskChannel+" as taskChannel on task.id = taskChannel.personal_no_task_id\n" +
                        "where task.task_name in "+ids;
                Sql sql = new Sql(sql1);
                List<Integer> channelIds = taskChannelService.listChannelIdsBySql(sql);
                if(!VerifyUtils.collectionIsEmpty(channelIds)){
                    ids = DaoGetSql.getIds(channelIds);
                    sql1 = "SELECT * from "+DBChannel+" where id in "+ids+" and deleted = 0";
                    channelList = channelService.list(sql1);
                }
            }
            return R.ok().data(channelList);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(queryPersonalData), "根据任务名称集合查询渠道集合异常", "", 1,e);
            return R.error().message("网页走丢了。。。请返回重试");
        }
    }

    @ApiOperation("根据通道名称查询渠道集合")
    @PostMapping("listChannelByRoadName")
    public R listChannelByRoadName(
            @ApiParam(name = "queryPersonalData", value = "请求参数", required = true)
            @RequestBody QueryPersonalData queryPersonalData,HttpServletRequest request
    ){
        try {
            log.info("");
            List<PersonalNoChannel> channelList = new ArrayList<>();
            if(!VerifyUtils.collectionIsEmpty(queryPersonalData.getNoRoadName())){
                String names = DaoGetSql.getIds(queryPersonalData.getNoRoadName());
                String getSql = "SELECT `id` FROM "+DBRoad+" WHERE `road_name` IN "+names;
                Sql sql = new Sql(getSql);
                List<String> idList = roadService.listStringBySql(sql);
                String roadIds = DaoGetSql.getIds(idList);
                getSql = "SELECT id FROM "+DBTask+" WHERE  `road_id` IN "+roadIds+" AND deleted = 0";
                sql.setSql(getSql);
                List<String> taskIdList = noTaskService.listStringBySql(sql);
                String taskIds = DaoGetSql.getIds(taskIdList);
                String sql1 = "select DISTINCT channel_id from "+DBTask+" as task\n" +
                        "left join "+DBTaskChannel+" as taskChannel on task.id = taskChannel.personal_no_task_id\n" +
                        "where task.id in "+taskIds;
                sql.setSql(sql1);
                List<Integer> channelIds = taskChannelService.listChannelIdsBySql(sql);
                if(!VerifyUtils.collectionIsEmpty(channelIds)){
                    String ids = DaoGetSql.getIds(channelIds);
                    sql1 = "SELECT * from "+DBChannel+" where id in "+ids+" and deleted = 0";
                    channelList = channelService.list(sql1);
                }
            }
            PersonalNoChannel taskChannel = new PersonalNoChannel();
            taskChannel.setId(0);
            taskChannel.setChannelName("默认渠道");
            channelList.add(0,taskChannel);
            return R.ok().data(channelList);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(queryPersonalData), "根据通道名称查询渠道集合", "", 1,e);
            return R.error().message("网页走丢了。。。请返回重试");
        }
    }



}

