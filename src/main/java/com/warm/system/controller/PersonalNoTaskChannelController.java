package com.warm.system.controller;


import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskChannel;
import com.warm.system.service.db1.PersonalNoTaskChannelService;
import com.warm.system.service.db1.PersonalNoTaskService;
import com.warm.utils.DaoGetSql;
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

    private String ZCDBTaskChannel = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_channel);

    @ApiOperation(value = "根据任务查询渠道")
    @GetMapping("listByTaskId/{taskId}/")
    public R listByTaskId(
            @ApiParam(name = "taskId", value = "根据任务查询渠道", required = true)
            @PathVariable("taskId")Long taskId
    ){
        try {
            log.info("根据id删除渠道开始");
//            String sql = DaoGetSql.getSql("select from " + ZCDBTaskChannel + " where id = ?", channelId);
            List<PersonalNoTaskChannel> listByTaskId = taskChannelService.getListByTaskId(taskId.intValue());
            log.info("根据id删除渠道成功");
            return R.ok().data(listByTaskId);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "根据通道查询渠道")
    @GetMapping("listByRoadId/{roadId}/")
    public R listByRoadId(
            @ApiParam(name = "roadId", value = "根据任务查询渠道", required = true)
            @PathVariable("roadId")Long roadId
    ){
        try {
            log.info("根据id删除渠道开始");
            Set<String> channelIdList = new HashSet<>();
            List<PersonalNoTaskChannel> personalNoTaskChannelList = new ArrayList<>();
            List<PersonalNoTask> personalNoTasks = noTaskService.listByRoadId(roadId.intValue());
            for (PersonalNoTask personalNoTask : personalNoTasks) {
                List<PersonalNoTaskChannel> listByTaskId = taskChannelService.getListByTaskId(personalNoTask.getId());
                for (PersonalNoTaskChannel personalNoTaskChannel : listByTaskId) {
                    if(!channelIdList.contains(personalNoTaskChannel.getChannelId())) {
                        personalNoTaskChannel.setUrl(null);
                        personalNoTaskChannelList.add(personalNoTaskChannel);
                    }
                }
            }
//            String sql = DaoGetSql.getSql("select from " + ZCDBTaskChannel + " where id = ?", channelId);
            log.info("根据id删除渠道成功");
            return R.ok().data(personalNoTaskChannelList);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

}

