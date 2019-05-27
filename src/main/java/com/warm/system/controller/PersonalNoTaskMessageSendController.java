package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoOperationStockWechatAccount;
import com.warm.system.entity.PersonalNoPeople;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskMessageSend;
import com.warm.system.service.db1.PersonalNoPeopleService;
import com.warm.system.service.db1.PersonalNoTaskMessageSendService;
import com.warm.system.service.db1.PersonalNoTaskService;
import com.warm.system.service.db2.PersonalNoOperationStockWechatAccountService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import com.warm.utils.WebConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
    @Autowired
    private PersonalNoPeopleService peopleService;
    @Autowired
    private PersonalNoOperationStockWechatAccountService wechatAccountService;
    //指定唯一锁
    private Lock lock = new ReentrantLock();    //注意这个地方

    private String DBTask = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task);
    private String DBPeople = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_people);
    private String DBWeChat = DB.DBAndTable(DB.OA,DB.operation_stock_wechat_account);

    @ApiOperation(value = "新增任务消息")
    @PostMapping(value = "taskMessageSend")
    public R taskMessageSend(
            @ApiParam(name = "personalNoTaskMessageSend", value = "要插入的任务消息", required = true)
            @RequestBody PersonalNoTaskMessageSend personalNoTaskMessageSend
    ){
        if(lock.tryLock()) {
            try {
                log.info("开始添加任务消息");
                String getSql = DaoGetSql.getSql("SELECT * from " + DBTask + " where id = ?", personalNoTaskMessageSend.getPersonaNoTaskId());
                Sql sql = new Sql(getSql);
                PersonalNoTask noTask = noTaskService.getBySql(sql);
                if (VerifyUtils.isEmpty(noTask)) {
                    log.info("未找到任务，添加失败");
                    return R.error().message("没有此任务");
                }
                if(VerifyUtils.isEmpty(personalNoTaskMessageSend.getStartTime())){
                    getSql = DaoGetSql.getSql("SELECT * from " + DBPeople + " where personal_task_id = ? and flag = 2 and deleted = 0", noTask.getId());
                }else {
                    getSql = DaoGetSql.getSql("SELECT * from " + DBPeople + " where personal_task_id = ? and be_friend_time  BETWEEN ? and ? and flag = 2 and deleted = 0", noTask.getId(),WebConst.getNowDate(personalNoTaskMessageSend.getStartTime()),WebConst.getNowDate(personalNoTaskMessageSend.getEndTime()));
                }
                List<PersonalNoPeople> list = peopleService.list(getSql);
                List<PersonalNoPeople> peopleList = new ArrayList<>();
                for (PersonalNoPeople people : list) {
                    getSql = DaoGetSql.getSql("SELECT * from "+DBWeChat+" where wx_id = ? and  operation_project_instance_id = ? limit 0,1",people.getPersonalFriendWxId(), G.ms_OPERATION_PROJECT_INSTANCE_ID);
                    sql.setSql(getSql);
                    PersonalNoOperationStockWechatAccount wechatAccount = wechatAccountService.getBySql(sql);
                    if(!VerifyUtils.isEmpty(wechatAccount) && !WebConst.WECHATSTATUS.equals(wechatAccount.getStatus())){
                        continue;
                    }
                    peopleList.add(people);
                }
                if (VerifyUtils.collectionIsEmpty(peopleList)) {
                    return R.error().message("选择的好友数为0");
                }
                log.info("开始向数据库插入任务消息");
                boolean b = personalNoTaskMessageSendService.insertPersonalNoTaskMessage(personalNoTaskMessageSend, noTask, peopleList);
                if (!b) {
                    log.info("添加任务消息失败");
                    return R.error().message("插入个人号消息失败");
                }
                log.info("添加任务消息成功");
                return R.ok();
            } catch (Exception e) {
                e.printStackTrace();
                return R.error().message("网页走丢了，请返回重试。。。");
            }finally {
                lock.unlock();
            }
        }else {
            return R.error().message("服务器正忙，请稍等一会再点击添加");
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
            return R.error().message("网页走丢了，请返回重试。。。");
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
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

}

