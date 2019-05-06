package com.warm.utils;


import com.warm.entity.DB;
import com.warm.system.entity.*;
import com.warm.system.service.db1.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TaskUtiles {
    public static Log log = LogFactory.getLog(TaskUtiles.class);

    private static String ZCDBTask = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_phone_task);
    private static String ZCDBPeople = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_people);

    //下发任务
    public static Map<String, Object> getMap(PersonalNoPeopleService peopleService, PersonalNoPhoneTaskGroupService taskGroupService, PersonalNoTaskService noTaskService, PersonalNoPhoneTaskService taskService, PersonalNoKeywordService keywordService) {
        Map<String, Object> map = new HashMap<>();
        map.put("peopleService", peopleService);
        map.put("taskGroupService", taskGroupService);
        map.put("noTaskService", noTaskService);
        map.put("TaskService", taskService);
        map.put("keywordService", keywordService);
        return map;
    }
    //将回复消息转换为任务组
    public static void toTask(Map<String, Object> map, String personalWxId, String userWxId, Integer taskId, Integer time) {
        PersonalNoPeopleService peopleService = (PersonalNoPeopleService) map.get("peopleService");
        log.info("根据任务id获取要发送的任务信息");
        if(VerifyUtils.isEmpty(taskId)){
            String sql = DaoGetSql.getSql("SELECT id,personal_friend_wx_id,personal_task_id,personal_no_wx_id,channel_id,flag,be_friend_time,remarks,personal_friend_nick_name,deleted FROM " + ZCDBPeople + " WHERE personal_no_wx_id = ? and personal_friend_wx_id = ? and deleted = 0 order by id desc LIMIT 0,1", personalWxId, userWxId);
            PersonalNoPeople people = peopleService.getOne(sql);
            if(!VerifyUtils.isEmpty(people)) {
                insertTaskGroup(people.getPersonalNoWxId(), people.getPersonalFriendWxId(), map, people.getPersonalTaskId(), time);
            }
        }else {
            insertTaskGroup(personalWxId, userWxId, map, taskId, time);
        }
    }
    private static void insertTaskGroup(String personalWxId, String userWxId, Map<String, Object> map, Integer taskId, Integer time) {
        PersonalNoPhoneTaskGroupService taskGroupService = (PersonalNoPhoneTaskGroupService) map.get("taskGroupService");
        PersonalNoTaskService noTaskService = (PersonalNoTaskService) map.get("noTaskService");
        PersonalNoPhoneTaskService taskService = (PersonalNoPhoneTaskService) map.get("TaskService");
        PersonalNoTask taskById = noTaskService.getTaskById(taskId);
        if(!VerifyUtils.isEmpty(taskById)) {
            log.info("将回复信息转换为任务任务组");
            PersonalNoPhoneTaskGroup taskGroup = new PersonalNoPhoneTaskGroup();
            taskGroup.setNextStep(1);
            taskGroup.setTaskOrder(9);
            taskGroup.setStatus("未下发");

            taskGroup.setCreateTime(new Date(new Date().getTime()+ time.longValue()));
            taskGroup.setTname(personalWxId + "发送回复消息" + userWxId);
            taskGroup.setCurrentRobotId(personalWxId);
            taskGroup.setTotalStep(taskById.getNoTaskReplyContentList().size());
            taskGroup.setDb(DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_phone_task_group));
            int save = taskGroupService.add(taskGroup);
            if (save != 0) {
                log.info("开始添加任务");
                for (int j = 0; j < taskById.getNoTaskReplyContentList().size(); j++) {
                    PersonalNoPhoneTask task = new PersonalNoPhoneTask();
                    if ("邀请入群".equals(taskById.getNoTaskReplyContentList().get(j).getContentType())) {
                        task.setContent(taskById.getNoTaskReplyContentList().get(j).getContent());
                        task.setContentType(taskById.getNoTaskReplyContentList().get(j).getContentType());
                        task.setStep(j + 1);
                        task.setRobotId(userWxId);
                        task.setCreateTime(new Date());
                        task.setStatus("未下发");
                        task.setTaskType(1102);
                        task.setTname(personalWxId + "发送入群邀请给" + userWxId);
                        task.setTaskGroupId(taskGroup.getId());
                    } else {
                        task.setTname(personalWxId + "发送自动回复消息给" + userWxId);
                        task.setTaskGroupId(taskGroup.getId());
                        task.setContent(taskById.getNoTaskReplyContentList().get(j).getContent());
                        task.setContentType(taskById.getNoTaskReplyContentList().get(j).getContentType());
                        task.setStep(j + 1);
                        task.setStatus("未下发");
                        task.setTaskType(100);
                        task.setRobotId(userWxId);
                        task.setCreateTime(new Date());
                    }
                    task.setDb(ZCDBTask);
                    int save1 = taskService.add(task);
                    if (save1 == 0) {
                        log.info("插入任务失败");
                        throw new RuntimeException("插入任务失败");
                    }
                }
            }
        }
    }
    //将关键字消息转换为任务组
    public static void toKeyWordTask(Map<String, Object> map, String s, String fromUsername, Integer i, Integer parseInt) {
        PersonalNoKeywordService keywordService = (PersonalNoKeywordService) map.get("keywordService");
        PersonalNoPhoneTaskGroupService taskGroupService = (PersonalNoPhoneTaskGroupService) map.get("taskGroupService");
        PersonalNoPhoneTaskService taskService = (PersonalNoPhoneTaskService) map.get("TaskService");
        String sql = DaoGetSql.getById(DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_keyword), i);
        PersonalNoKeyword one = keywordService.getOne(sql);
        PersonalNoKeyword infoById = keywordService.getInfoById(one);
        if(VerifyUtils.isEmpty(infoById)){
            return;
        }
        log.info("将非通道好友回复消息转换为任务任务组");
        PersonalNoPhoneTaskGroup taskGroup = new PersonalNoPhoneTaskGroup();
        taskGroup.setNextStep(1);
        taskGroup.setCreateTime(new Date(new Date().getTime()+ parseInt.longValue()));
        taskGroup.setTaskOrder(9);
        taskGroup.setStatus("未下发");
        taskGroup.setTname(s + "发送问候消息" + fromUsername);
        taskGroup.setCurrentRobotId(s);
        taskGroup.setTotalStep(infoById.getKeywordContentList().size());
        taskGroup.setDb(DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_phone_task_group));
        int save = taskGroupService.add(taskGroup);
        if (save!=0) {
            log.info("开始添加任务");
            for (int j = 0; j < infoById.getKeywordContentList().size(); j++) {
                PersonalNoPhoneTask task = new PersonalNoPhoneTask();
                if ("邀请入群".equals(infoById.getKeywordContentList().get(j).getContentType())) {
                    task.setContent(infoById.getKeywordContentList().get(j).getContent());
                    task.setContentType(infoById.getKeywordContentList().get(j).getContentType());
                    task.setCreateTime(new Date());
                    task.setStatus("未下发");
                    task.setStep(j + 1);
                    task.setRobotId(fromUsername);
                    task.setTaskType(1102);
                    task.setTname(s + "发送入群邀请给" + fromUsername);
                    task.setTaskGroupId(taskGroup.getId());
                } else {
                    task.setTname(s + "发送问候消息给" + fromUsername);
                    task.setTaskGroupId(taskGroup.getId());
                    task.setContent(infoById.getKeywordContentList().get(j).getContent());
                    task.setContentType(infoById.getKeywordContentList().get(j).getContentType());
                    task.setStep(j + 1);
                    task.setTaskType(100);
                    task.setStatus("未下发");
                    task.setRobotId(fromUsername);
                    task.setCreateTime(new Date());
                }
                task.setDb(ZCDBTask);
                int save1 = taskService.add(task);
                if (save1 == 0) {
                    log.info("插入任务失败");
                    throw new RuntimeException("插入任务失败");
                }
            }
        }
    }
    //开课提醒任务
    public static void toRemindTask(Map<String, Object> map,PersonalNoTaskRemindFlagService remindFlagService, String personalNoWxId, String personalFriendWxId, Integer personalTaskId, int i) {
        PersonalNoTaskService noTaskService = (PersonalNoTaskService) map.get("noTaskService");
        PersonalNoTask taskById = noTaskService.getTaskById(personalTaskId);
        log.info("判断是否需要下发");
        if (taskById == null || "0".equals(taskById.getAutoRemind())) {
            return;
        }
        if (new Date().getTime() - taskById.getTaskStartTime().getTime() > 0) {
            return;
        }
        if (!VerifyUtils.collectionIsEmpty(taskById.getNoTaskBeginRemindList())) {
            PersonalNoPhoneTaskGroupService taskGroupService = (PersonalNoPhoneTaskGroupService) map.get("taskGroupService");
            PersonalNoPhoneTaskService taskService = (PersonalNoPhoneTaskService) map.get("TaskService");
            PersonalNoPhoneTaskGroup taskGroup = new PersonalNoPhoneTaskGroup();
            taskGroup.setCreateTime(new Date(taskById.getTaskStartTime().getTime() - Integer.parseInt(taskById.getAutoRemind()) * 60 * 1000));
            taskGroup.setNextStep(1);
            taskGroup.setTaskOrder(0);
            taskGroup.setStatus("未下发");
            taskGroup.setTname(personalNoWxId + "发送开课提醒消息" + personalFriendWxId);
            taskGroup.setCurrentRobotId(personalNoWxId);
            taskGroup.setTotalStep(taskById.getNoTaskBeginRemindList().size());
            taskGroup.setDb(DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_phone_task_group));
            int save = taskGroupService.add(taskGroup);
            if (save!= 0) {
                log.info("开始添加任务");
                for (int j = 0; j < taskById.getNoTaskBeginRemindList().size(); j++) {
                    PersonalNoPhoneTask task = new PersonalNoPhoneTask();
                    if ("邀请入群".equals(taskById.getNoTaskBeginRemindList().get(j).getContentType())) {
                        task.setContent(taskById.getNoTaskBeginRemindList().get(j).getContent());
                        task.setContentType(taskById.getNoTaskBeginRemindList().get(j).getContentType());
                        task.setCreateTime(new Date());
                        task.setRobotId(personalFriendWxId);
                        task.setStatus("未下发");
                        task.setStep(j + 1);
                        task.setTaskType(1102);
                        task.setTname(personalNoWxId + "发送入群邀请给" + personalFriendWxId);
                        task.setTaskGroupId(taskGroup.getId());
                    } else {
                        task.setTname(personalNoWxId + "发送消息给" + personalFriendWxId);
                        task.setTaskGroupId(taskGroup.getId());
                        task.setContent(taskById.getNoTaskBeginRemindList().get(j).getContent());
                        task.setContentType(taskById.getNoTaskBeginRemindList().get(j).getContentType());
                        task.setStep(j + 1);
                        task.setTaskType(100);
                        task.setRobotId(personalFriendWxId);
                        task.setCreateTime(new Date());
                        task.setStatus("未下发");
                    }
                    task.setDb(ZCDBTask);
                    int save1 = taskService.add(task);
                    if (save1 == 0) {
                        log.info("插入任务失败");
                        throw new RuntimeException("插入任务失败");
                    }
                }
            }
            PersonalNoTaskRemindFlag remindFlag = new PersonalNoTaskRemindFlag();
            remindFlag.setPersonalNoWxId(personalNoWxId);
            remindFlag.setUserWxId(personalFriendWxId);
            remindFlag.setPersonalNoTaskId(personalTaskId);
            remindFlagService.insert(remindFlag);
        }
    }
}
