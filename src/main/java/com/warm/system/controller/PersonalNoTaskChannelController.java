package com.warm.system.controller;


import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.query.QueryPersonalData;
import com.warm.system.entity.PersonalNoChannel;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskChannel;
import com.warm.system.service.db1.PersonalNoChannelService;
import com.warm.system.service.db1.PersonalNoTaskChannelService;
import com.warm.system.service.db1.PersonalNoTaskService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private String DBTaskChannel = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_channel);
    private String DBTask = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task);
    private String DBChannel = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_channel);

    @ApiOperation(value = "根据任务查询渠道")
    @GetMapping("listByTaskId/{taskId}/")
    public R listByTaskId(
            @ApiParam(name = "taskId", value = "根据任务查询渠道", required = true)
            @PathVariable("taskId")Long taskId
    ){
        try {
            log.info("根据id删除渠道开始");
            String getSql = DaoGetSql.getSql("select * from " + DBTaskChannel + " where personal_no_task_id = ?", taskId.intValue());
            List<PersonalNoTaskChannel> listByTaskId = taskChannelService.listBySql(new Sql(getSql));
            log.info("根据id删除渠道成功");
            return R.ok().data(listByTaskId);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "根据通道查询渠道")
    @GetMapping("listByRoadId/{roadId}/")
    public R listByRoadId(
            @ApiParam(name = "roadId", value = "根据任务查询渠道", required = true)
            @PathVariable("roadId")Long roadId
    ){
        try {
            log.info("根据任务id查询渠道开始");
            Set<String> channelIdList = new HashSet<>();
            List<PersonalNoTaskChannel> personalNoTaskChannelList = new ArrayList<>();
            String getSql = DaoGetSql.getSql("SELECT * FROM " + DBTask + " where road_id = ?", roadId);
            Sql sql = new Sql(getSql);
            List<PersonalNoTask> personalNoTasks = noTaskService.listBySql(sql);
            for (PersonalNoTask personalNoTask : personalNoTasks) {
                getSql = DaoGetSql.getSql("SELECT * FROM "+DBTaskChannel+" where personal_no_task_id = ? and road_or_task <> 1",personalNoTask.getId());
                sql.setSql(getSql);
                List<PersonalNoTaskChannel> listByTaskId = taskChannelService.listBySql(sql);
                for (PersonalNoTaskChannel personalNoTaskChannel : listByTaskId) {
                    if(!channelIdList.contains(personalNoTaskChannel.getChannelId())) {
                        personalNoTaskChannel.setUrl(null);
                        personalNoTaskChannelList.add(personalNoTaskChannel);
                    }
                }
            }
            log.info("根据任务id查询渠道渠道成功");
            return R.ok().data(personalNoTaskChannelList);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation("根据任务名称集合查询渠道集合")
    @PostMapping("listChannelByTaskNames")
    public R listChannelByTaskNames(
            @ApiParam(name = "queryPersonalData", value = "请求参数", required = true)
            @RequestBody QueryPersonalData queryPersonalData
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
                    sql1 = "SELECT * from "+DBChannel+" where id in "+ids;
                    channelList = channelService.list(sql1);
                }
            }
            return R.ok().data(channelList);
        }catch (Exception e){
            return R.error().message("网页走丢了。。。请返回重试");
        }
    }
}

