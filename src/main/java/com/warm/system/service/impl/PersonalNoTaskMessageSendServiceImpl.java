package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.robot.common.SunTaskType;
import com.warm.system.entity.*;
import com.warm.system.mapper.PersonalNoTaskMessageSendMapper;
import com.warm.system.service.db1.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
    private PersonalNoTaskPersonalService taskPersonalService;
    @Autowired
    private PersonalNoPeopleService taskPeopleService;
    @Autowired
    private PersonalNoPhoneTaskGroupService taskGroupService;
    @Autowired
    private PersonalNoPhoneTaskService taskService;
    @Autowired
    private PersonalNoGroupCategoryService groupCategoryService;

    private String ZCDBNoPeople = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_people);
    private String ZCDBTaskGroup = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_phone_task_group);
    private String ZCDBTask = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_phone_task);
    /*
     * 插入个人号任务消息
     */
    @Transactional
    @Override
    public boolean insertPersonalNoTaskMessage(PersonalNoTaskMessageSend personalNoTaskMessageSend , PersonalNoTask noTask) {
        Date sendTime = personalNoTaskMessageSend.getSendTime();
        log.info("数据库开始添加任务消息");
        log.info("将任务信息   添加到   任务消息中");
        personalNoTaskMessageSend.setPersonalNoTaskMessageName(noTask.getTaskName());
        personalNoTaskMessageSend.setPersonaNolTaskMessageStatus("0");
        List<PersonalNoTaskMessageSendContent> personalNoTaskMessageSendContentList = personalNoTaskMessageSend.getPersonalNoTaskMessageSendContentList();
        //做判断，生成格式：2（1条文字，1条邀请入群）
        log.info("开始生成任务消息内容预览");
        String contentShow = WebConst.getTaskContentShow(personalNoTaskMessageSendContentList);
        log.info("生成任务消息内容预览成功");
        personalNoTaskMessageSend.setPersonalNoTaskMessageContentShow(contentShow);
        int num = getNum(personalNoTaskMessageSend);
        personalNoTaskMessageSend.setPersonaNolTaskMessageStatus("0"+"/"+num);
        int insert = 0;
        if(VerifyUtils.isEmpty(personalNoTaskMessageSend.getId())){
            log.info("数据库添加任务消息");
            if(VerifyUtils.isEmpty(sendTime)){
                personalNoTaskMessageSend.setSendTime(new Date());
            }
            insert = baseMapper.insert(personalNoTaskMessageSend);
        }else {
            log.info("数据库修改任务消息");
            insert = baseMapper.updateById(personalNoTaskMessageSend);
            log.info("修改任务消息，将任务表内相关的任务组和任务删除");
            log.info("根据任务消息id查询所有的任务组");
            String sql = DaoGetSql.getSql("SELECT * FROM " + ZCDBTaskGroup + " WHERE task_send_id = ?", personalNoTaskMessageSend.getId());
            List<PersonalNoPhoneTaskGroup> taskGroupList = taskGroupService.list(sql);
            String deleteSql = "";
            for (PersonalNoPhoneTaskGroup taskGroup : taskGroupList) {
                log.info("根据任务组id删除所有的子任务");
                deleteSql = "delete from "+ZCDBTask+" where task_group_id = "+taskGroup.getId();
                taskService.delete(deleteSql);
                deleteSql = "delete from "+ZCDBTaskGroup+" where id = "+taskGroup.getId();
                taskGroupService.delete(deleteSql);

            }
        }
        if(insert != 1){
            log.info("数据库添加任务消息失败");
            return false;
        }
        boolean b = personalNoTaskMessageSendContentService.batchSave(personalNoTaskMessageSend);
        //------------------------------------------------处理任务组和任务
        log.info("数据库添加任务消息内容到数据库成功");
        log.info("数据库开始将任务消息转换为  手机将要下发的任务组和任务");
        log.info("任务相关个人号");
        List<PersonalNoTaskPersonal> personalNoTaskPersonals = taskPersonalService.listByTaskId(personalNoTaskMessageSend.getPersonaNoTaskId());
        log.info("任务和个人号下的粉丝微信id");
        List<String> userWxIdSet = new ArrayList<>();
        //根据任务id和个人号微信id查询好友微信id列表
        String sql = "";
        for (PersonalNoTaskPersonal personalNoTaskPersonal : personalNoTaskPersonals) {
            List<String> userWxIdSet1 = getUserWxIdSet(personalNoTaskMessageSend, personalNoTaskPersonal);
            userWxIdSet.addAll(userWxIdSet1);
        }
        log.info("开始转换任务组和任务");
        String getPeoplesql = "";
        for (String s : userWxIdSet) {
            for (PersonalNoTaskPersonal personalNoTaskPersonal : personalNoTaskPersonals) {
                getPeoplesql = DaoGetSql.getSql("select * from "+ZCDBNoPeople+" where personal_no_wx_id = ? and personal_friend_wx_id = ? limit 0,1",personalNoTaskPersonal.getPersonalNoWxId(),s);
                PersonalNoPeople one = taskPeopleService.getOne(getPeoplesql);
                if(!VerifyUtils.isEmpty(one)) {
                    PersonalNoPhoneTaskGroup taskGroup = new PersonalNoPhoneTaskGroup();
                    taskGroup.setTname(personalNoTaskPersonal.getPersonalNoWxId() + "发送任务消息组给" + s);
                    taskGroup.setCurrentRobotId(personalNoTaskPersonal.getPersonalNoWxId());
                    taskGroup.setTotalStep(personalNoTaskMessageSendContentList.size());
                    taskGroup.setNextStep(1);
                    taskGroup.setStatus("未下发");
                    taskGroup.setTaskSendId(personalNoTaskMessageSend.getId());
                    taskGroup.setCreateTime(new Date());
                    if (!VerifyUtils.isEmpty(sendTime)) {
                        log.info("定时任务");
                        taskGroup.setCreateTime(personalNoTaskMessageSend.getSendTime());
                        taskGroup.setTaskOrder(1);
                    } else {
                        log.info("即时任务");
                        taskGroup.setTaskOrder(0);
                    }
                    taskGroup.setTaskSendId(personalNoTaskMessageSend.getId());
                    taskGroup.setDb(ZCDBTaskGroup);
                    int save = taskGroupService.add(taskGroup);
                    if (save == 0) {
                        throw new RuntimeException("插入" + taskGroup.getTname() + "组失败");
                    }
                    for (int i = 0; i < personalNoTaskMessageSendContentList.size(); i++) {
                        PersonalNoPhoneTask task = new PersonalNoPhoneTask();
                        task.setTaskGroupId(taskGroup.getId());
                        task.setStatus("未下发");
                        task.setCreateTime(new Date());
                        task.setRobotId(s);
                        task.setTname(personalNoTaskPersonal.getPersonalNoWxId() + "发送第" + (i + 1) + "条标签消息组给" + s);
                        task.setStep(i + 1);
                        task.setContentType(personalNoTaskMessageSendContentList.get(i).getContentType());
                        task.setContent(personalNoTaskMessageSendContentList.get(i).getContent());
                        //发送消息
                        task.setTaskType(SunTaskType.FRIEND_SEND_MSG);
                        task.setDb(ZCDBTask);
                        int save1 = taskService.add(task);
                        if (save1 == 0) {
                            throw new RuntimeException("插入" + personalNoTaskPersonal.getPersonalNoWxId() + "发送第" + (i + 1) + "条标签消息组给" + s + "失败");
                        }
                    }
                    break;
                }
            }
        }
        log.info("插入消息内容成功");
        //------------------------------处理任务组和任务结束
        return true;
    }

    private List<String> getUserWxIdSet(PersonalNoTaskMessageSend personalNoTaskMessageSend, PersonalNoTaskPersonal personalNoTaskPersonal) {
        String sql;
        List<String> userWxIdSet;
        if (!VerifyUtils.isEmpty(personalNoTaskMessageSend.getStartTime()) && !VerifyUtils.isEmpty(personalNoTaskMessageSend.getEndTime())) {
            sql = DaoGetSql.getSql("SELECT DISTINCT personal_friend_wx_id  FROM " + ZCDBNoPeople + " WHERE personal_task_id = ? AND personal_no_wx_id = ? and be_friend_time between ? and ? and flag <> 0", personalNoTaskMessageSend.getPersonaNoTaskId(), personalNoTaskPersonal.getPersonalNoWxId(), WebConst.getNowDate(personalNoTaskMessageSend.getStartTime()), WebConst.getNowDate(personalNoTaskMessageSend.getEndTime()));
        } else {
            sql = DaoGetSql.getSql("SELECT DISTINCT personal_friend_wx_id  FROM " + ZCDBNoPeople + " WHERE personal_task_id = ? AND personal_no_wx_id = ? and flag <> 0", personalNoTaskMessageSend.getPersonaNoTaskId(), personalNoTaskPersonal.getPersonalNoWxId());
        }
        userWxIdSet = taskPeopleService.listString(sql);
        return userWxIdSet;
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
                List<PersonalNoTaskMessageSendContent> taskMessageSendContentList =  personalNoTaskMessageSendContentService.listByTaskMessageContentId(record.getId());
                record.setPersonalNoTaskMessageSendContentList(taskMessageSendContentList);
            }
            log.info("查询每条任务消息的消息内容结束");
        }
        log.info("数据库分页查找任务消息结束");
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
            List<PersonalNoTaskMessageSendContent> taskMessageSendContentList = personalNoTaskMessageSendContentService.listByTaskMessageContentId(personalNoTaskMessageSend.getId());
            for (PersonalNoTaskMessageSendContent personalNoTaskMessageSendContent : taskMessageSendContentList) {
                if("邀请入群".equals(personalNoTaskMessageSendContent.getContentType())){
                    String[] split = personalNoTaskMessageSendContent.getContent().split("/");
                    PersonalNoGroupCategory personalNoGroupCategory = groupCategoryService.getPersonalNoGroupCategory(split);
                    personalNoTaskMessageSend.setGroupName(personalNoGroupCategory==null?"":personalNoGroupCategory.getCname());
                }
            }
            personalNoTaskMessageSend.setPersonalNoTaskMessageSendContentList(taskMessageSendContentList);
        }
        log.info("数据库根据id查询任务消息结束");
        return personalNoTaskMessageSend;
    }

    @Override
    public int getNum(PersonalNoTaskMessageSend personalNoTaskMessageSend) {
        List<PersonalNoTaskPersonal> personalNoTaskPersonals = taskPersonalService.listByTaskId(personalNoTaskMessageSend.getPersonaNoTaskId());
        String sql = "";
        List<String> resultUserWxIdList = new ArrayList<>();
        for (PersonalNoTaskPersonal personalNoTaskPersonal : personalNoTaskPersonals) {
            List<String> userWxIdList = getUserWxIdSet(personalNoTaskMessageSend, personalNoTaskPersonal);
            resultUserWxIdList.addAll(userWxIdList);
            log.info("去重");
        }
        return resultUserWxIdList.size();
    }

}
