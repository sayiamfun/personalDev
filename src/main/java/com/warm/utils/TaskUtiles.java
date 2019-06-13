package com.warm.utils;


import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.entity.robot.common.SunTaskType;
import com.warm.system.entity.*;
import com.warm.system.service.db1.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskUtiles {
    public static Log log = LogFactory.getLog(TaskUtiles.class);

    private static String DBTask = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_phone_task);
    private static String DBRemindFlag = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_task_remind_flag);
    private static String DBTaskGroup = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_phone_task_group);
    private static String DBTaskLable = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_task_lable);


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
        PersonalNoPhoneTaskGroupService taskGroupService = (PersonalNoPhoneTaskGroupService) map.get("taskGroupService");
        String getsql = "SELECT * FROM "+DBTaskGroup+" where tname = '"+personalWxId+"发送回复消息"+userWxId+"' and create_time > '"+WebConst.getNowDate(new Date(new Date().getTime()-600000))+"' and status = '未下发' order by id desc limit 0,1";
        PersonalNoPhoneTaskGroup taskGroup = taskGroupService.getOne(getsql);
        if(VerifyUtils.isEmpty(taskGroup)) {
            insertTaskGroup(personalWxId, userWxId, map, taskId, time);
        }
    }

    private static void insertTaskGroup(String personalWxId, String userWxId, Map<String, Object> map, Integer taskId, Integer time) {
        PersonalNoPhoneTaskGroupService taskGroupService = (PersonalNoPhoneTaskGroupService) map.get("taskGroupService");
        PersonalNoTaskService noTaskService = (PersonalNoTaskService) map.get("noTaskService");
        PersonalNoPhoneTaskService taskService = (PersonalNoPhoneTaskService) map.get("TaskService");
        PersonalNoTask taskById = noTaskService.getTaskById(taskId);
        if (!VerifyUtils.isEmpty(taskById)) {
            log.info("将回复信息转换为任务任务组");
            PersonalNoPhoneTaskGroup taskGroup = new PersonalNoPhoneTaskGroup();
            taskGroup.setNextStep(1);
            taskGroup.setTaskOrder(9);
            taskGroup.setStatus("未下发");
            taskGroup.setCreateTime(new Date(new Date().getTime() + time.longValue()));
            taskGroup.setTname(personalWxId + "发送回复消息" + userWxId);
            taskGroup.setCurrentRobotId(personalWxId);
            taskGroup.setTotalStep(taskById.getNoTaskReplyContentList().size());
            taskGroup.setTaskSendId(taskId);
            taskGroup.setDb(DBTaskGroup);
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
                    task.setDb(DBTask);
                    int save1 = taskService.add(task);
                    if (save1 == 0) {
                        log.info("插入任务失败");
                        throw new RuntimeException("插入任务失败");
                    }
                }
            }
        }
    }

    //开课提醒任务
    public static void toRemindTask(Map<String, Object> map, PersonalNoTaskRemindFlagService remindFlagService, String personalNoWxId, String personalFriendWxId, Integer personalTaskId, int i) {
        PersonalNoTaskService noTaskService = (PersonalNoTaskService) map.get("noTaskService");
        PersonalNoTask taskById = noTaskService.getTaskById(personalTaskId);
        log.info("判断是否有开课提醒");
        if (taskById == null || "0".equals(taskById.getAutoRemind())) {
            return;
        }
        log.info("判断是否已经发送过开课提醒");
        String getSql = DaoGetSql.getSql("SELECT * from " + DBRemindFlag + " where personal_no_wx_id = ? and user_wx_id = ? and personal_no_task_id = ? limit 0,1", personalNoWxId, personalFriendWxId, personalTaskId);
        Sql sql = new Sql(getSql);
        PersonalNoTaskRemindFlag remindFlag = remindFlagService.getBySql(sql);
        if (!VerifyUtils.isEmpty(remindFlag)) {
            return;
        }
        log.info("判断是否已经过了开课时间");
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
            taskGroup.setDb(DBTaskGroup);
            int save = taskGroupService.add(taskGroup);
            if (save != 0) {
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
                    task.setDb(DBTask);
                    int save1 = taskService.add(task);
                    if (save1 == 0) {
                        log.info("插入任务失败");
                        throw new RuntimeException("插入任务失败");
                    }
                }
            }
            remindFlag = new PersonalNoTaskRemindFlag();
            remindFlag.setPersonalNoWxId(personalNoWxId);
            remindFlag.setUserWxId(personalFriendWxId);
            remindFlag.setPersonalNoTaskId(personalTaskId);
            remindFlag.setDb(DBRemindFlag);
            remindFlagService.add(remindFlag);
        }
    }

    public static void toAddLable(Map<String, Object> map1, String personalNoWxId, String personalFriendWxId,Integer taskId,  int time) {
        String getSql = DaoGetSql.getSql("SELECT `lable_name` FROM "+DBTaskLable+" WHERE `personal_no_task_id` = ? AND deleted = 0",taskId);
        Sql sql = new Sql(getSql);
        PersonalNoTaskLableService taskLableService = (PersonalNoTaskLableService) map1.get("taskLableService");
        List<String> taskLableList = taskLableService.listStringBySql(sql);
        if (!VerifyUtils.collectionIsEmpty(taskLableList)) {
            PersonalNoPhoneTaskGroupService taskGroupService = (PersonalNoPhoneTaskGroupService) map1.get("taskGroupService");
            PersonalNoPhoneTaskService taskService = (PersonalNoPhoneTaskService) map1.get("TaskService");
            PersonalNoPhoneTaskGroup taskGroup = new PersonalNoPhoneTaskGroup();
            taskGroup.setTaskOrder(0);
            taskGroup.setCreateTime(new Date());
            taskGroup.setTname(personalNoWxId + "给好友" + personalFriendWxId + "添加标签");
            taskGroup.setTotalStep(taskLableList.size());
            taskGroup.setNextStep(1);
            taskGroup.setCurrentRobotId(personalNoWxId);
            taskGroup.setStatus("未下发");
            taskGroup.setDb(DBTaskGroup);
            boolean save = taskGroupService.add(taskGroup) > 0;
            if (save) {
                for (int j = 0; j < taskLableList.size(); j++) {
                    PersonalNoPhoneTask task = new PersonalNoPhoneTask();
                    task.setStep(j + 1);
                    task.setTaskGroupId(taskGroup.getId());
                    task.setTaskType(SunTaskType.FRIEND_ADD_LABEL);
                    task.setRobotId(personalFriendWxId);
                    task.setStatus("未下发");
                    task.setTname(personalNoWxId + "给好友" + personalFriendWxId + "添加标签" + taskLableList.get(j));
                    task.setCreateTime(new Date());
                    task.setContent(taskLableList.get(j));
                    task.setDb(DBTask);
                    boolean save1 = taskService.add(task) > 0;
                    if (!save1) {
                        log.error("添加粉丝标签任务失败");
                        throw new RuntimeException("添加粉丝标签任务失败");
                    }
                }
            }
        }
    }
}
