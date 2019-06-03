package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.entity.robot.G;
import com.warm.entity.robot.common.SunTaskType;
import com.warm.system.entity.*;
import com.warm.system.mapper.PersonalNoTaskMessageSendMapper;
import com.warm.system.service.db1.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db2.PersonalNoOperationStockWechatAccountService;
import com.warm.system.service.db3.PersonalNoGroupCategoryService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import com.warm.utils.WebConst;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Service
public class PersonalNoTaskMessageSendServiceImpl extends ServiceImpl<PersonalNoTaskMessageSendMapper, PersonalNoTaskMessageSend> implements PersonalNoTaskMessageSendService {
    private static Log log = LogFactory.getLog(PersonalNoTaskMessageSendServiceImpl.class);
    @Autowired
    private PersonalNoTaskMessageSendContentService personalNoTaskMessageSendContentService;
    @Autowired
    private PersonalNoPhoneTaskGroupService taskGroupService;
    @Autowired
    private PersonalNoPhoneTaskService taskService;
    @Autowired
    private PersonalNoGroupCategoryService groupCategoryService;
    @Autowired
    private PersonalNoPeopleService peopleService;
    @Autowired
    private PersonalNoTaskMessageSendMapper taskMessageSendMapper;

    private String DBTaskGroup = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_phone_task_group);
    private String DBTaskGroupFinish = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_phone_task_group_finish);
    private String DBTask = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_phone_task);
    private String DBTaskMessageContent = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_message_send_content);
    private String DBTaskMessage = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_message_send);
    private Date taskDate = new Date();
    /*
     * 插入个人号任务消息
     */
    @Transactional
    @Override
    public boolean insertPersonalNoTaskMessage(PersonalNoTaskMessageSend personalNoTaskMessageSend , PersonalNoTask noTask, List<PersonalNoPeople> peopleList) {
        Date sendTime = personalNoTaskMessageSend.getSendTime();
        log.info("数据库开始添加任务消息");
        log.info("将任务信息   添加到   任务消息中");
        personalNoTaskMessageSend.setPersonalNoTaskMessageName(noTask.getTaskName());
        List<PersonalNoTaskMessageSendContent> personalNoTaskMessageSendContentList = personalNoTaskMessageSend.getPersonalNoTaskMessageSendContentList();
        //做判断，生成格式：2（1条文字，1条邀请入群）
        log.info("开始生成任务消息内容预览");
        String contentShow = WebConst.getTaskContentShow(personalNoTaskMessageSendContentList);
        log.info("生成任务消息内容预览成功");
        personalNoTaskMessageSend.setPersonalNoTaskMessageContentShow(contentShow);
        personalNoTaskMessageSend.setPersonaNolTaskMessageStatus("0"+"/"+peopleList.size());
        int insert = 0;
        if(VerifyUtils.isEmpty(personalNoTaskMessageSend.getId())){
            log.info("数据库添加任务消息");
            if(VerifyUtils.isEmpty(sendTime)){
                personalNoTaskMessageSend.setSendTime(new Date());
            }
            personalNoTaskMessageSend.setDeleted(0);
            personalNoTaskMessageSend.setDb(DBTaskMessage);
            insert = taskMessageSendMapper.add(personalNoTaskMessageSend);
        }else {
            log.info("数据库修改任务消息");
            insert = baseMapper.updateById(personalNoTaskMessageSend);
            log.info("修改任务消息，将任务表内相关的任务组和任务删除");
            log.info("根据任务消息id查询所有的任务组");
            String sql = DaoGetSql.getSql("SELECT * FROM " + DBTaskGroup + " WHERE task_send_id = ?", personalNoTaskMessageSend.getId());
            List<PersonalNoPhoneTaskGroup> taskGroupList = taskGroupService.list(sql);
            String deleteSql = "";
            for (PersonalNoPhoneTaskGroup taskGroup : taskGroupList) {
                log.info("根据任务组id删除所有的子任务");
                deleteSql = "delete from "+DBTask+" where task_group_id = "+taskGroup.getId();
                taskService.delete(deleteSql);
                deleteSql = "delete from "+DBTaskGroup+" where id = "+taskGroup.getId();
                taskGroupService.delete(deleteSql);
            }
        }
        if(insert != 1){
            log.info("数据库添加任务消息失败");
            return false;
        }
        personalNoTaskMessageSendContentService.batchSave(personalNoTaskMessageSend);
        //------------------------------------------------处理任务组和任务
        log.info("数据库添加任务消息内容到数据库成功");
        log.info("数据库开始将任务消息转换为  手机将要下发的任务组和任务");
        log.info("任务相关个人号");
        Map<String,List<String>> map = peopleService.MapByPeopleList(peopleList);
        String getSql = "";
        for (Map.Entry<String, List<String>> stringListEntry : map.entrySet()) {
            String key = stringListEntry.getKey();
            if (!VerifyUtils.isEmpty(sendTime)) {
                log.info("定时任务");
                taskDate = personalNoTaskMessageSend.getSendTime();
            } else {
                taskDate = new Date();
                log.info("即时任务");
            }
            getSql = "SELECT * FROM "+DBTaskGroup+" where current_robot_id = '"+key+"' and `status` = '未下发' and message_send_id = 1";
            List<PersonalNoPhoneTaskGroup> list = taskGroupService.list(getSql);
            for (String userWxId : map.get(key)) {
                log.info("筛选30秒内是否已有任务，没有则插入，有则判断下一个时间节点");
                if(VerifyUtils.isEmpty(list)){
                    list = new ArrayList<PersonalNoPhoneTaskGroup>();
                }
                Stream<PersonalNoPhoneTaskGroup> stream = list.stream();
                Long count = stream.filter(taskGroup -> taskGroup.getCreateTime().getTime() - taskDate.getTime() < 30000).filter(taskGroup -> taskDate.getTime() - taskGroup.getCreateTime().getTime() < 30000).count();
                while (count.intValue()!=0) {
                    taskDate = new Date(taskDate.getTime()+30000);
                    stream = list.stream();
                    count = stream.filter(taskGroup -> taskGroup.getCreateTime().getTime() - taskDate.getTime() < 30000).filter(taskGroup -> taskDate.getTime() - taskGroup.getCreateTime().getTime() < 30000).count();
                }
                PersonalNoPhoneTaskGroup taskGroup = new PersonalNoPhoneTaskGroup();
                taskGroup.setTname(key + "发送任务消息组给" + userWxId);
                taskGroup.setCurrentRobotId(key);
                taskGroup.setTotalStep(personalNoTaskMessageSendContentList.size());
                taskGroup.setNextStep(1);
                taskGroup.setStatus("未下发");
                taskGroup.setTaskSendId(personalNoTaskMessageSend.getId());
                taskGroup.setCreateTime(new Date());
                taskGroup.setTaskOrder(0);
                taskGroup.setMessageSendId(1);
                taskGroup.setCreateTime(taskDate);
                taskGroup.setDb(DBTaskGroup);
                int save = taskGroupService.add(taskGroup);
                if (save < 0) {
                    throw new RuntimeException("插入" + taskGroup.getTname() + "组失败");
                }
                for (int i = 0; i < personalNoTaskMessageSendContentList.size(); i++) {
                    PersonalNoPhoneTask task = new PersonalNoPhoneTask();
                    task.setTaskGroupId(taskGroup.getId());
                    task.setStatus("未下发");
                    task.setCreateTime(taskDate);
                    task.setRobotId(userWxId);
                    task.setTname(key + "发送第" + (i + 1) + "条任务消息给" + userWxId);
                    task.setStep(i + 1);
                    task.setContentType(personalNoTaskMessageSendContentList.get(i).getContentType());
                    task.setContent(personalNoTaskMessageSendContentList.get(i).getContent());
                    //发送消息
                    task.setTaskType(SunTaskType.FRIEND_SEND_MSG);
                    task.setDb(DBTask);
                    taskService.add(task);
                }
                list.add(taskGroup);
                taskDate = new Date(taskDate.getTime()+30000);
            }
        }
        log.info("插入消息内容成功");
        return true;
    }
    /*
     * 分页查询任务消息
     */
    @Override
    public Page<PersonalNoTaskMessageSend> pageQuery(Page<PersonalNoTaskMessageSend> page, Object o) {
        log.info("数据库分页查找任务消息开始");
        EntityWrapper<PersonalNoTaskMessageSend> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        List<PersonalNoTaskMessageSend> personalNoTaskMessageSends = baseMapper.selectPage(page, entityWrapper);
        page.setRecords(personalNoTaskMessageSends);
        if(!VerifyUtils.collectionIsEmpty(page.getRecords())){
            log.info("查询每条任务消息的消息内容开始");
            for (PersonalNoTaskMessageSend record : page.getRecords()) {
                if(!"已完成".equals(record.getPersonaNolTaskMessageStatus())){
                    Long count = taskGroupService.getCount("SELECT COUNT(*) FROM " + DBTaskGroupFinish + " where `status` = '已完成' and task_send_id = " + record.getId());
                    String[] split = record.getPersonaNolTaskMessageStatus().split("/");
                    if(count.intValue()>=Integer.parseInt(split[1])){
                        record.setPersonaNolTaskMessageStatus("已完成");
                        record.setDb(DBTaskMessage);
                        taskMessageSendMapper.updateOne(record);
                    }else {
                        record.setPersonaNolTaskMessageStatus("" + count.intValue() + "/" + split[1]);
                    }
                    String getSql = DaoGetSql.getSql("SELECT * FROM " + DBTaskMessageContent + " where personal_no_task_message_send_id = ?", record.getId());
                    List<PersonalNoTaskMessageSendContent> taskMessageSendContentList = personalNoTaskMessageSendContentService.listBySql(new Sql(getSql));
                    PersonalNoGroupCategory personalNoGroupCategory = null;
                    for (PersonalNoTaskMessageSendContent personalNoTaskMessageSendContent : taskMessageSendContentList) {
                        if("邀请入群".equals(personalNoTaskMessageSendContent.getContentType())){
                            personalNoGroupCategory = groupCategoryService.getPersonalNoGroupCategory(personalNoTaskMessageSendContent.getContent());
                        }
                    }
                    record.setGroupName(VerifyUtils.isEmpty(personalNoGroupCategory)?"":personalNoGroupCategory.getCname());
                    record.setPersonalNoTaskMessageSendContentList(taskMessageSendContentList);
                }
            }
            log.info("查询每条任务消息的消息内容结束");
        }
        log.info("数据库分页查找任务消息结束");
        page.setTotal(personalNoTaskMessageSends.size());
        return page;
    }

    /**
     * 根据id查询任务消息
     * @param id
     * @return
     */
    @Override
    public PersonalNoTaskMessageSend getTaskMessageById(Long id) {
        log.info("数据库根据id查询任务消息开始");
        PersonalNoTaskMessageSend personalNoTaskMessageSend = baseMapper.selectById(id);
        if(!VerifyUtils.isEmpty(personalNoTaskMessageSend)){
            log.info("根据任务消息id查询任务消息内容");
            String getSql = DaoGetSql.getSql("SELECT * FROM " + DBTaskMessageContent + " where personal_no_task_message_send_id = ?", personalNoTaskMessageSend.getId());
            List<PersonalNoTaskMessageSendContent> taskMessageSendContentList = personalNoTaskMessageSendContentService.listBySql(new Sql(getSql));
            for (PersonalNoTaskMessageSendContent personalNoTaskMessageSendContent : taskMessageSendContentList) {
                if("邀请入群".equals(personalNoTaskMessageSendContent.getContentType())){
                    PersonalNoGroupCategory personalNoGroupCategory = groupCategoryService.getPersonalNoGroupCategory(personalNoTaskMessageSendContent.getContent());
                    personalNoTaskMessageSend.setGroupName(personalNoGroupCategory==null?"":personalNoGroupCategory.getCname());
                }
            }
            personalNoTaskMessageSend.setPersonalNoTaskMessageSendContentList(taskMessageSendContentList);
        }
        log.info("数据库根据id查询任务消息结束");
        return personalNoTaskMessageSend;
    }

}
