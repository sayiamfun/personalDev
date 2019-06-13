package com.warm.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.entity.query.QueryPersonal;
import com.warm.entity.requre.GetNoEntity;
import com.warm.entity.robot.G;
import com.warm.system.entity.*;
import com.warm.system.mapper.PersonalNoOperationStockWechatAccountMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.*;
import com.warm.system.service.db2.PersonalNoOperationStockWechatAccountService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.TaskUtiles;
import com.warm.utils.VerifyUtils;
import com.warm.utils.WebConst;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Service
public class PersonalNoOperationStockWechatAccountServiceImpl extends ServiceImpl<PersonalNoOperationStockWechatAccountMapper, PersonalNoOperationStockWechatAccount> implements PersonalNoOperationStockWechatAccountService {

    private static Log log = LogFactory.getLog(PersonalNoOperationStockWechatAccountServiceImpl.class);

    @Autowired
    private PersonalNoOperationStockWechatAccountMapper wechatAccountMapper;
    @Autowired
    private PersonalNoTaskService noTaskService;
    @Autowired
    private PassageVisitorRecordService passageVisitorRecordService;
    @Autowired
    private PersonalNoPeopleService peopleService;
    @Autowired
    private PersonalNoKeywordService keywordService;
    @Autowired
    private PersonalNoUserService userService;
    @Autowired
    private PersonalNoValueTableService valueTableService;
    @Autowired
    private PersonalNoTaskRemindFlagService remindFlagService;
    @Autowired
    private ShortUrlService shortUrlService;
    @Autowired
    private PersonalNoPhoneTaskService taskService;
    @Autowired
    private PersonalNoPhoneTaskGroupService taskGroupService;
    @Autowired
    private PersonalNoTaskPersonalService taskPersonalService;
    @Autowired
    private PersonalNoFriendsService noFriendsService;
    @Autowired
    private PersonalNoCategoryAndGroupService categoryAndGroupService;
    @Autowired
    private PersonalNoTaskLableService taskLableService;


    private String DBNoPeople = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_people);
    private String DBFriends = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_friends);
    private String DBPVR = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.passage_visitor_record);
    private String DBWeChat = DB.DBAndTable(DB.OA, DB.operation_stock_wechat_account);
    private String DBTask = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_task);
    private String DBTaskPersonal = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_task_personal);
    private String DBCategoryAndGroup = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_category_and_group);
    private String DBUser = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_user);
    private String DBValueTable = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_value_table);
    private String DBShortUrl = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.short_url);
    private String DBTaskLable = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_task_lable);
    private String DBTaskGroup = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_phone_task_group);

    @Override
    public Integer add(PersonalNoOperationStockWechatAccount entity) {
        if (VerifyUtils.isEmpty(entity.getId()))
            return wechatAccountMapper.add(entity);
        return wechatAccountMapper.updateOne(entity);
    }


    @Override
    public Map<String, Object> getPersonalByTaskId(GetNoEntity getNoEntity) {
        Map<String, Object> map = new HashMap<>();
        PersonalNoTask task = noTaskService.getTaskByIdLess(getNoEntity.getTaskId());
        PersonalNoOperationStockWechatAccount wechatAccount = null;
        String getSql = null;
        Integer shortUrlId = null;
        if (VerifyUtils.isEmpty(task) || task.getActivityType() == 1) {
            map.put("task", task);
            map.put("wechat", wechatAccount);
            return map;
        }
        if (VerifyUtils.isEmpty(getNoEntity.getUnionId())) {
            wechatAccount = getWeChat(task);
        } else {
            log.info("获取短链id，标识好友从哪一个链接进来");
            getSql = DaoGetSql.getSql("SELECT * FROM " + DBPVR + " where union_id = ? order by create_time desc limit 0,1", getNoEntity.getUnionId());
            Sql sql = new Sql(getSql);
            PassageVisitorRecord byUnionId = passageVisitorRecordService.getBySql(sql);
            if (!VerifyUtils.isEmpty(byUnionId)) {
                if (!VerifyUtils.isEmpty(byUnionId) && !VerifyUtils.isEmpty(byUnionId.getTaskId())) {
                    getSql = DaoGetSql.getSql("SELECT id from " + DBShortUrl + " where task_id = ? and channel_id = ? limit 0,1", byUnionId.getTaskId(), byUnionId.getChannelId());
                    sql.setSql(getSql);
                    shortUrlId = VerifyUtils.isEmpty(shortUrlService.getBySql(sql)) ? -1 : shortUrlService.getBySql(sql).getId();
                } else if (!VerifyUtils.isEmpty(byUnionId) && !VerifyUtils.isEmpty(byUnionId.getPassageId())) {
                    getSql = DaoGetSql.getSql("SELECT id from " + DBShortUrl + " where passage_id = ? and channel_id = ? limit 0,1", byUnionId.getPassageId(), byUnionId.getChannelId());
                    sql.setSql(getSql);
                    shortUrlId = VerifyUtils.isEmpty(shortUrlService.getBySql(sql)) ? -1 : shortUrlService.getBySql(sql).getId();
                }
            }
            log.info("封装发送任务的service集合");
            Map<String, Object> map1 = TaskUtiles.getMap(peopleService, taskGroupService, noTaskService, taskService, keywordService);
            map1.put("taskLableService",taskLableService);
            log.info("获取任务的标签，给好友贴标签");
            getSql = DaoGetSql.getSql("SELECT `lable_name` FROM "+DBTaskLable+" WHERE `personal_no_task_id` = ? AND deleted = 0",getNoEntity.getTaskId());
            sql.setSql(getSql);
            List<String> lableList = taskLableService.listStringBySql(sql);
            String lableNames = "";
            if(!VerifyUtils.collectionIsEmpty(lableList)){
                 lableNames = WebConst.getLableNames(lableList);
            }
            log.info("判断用户是否已经扫过任务");
            String getPeopleSql = DaoGetSql.getSql("SELECT * FROM " + DBNoPeople + " WHERE personal_task_id = ? and personal_friend_nick_name = ? and deleted = 0 order by be_friend_time desc LIMIT 0,1", getNoEntity.getTaskId(), getNoEntity.getUnionId());
            PersonalNoPeople people = peopleService.getOne(getPeopleSql);
            if (!VerifyUtils.isEmpty(people) && !VerifyUtils.isEmpty(people.getPersonalFriendWxId())) {
                log.info("是任务好友，需要判断个人号是否被封禁");
                getSql = DaoGetSql.getSql("SELECT * from " + DBWeChat + " where wx_id = ?", people.getPersonalNoWxId());
                sql = new Sql(getSql);
                wechatAccount = wechatAccountMapper.getBySql(sql);
                if (VerifyUtils.isEmpty(wechatAccount) || WebConst.WECHATSTATUS.equals(wechatAccount.getStatus())) {
                    log.info("个人号被封，删除旧信息，给新的个人号");
                    people.setDeleted(1);
                    people.setDb(DBNoPeople);
                    peopleService.add(people);
                    wechatAccount = getWeChat(task);
                    if (!VerifyUtils.isEmpty(wechatAccount)) {
                        log.info("将新的任务好友信息添加到数据库");
                        people.setId(null);
                        people.setPersonalNoWxId(wechatAccount.getWxId());
                        people.setDeleted(0);
                        people.setBeFriendTime(new Date());
                        people.setLable(lableNames);
                        log.info("判断是否是好友，是的话直接发送消息");
                        people = getPeople(map1, people);
                        people.setDb(DBNoPeople);
                        peopleService.add(people);
                    }
                } else {
//                    getSql = DaoGetSql.getById(DBValueTable,8);
//                    sql.setSql(getSql);
//                    PersonalNoValueTable valueTable8 = valueTableService.getBySql(sql);
//                    if(!VerifyUtils.isEmpty(valueTable8.getValue())){
//                        List<String> split = Arrays.asList(valueTable8.getValue().split(","));
//                        if(split.contains(getNoEntity.getTaskId().toString())){
//                            people.setLable(lableNames);
//                            people = getPeople(map1, people);
//                            people.setDb(DBNoPeople);
//                            peopleService.add(people);
//                        }else {
//                            if(people.getFlag()!=2) {
                                people.setLable(lableNames);
                                people = getPeople(map1, people);
                                people.setDb(DBNoPeople);
                                peopleService.add(people);
//                            }
                        }
//                    }
//                }
            } else {
                log.info("不是任务好友，");
                getSql = DaoGetSql.getSql("SELECT * FROM " + DBUser + " where unionid = ? ORDER BY create_time desc limit 0,1", getNoEntity.getUnionId());
                sql.setSql(getSql);
                PersonalNoUser user = userService.getBySql(sql);
                if (VerifyUtils.isEmpty(people)) {
                    log.info("新任务好友，直接添加");
                    wechatAccount = getWeChat(task);
                    if (!VerifyUtils.isEmpty(wechatAccount)) {
                        people = new PersonalNoPeople();
                        people.setChannelId(shortUrlId);
                        people.setPersonalNoWxId(wechatAccount.getWxId());
                        people.setBeFriendTime(new Date());
                        people.setPersonalFriendNickName(getNoEntity.getUnionId());
                        people.setPersonalTaskId(task.getId());
                        if (!VerifyUtils.isEmpty(user) && !VerifyUtils.isEmpty(user.getWxId())) {
                            people.setPersonalFriendWxId(user.getWxId());
                        }
                        people.setLable(lableNames);
                        people = getPeople(map1, people);
                        people.setDeleted(0);
                        people.setDb(DBNoPeople);
                        peopleService.add(people);
                    }
                } else {
                    log.info("以前扫过二维码。但是没有更新用户微信id");
                    people.setBeFriendTime(new Date());
                    people.setChannelId(shortUrlId);
                    log.info("判断个人号是否被封禁");
                    getSql = DaoGetSql.getSql("SELECT * from " + DBWeChat + " where wx_id = ?", people.getPersonalNoWxId());
                    sql = new Sql(getSql);
                    wechatAccount = wechatAccountMapper.getBySql(sql);
                    if (VerifyUtils.isEmpty(wechatAccount) || WebConst.WECHATSTATUS.equals(wechatAccount.getStatus())) {
                        log.info("个人号被封，删除旧信息，给新的个人号");
                        people.setDeleted(1);
                        people.setDb(DBNoPeople);
                        peopleService.add(people);
                        wechatAccount = getWeChat(task);
                        people.setId(null);

                    }
                    if (!VerifyUtils.isEmpty(user) && !VerifyUtils.isEmpty(user.getWxId())) {
                        log.info("有用户信息并且有微信id");
                        people.setPersonalFriendWxId(user.getWxId());
                    }
                    if (!VerifyUtils.isEmpty(wechatAccount)) {
                        people.setDeleted(0);
                        people.setPersonalNoWxId(wechatAccount.getWxId());
                        log.info("判断是否是好友");
                        people.setLable(lableNames);
                        people = getPeople(map1, people);
                        people.setDb(DBNoPeople);
                        peopleService.add(people);
                    }
                }
            }
            log.info("开课提醒任务");
            if (!VerifyUtils.isEmpty(people.getPersonalFriendWxId())) {
                TaskUtiles.toRemindTask(map1, remindFlagService, people.getPersonalNoWxId(), people.getPersonalFriendWxId(), people.getPersonalTaskId(), 0);
            }
        }
        map.put("task", task);
        map.put("wechat", wechatAccount);
        return map;
    }

    @Override
    public List<PersonalNoOperationStockWechatAccount> listBySql(Sql sql) {
        return wechatAccountMapper.listBySql(sql);
    }

    @Override
    public Page<PersonalNoOperationStockWechatAccount> pageQuery(Page<PersonalNoOperationStockWechatAccount> page, QueryPersonal searchObj) {
        String nickname = searchObj.getNickname();
        String equipmentStatus = searchObj.getEquipmentStatus();
        StringBuffer temp = new StringBuffer();
        if (!VerifyUtils.isEmpty(nickname)) {
            temp = DaoGetSql.getTempSql(temp, true);
            temp.append(" nick_name like '%" + nickname + "%' ");
        }
        if (!VerifyUtils.isEmpty(equipmentStatus)) {
            if(!"所有".equals(equipmentStatus)) {
                temp = DaoGetSql.getTempSql(temp, true);
                temp.append(" status = '" + equipmentStatus + "' ");
            }
        }
        log.info("获取总数量");
        Sql sql = new Sql("SELECT count(*) FROM " + DBWeChat + " where operation_project_instance_id = " + G.ms_OPERATION_PROJECT_INSTANCE_ID + temp.toString());
        Long count = wechatAccountMapper.countBySql(sql);
        page.setTotal(count.intValue());
        log.info("获取本页数据");
        temp.append(" order by id desc limit " + page.getOffset() + "," + page.getLimit());
        sql.setSql("SELECT * FROM " + DBWeChat + " where operation_project_instance_id = " + G.ms_OPERATION_PROJECT_INSTANCE_ID + temp.toString());
        List<PersonalNoOperationStockWechatAccount> personalNoOperationStockWechatAccounts = wechatAccountMapper.listBySql(sql);
        log.info("个人号集合查询成功,开始计算好友数量");
        String getSql = "";
        for (PersonalNoOperationStockWechatAccount no : personalNoOperationStockWechatAccounts) {
            no.setNickName(no.getNickName()+"("+no.getStatus()+")");
            getSql = DaoGetSql.getSql("SELECT count(*) FROM " + DBFriends + " where personal_no_wx_id = ? and deleted = 0", no.getWxId());
            no.setFriendsNum(noFriendsService.getCount(getSql).intValue());
            getSql = DaoGetSql.getSql("SELECT count(*) FROM " + DBTaskGroup + " WHERE current_robot_id = ? AND `tname` LIKE '%添加好友%' AND status = '未下发'",no.getWxId());
            count = taskGroupService.getCount(getSql);
            no.setWaitingNums(count.intValue());
            getSql = DaoGetSql.getSql("SELECT * FROM " + DBCategoryAndGroup + " where personal_no_wx_id = ? limit 0,1", no.getWxId());
            sql.setSql(getSql);
            PersonalNoCategoryAndGroup categoryAndGroup = categoryAndGroupService.getBySql(sql);
            log.info("设置类别和销售组");
            if (!VerifyUtils.isEmpty(categoryAndGroup)) {
                no.setCategory(categoryAndGroup.getCategory());
                no.setGroup(categoryAndGroup.getGroup());
            }
        }
        page.setRecords(personalNoOperationStockWechatAccounts);
        return page;
    }

    @Override
    public PersonalNoOperationStockWechatAccount getBySql(Sql sql) {
        return wechatAccountMapper.getBySql(sql);
    }

    //判断用户和个人号是否是好友
    private PersonalNoPeople getPeople(Map<String, Object> map1, PersonalNoPeople people) {
        log.info("判断是否是好友，是的话直接发送消息");
        if (VerifyUtils.isEmpty(people.getPersonalFriendWxId())) {
            people.setFlag(0);
            return people;
        }
        String getSql = DaoGetSql.getSql("SELECT * FROM " + DBFriends + " where user_wx_id = ? and personal_no_wx_id = ? and deleted = 0 limit 0,1", people.getPersonalFriendWxId(), people.getPersonalNoWxId());
        PersonalNoFriends friends = noFriendsService.getOne(getSql);
        if (!VerifyUtils.isEmpty(friends) && friends.getBeFriendTime().getTime()-new Date().getTime()<0) {
            log.info("获取下发消息的间隔时间");
            getSql = DaoGetSql.getById(DBValueTable, 1);
            Sql sql = new Sql(getSql);
            PersonalNoValueTable value1 = valueTableService.getBySql(sql);
            int time = Integer.parseInt(VerifyUtils.isEmpty(value1.getValue()) ? "0" : value1.getValue()) * 1000;
            people.setFlag(2);
            log.info("修改通道好友表的用户微信id");
            undatePVRUserWxId(people);
            TaskUtiles.toTask(map1, people.getPersonalNoWxId(), people.getPersonalFriendWxId(), people.getPersonalTaskId(), time);
            log.info("下发添加标签任务");
            TaskUtiles.toAddLable(map1,people.getPersonalNoWxId(), people.getPersonalFriendWxId(),people.getPersonalTaskId(), time);
        } else {
            people.setFlag(0);
        }
        return people;
    }

    //获取一个新的个人号
    private PersonalNoOperationStockWechatAccount getWeChat(PersonalNoTask task) {
        PersonalNoOperationStockWechatAccount wechatAccount = null;
        String getSql = null;
        String updateSql = null;
        Sql sql = new Sql();
        log.info("获取该任务下的所有个人号");
        getSql = DaoGetSql.getSql("SELECT * FROM " + DBTaskPersonal + " WHERE personal_no_task_id = ? and deleted = 0", task.getId());
        sql.setSql(getSql);
        List<PersonalNoTaskPersonal> personalNoTaskPersonalList = taskPersonalService.listBySql(sql);
        boolean F = false;
        if (!VerifyUtils.collectionIsEmpty(personalNoTaskPersonalList)) {
            log.info("如果现在的下标大于个人号总数，置为0");
            if (task.getAddFriendIndex() >= personalNoTaskPersonalList.size()) {
                task.setAddFriendIndex(0);
            }
            log.info("根据下表获取一个个人号");
            PersonalNoTaskPersonal result = personalNoTaskPersonalList.get(task.getAddFriendIndex());
            if (VerifyUtils.isEmpty(result)) {
                log.info("如果取到数据为空，重新取个人号");
                F = true;
            } else {
                log.info("获取取到的个人号完整信息");
                getSql = DaoGetSql.getSql("SELECT * from " + DBWeChat + " where wx_id = ? and operation_project_instance_id = ? limit 0,1", result.getPersonalNoWxId(), G.ms_OPERATION_PROJECT_INSTANCE_ID);
                sql.setSql(getSql);
                wechatAccount = wechatAccountMapper.getBySql(sql);
                log.info("判断渠道的个人号是否实在封禁状态，如果是，去掉此个人号，重新获取新的个人号");
                if (VerifyUtils.isEmpty(wechatAccount) || WebConst.WECHATSTATUS.equals(wechatAccount.getStatus())) {
                    updateSql = DaoGetSql.getSql("UPDATE " + DBTaskPersonal + " SET deleted = 1 where personal_no_wx_id = ?", result.getPersonalNoWxId());
                    sql.setSql(updateSql);
                    taskPersonalService.deleteBySql(sql);
                    F = true;
                }
            }
            if (F) {
                for (PersonalNoTaskPersonal personalNoTaskPersonal : personalNoTaskPersonalList) {
                    getSql = DaoGetSql.getSql("SELECT * from " + DBWeChat + " where wx_id = ? and operation_project_instance_id = ? limit 0,1", personalNoTaskPersonal.getPersonalNoWxId(), G.ms_OPERATION_PROJECT_INSTANCE_ID);
                    sql.setSql(getSql);
                    wechatAccount = wechatAccountMapper.getBySql(sql);
                    log.info("如果重新获取的个人号为空，或者状态是封禁，则去掉个人号重新获取，直到获得未封禁的个人号或者全部取完");
                    if (!VerifyUtils.isEmpty(wechatAccount)) {
                        if (WebConst.WECHATSTATUS.equals(wechatAccount.getStatus())) {
                            updateSql = DaoGetSql.getSql("UPDATE " + DBTaskPersonal + " SET deleted = 1 WHERE personal_no_wx_id = ?", wechatAccount.getWxId());
                            sql.setSql(updateSql);
                            taskPersonalService.deleteBySql(sql);
                            wechatAccount = null;
                        } else {
                            log.info("个人号可以用，跳出循环");
                            break;
                        }
                    } else {
                        updateSql = DaoGetSql.getSql("UPDATE " + DBTaskPersonal + " SET deleted = 1 WHERE personal_no_task_id = ? and personal_no_wx_id = ?", personalNoTaskPersonal.getPersonalNoTaskId(), personalNoTaskPersonal.getPersonalNoWxId());
                        sql.setSql(updateSql);
                        taskPersonalService.deleteBySql(sql);
                    }
                }
            }
        }
        if (personalNoTaskPersonalList.size() == 0 || VerifyUtils.isEmpty(wechatAccount)) {
            log.info("任务下没有可用的个人号，关闭此任务");
            updateSql = DaoGetSql.getSql("UPDATE " + DBTask + " SET activity_type = 1 where id = ?", task.getId());
            sql.setSql(updateSql);
            noTaskService.updateBySql(sql);
        } else {
            log.info("修改下标，便于循环获取个人号");
            updateSql = DaoGetSql.getSql("UPDATE " + DBTask + " SET add_friend_index = ? where id = ?", task.getAddFriendIndex() + 1, task.getId());
            sql.setSql(updateSql);
            noTaskService.updateBySql(sql);
        }
        return wechatAccount;

    }

    //修改渠道好友关系表的用户微信id
    private void undatePVRUserWxId(PersonalNoPeople people) {
        log.info("处理渠道和用户信息关系对应表，插入用户wxid");
        String getSql = DaoGetSql.getSql("SELECT * FROM " + DBPVR + " where union_id = ? order by id desc limit 0,1", people.getPersonalFriendNickName());
        Sql sql = new Sql(getSql);
        PassageVisitorRecord passageVisitorRecord = passageVisitorRecordService.getBySql(sql);
        if (!VerifyUtils.isEmpty(passageVisitorRecord) && VerifyUtils.isEmpty(passageVisitorRecord.getUserWxId())) {
            passageVisitorRecord.setDb(DBPVR);
            passageVisitorRecord.setUserWxId(people.getPersonalFriendWxId());
            passageVisitorRecordService.add(passageVisitorRecord);
        }
    }
}
