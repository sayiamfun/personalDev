package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.R;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskMessageSend;
import com.warm.system.service.db1.PersonalNoTaskMessageSendService;
import com.warm.system.service.db1.PersonalNoTaskService;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@CrossOrigin //跨域
@Api(description = "任务消息发送管理")
@RestController
@RequestMapping("/personalNoTaskMessageSend")
public class PersonalNoTaskMessageSendController {
    public static Log log = LogFactory.getLog(PersonalNoTaskMessageSendController.class);

    @Autowired
    private PersonalNoTaskService noTaskService;
    @Autowired
    private PersonalNoTaskMessageSendService personalNoTaskMessageSendService;

    @ApiOperation(value = "新增任务消息")
    @PostMapping(value = "taskMessageSend")
    public R taskMessageSend(
            @ApiParam(name = "personalNoTaskMessageSend", value = "要插入的任务消息", required = true)
            @RequestBody PersonalNoTaskMessageSend personalNoTaskMessageSend,
            HttpServletRequest request
    ){
        try {
            log.info("开始添加任务消息");
            PersonalNoTask noTask = noTaskService.selectById(personalNoTaskMessageSend.getPersonaNoTaskId());
            if(VerifyUtils.isEmpty(noTask)){
                log.info("未找到任务，添加失败");
                return R.error().message("没有此任务");
            }
            int num = personalNoTaskMessageSendService.getNum(personalNoTaskMessageSend);
            if(num<=0){
                return R.error().message("选择的好友数为0");
            }
            log.info("开始向数据库插入任务消息");
            boolean b = personalNoTaskMessageSendService.insertPersonalNoTaskMessage(personalNoTaskMessageSend , noTask);
            if(!b){
                log.info("添加任务消息失败");
                return R.error().message("插入个人号消息失败");
            }
            log.info("添加任务消息成功");
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "分页查找任务消息")
    @PostMapping(value = "/{pageNum}/{size}/")
    public R listtaskMessageSend(
            @ApiParam(name = "pageNum", value = "当前页码", required = true)
            @PathVariable Long pageNum,

            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable Long size
    ){
        try {
            log.info("开始分页查找任务消息");
            Page<PersonalNoTaskMessageSend> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            page = personalNoTaskMessageSendService.pageQuery(page,null);
            log.info("分页查找任务消息结束");
            return R.ok().data(page);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "根据ID查询任务消息")
    @PostMapping(value = "/{id}/")
    public R getTaskMessageById(
            @ApiParam(name = "id", value = "任务消息id", required = true)
            @PathVariable Long id
    ){
        try {
            log.info("根据id查询任务消息");
            PersonalNoTaskMessageSend taskMessage = personalNoTaskMessageSendService.getTaskMessageById(id);
            log.info("根据id查询任务消息结束");
            return R.ok().data(taskMessage);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

}

