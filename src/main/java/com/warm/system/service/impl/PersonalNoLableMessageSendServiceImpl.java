package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.entity.requre.PeopleNumReq;
import com.warm.entity.robot.G;
import com.warm.entity.robot.common.SunTaskType;
import com.warm.system.entity.*;
import com.warm.system.mapper.PersonalNoLableMessageSendMapper;
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
 * 服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Service
public class PersonalNoLableMessageSendServiceImpl extends ServiceImpl<PersonalNoLableMessageSendMapper, PersonalNoLableMessageSend> implements PersonalNoLableMessageSendService {
    public static Log log = LogFactory.getLog(PersonalNoLableMessageSendServiceImpl.class);

    @Autowired
    private PersonalNoLableMessageSendContentService personalNoLableMessageSendContentService;
    @Autowired
    private PersonalNoPeopleService taskPeopleService;
    @Autowired
    private PersonalNoPhoneTaskGroupService taskGroupService;
    @Autowired
    private PersonalNoPhoneTaskService taskService;
    @Autowired
    private PersonalNoGroupCategoryService groupCategoryService;
    @Autowired
    private PersonalNoLableMessageSendMapper lableMessageSendMapper;
    @Autowired
    private PersonalNoLableService lableService;


    private String DBLableMessageContent = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_lable_message_send_content);
    private String DBLableMessage = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_lable_message_send);
    private String DBTask = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_phone_task);
    private String DBTaskGroup = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_phone_task_group);
    private String DBTaskGroupFinish = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_phone_task_group_finish);
    private String DBLable = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_lable);

    private Date taskDate = new Date();

    /*
     * 添加标签消息
     */
    @Transactional
    @Override
    public boolean insertLableMessage(PersonalNoLableMessageSend personalNoLableMessageSend) {
        Date sendTime = personalNoLableMessageSend.getSendTime();
        log.info("获取标签下任务相关个人号的任务好友");
        PeopleNumReq peopleNumReq = new PeopleNumReq();
        peopleNumReq.setLableIdList(personalNoLableMessageSend.getLableList());
        peopleNumReq.setNoWxIdList(personalNoLableMessageSend.getNoWxIdList());
        List<PersonalNoPeople> peopleList = taskPeopleService.listByLableAndPersonal(peopleNumReq);
        log.info("开始添加标签消息到数据库");
        personalNoLableMessageSend.setPersonaNolLableMessageStatus("0/" + peopleList.size());
        List<PersonalNoLableMessageSendContent> personalNoLableMessageSendContentList = personalNoLableMessageSend.getPersonalNoLableMessageSendContentList();
        log.info("构建标签消息内容预览");
        String lableContentShow = WebConst.getLableContentShow(personalNoLableMessageSendContentList);
        personalNoLableMessageSend.setPersonalNoLableMessageContentShow(lableContentShow);
        log.info("构建标签列表集合开始");
        String lableIds = DaoGetSql.getIds(personalNoLableMessageSend.getLableList());
        String getSql = "SELECT lable_name FROM " + DBLable + " where id in " + lableIds;
        List<String> lableNameList = lableService.listString(getSql);
        String lableNames = WebConst.getNames(lableNameList);
        personalNoLableMessageSend.setPersonalNoLableMessageLableList(lableNames);
        personalNoLableMessageSend.setDb(DBLableMessage);
        if (VerifyUtils.isEmpty(sendTime)) {
            personalNoLableMessageSend.setSendTime(new Date());
        }
        personalNoLableMessageSend.setDeleted(0);
        lableMessageSendMapper.add(personalNoLableMessageSend);
        log.info("添加标签消息内容");
        personalNoLableMessageSendContentService.batchSave(personalNoLableMessageSend);
        //---------------------------------------------------------------------------
        log.info("将添加的标签消息任务拆解为手机请求的任务");
        Map<String, List<String>> map = taskPeopleService.MapByPeopleList(peopleList);
        for (Map.Entry<String, List<String>> stringListEntry : map.entrySet()) {
            String key = stringListEntry.getKey();
            if (!VerifyUtils.isEmpty(sendTime)) {
                log.info("定时任务");
                taskDate = personalNoLableMessageSend.getSendTime();
            } else {
                taskDate = new Date();
                log.info("即时任务");
            }
            getSql = "SELECT * FROM " + DBTaskGroup + " where current_robot_id = '" + key + "' and `status` = '未下发' and message_send_id = 1";
            List<PersonalNoPhoneTaskGroup> list = taskGroupService.list(getSql);
            for (String userWxId : map.get(key)) {
                log.info("筛选30秒内是否已有任务，没有则插入，有则判断下一个时间节点");
                Stream<PersonalNoPhoneTaskGroup> stream = list.stream();
                Long count = stream.filter(taskGroup -> taskGroup.getCreateTime().getTime() - taskDate.getTime() < 30000).filter(taskGroup -> taskDate.getTime() - taskGroup.getCreateTime().getTime() < 30000).count();
                while (count.intValue() != 0) {
                    taskDate = new Date(taskDate.getTime() + 30000);
                    stream = list.stream();
                    count = stream.filter(taskGroup -> taskGroup.getCreateTime().getTime() - taskDate.getTime() < 30000).filter(taskGroup -> taskDate.getTime() - taskGroup.getCreateTime().getTime() < 30000).count();
                }
                PersonalNoPhoneTaskGroup taskGroup = new PersonalNoPhoneTaskGroup();
                taskGroup.setTname(key + "发送标签消息组给" + userWxId);
                taskGroup.setCurrentRobotId(key);
                taskGroup.setTotalStep(personalNoLableMessageSendContentList.size());
                taskGroup.setNextStep(1);
                taskGroup.setStatus("未下发");
                taskGroup.setLableSendId(personalNoLableMessageSend.getId());
                taskGroup.setCreateTime(new Date());
                taskGroup.setTaskOrder(0);
                taskGroup.setMessageSendId(1);
                taskGroup.setCreateTime(taskDate);
                taskGroup.setDb(DBTaskGroup);
                int save = taskGroupService.add(taskGroup);
                if (save < 0) {
                    throw new RuntimeException("插入" + taskGroup.getTname() + "失败");
                }
                for (int i = 0; i < personalNoLableMessageSendContentList.size(); i++) {
                    PersonalNoPhoneTask task = new PersonalNoPhoneTask();
                    if ("邀请入群".equals(personalNoLableMessageSendContentList.get(i).getContentType())) {
                        task.setContent(personalNoLableMessageSendContentList.get(i).getContent());
                        task.setContentType(personalNoLableMessageSendContentList.get(i).getContentType());
                        task.setStep(i + 1);
                        task.setRobotId(userWxId);
                        task.setCreateTime(new Date());
                        task.setStatus("未下发");
                        task.setTaskType(1102);
                        task.setTname(key + "发送第" + (i + 1) + "条标签消息给" + userWxId);
                        task.setTaskGroupId(taskGroup.getId());
                    } else {
                        task.setTaskGroupId(taskGroup.getId());
                        task.setStatus("未下发");
                        task.setCreateTime(taskDate);
                        task.setRobotId(userWxId);
                        task.setTname(key + "发送第" + (i + 1) + "条标签消息给" + userWxId);
                        task.setStep(i + 1);
                        task.setContentType(personalNoLableMessageSendContentList.get(i).getContentType());
                        task.setContent(personalNoLableMessageSendContentList.get(i).getContent());
                        //发送消息
                        task.setTaskType(SunTaskType.FRIEND_SEND_MSG);
                    }
                    task.setDb(DBTask);
                    taskService.add(task);
                }
                list.add(taskGroup);
                taskDate = new Date(taskDate.getTime() + 30000);
            }
        }
        //------------------------------------处理手机任务组和任务结束----------------------------------------
        return true;
    }


    /*
     * 分页查询标签消息列表
     */
    @Override
    public Page<PersonalNoLableMessageSend> pageQuery(Page<PersonalNoLableMessageSend> page, Object o) {
        log.info("分页查找标签消息列表开始");
        String getSql = DaoGetSql.getSql("SELECT count(*) FROM " + DBLableMessage + " where deleted = 0");
        Long count = lableMessageSendMapper.getCount(getSql);
        page.setTotal(count.intValue());
        getSql = DaoGetSql.getSql("SELECT * FROM " + DBLableMessage + " where deleted = 0 order by id desc LIMIT ?,?", page.getOffset(), page.getLimit());
        List<PersonalNoLableMessageSend> personalNoLableMessageSends = lableMessageSendMapper.list(getSql);
        for (PersonalNoLableMessageSend personalNoLableMessageSend : personalNoLableMessageSends) {
            log.info("查询发送数量");
            if (!"已完成".equals(personalNoLableMessageSend.getPersonaNolLableMessageStatus())) {
                int num = 0;
                count = taskGroupService.getCount("SELECT COUNT(*) FROM " + DBTaskGroupFinish + " where `status` = '已完成' and lable_send_id = " + personalNoLableMessageSend.getId());
                num += count;
                count = taskGroupService.getCount("SELECT COUNT(*) FROM " + DBTaskGroup + " where `status` = '已完成' and lable_send_id = " + personalNoLableMessageSend.getId());
                num += count;
                String[] split = personalNoLableMessageSend.getPersonaNolLableMessageStatus().split("/");
                if (num >= Integer.parseInt(split[1])) {
                    personalNoLableMessageSend.setPersonaNolLableMessageStatus("已完成");
                    personalNoLableMessageSend.setDb(DBLableMessage);
                    personalNoLableMessageSend.setDeleted(0);
                    lableMessageSendMapper.updateOne(personalNoLableMessageSend);
                } else {
                    personalNoLableMessageSend.setPersonaNolLableMessageStatus("" + num + "/" + split[1]);
                }
            }
            log.info("获取标签消息内容");
            getSql = DaoGetSql.getSql("select * from " + DBLableMessageContent + " where personal_no_lable_message_send_id = ?", personalNoLableMessageSend.getId());
            List<PersonalNoLableMessageSendContent> lableMessageSendContentList = personalNoLableMessageSendContentService.list(getSql);
            personalNoLableMessageSend.setPersonalNoLableMessageSendContentList(lableMessageSendContentList);
        }
        page.setRecords(personalNoLableMessageSends);
        log.info("分页查找标签消息列表结束");
        return page;
    }

    @Override
    public Integer add(PersonalNoLableMessageSend entity) {
        if (VerifyUtils.isEmpty(entity.getId()))
            return lableMessageSendMapper.add(entity);
        return lableMessageSendMapper.updateOne(entity);
    }

    @Override
    public Integer delete(String sql) {
        return lableMessageSendMapper.delete(sql);
    }

    @Override
    public List<PersonalNoLableMessageSend> list(String sql) {
        return lableMessageSendMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return lableMessageSendMapper.listString(sql);
    }

    @Override
    public PersonalNoLableMessageSend getOne(String sql) {
        return lableMessageSendMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return lableMessageSendMapper.getCount(sql);
    }

    @Override
    public PersonalNoLableMessageSend getLableMessageById(Integer id) {
        String getSql = DaoGetSql.getById(DBLableMessage, id);
        PersonalNoLableMessageSend lableMessageSend = lableMessageSendMapper.getOne(getSql);

        if (!VerifyUtils.isEmpty(lableMessageSend)) {
            String[] split = lableMessageSend.getPersonalNoLableMessageLableList().split("\\|");
            List<String> strings = Arrays.asList(split);
            lableMessageSend.setLableList(strings);
            getSql = DaoGetSql.getSql("select * from " + DBLableMessageContent + " where personal_no_lable_message_send_id = ?", id);
            List<PersonalNoLableMessageSendContent> lableMessageSendContentList = personalNoLableMessageSendContentService.list(getSql);
            lableMessageSend.setPersonalNoLableMessageSendContentList(lableMessageSendContentList);
        }
        return lableMessageSend;
    }

}
