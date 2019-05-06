package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.robot.common.SunTaskType;
import com.warm.system.entity.*;
import com.warm.system.mapper.PersonalNoFriendsMapper;
import com.warm.system.service.db1.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
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
public class PersonalNoFriendsServiceImpl extends ServiceImpl<PersonalNoFriendsMapper, PersonalNoFriends> implements PersonalNoFriendsService {
    private static Log log = LogFactory.getLog(PersonalNoFriendsServiceImpl.class);
    @Autowired
    private PersonalNoFriendsMapper friendsMapper;
    @Autowired
    private PersonalNoUserService userService;
    @Autowired
    private PersonalNoPhoneTaskGroupService taskGroupService;
    @Autowired
    private PersonalNoPhoneTaskService taskService;
    @Autowired
    private PersonalNoPeopleService peopleService;
    @Autowired
    private PersonalNoBlacklistService blacklistService;

    private String ZCDB = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_friends);
    private String ZCDBNoPeople = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_people);
    private String ZCDBTaskGroup = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_phone_task_group);
    private String ZCDBTask = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_phone_task);


    /**
     * 根据个人号微信id查询对应的所有好友信息
     * @param map
     * @return
     */
    @Override
    public List<PersonalNoUser> pageQuery(Page<PersonalNoFriends> page, Map<String, String> map) {
        List<PersonalNoUser>  userList = new ArrayList<>();
        String personalWxId = map.get("personalWxId");
        String nickName = map.get("nickName");
        if(VerifyUtils.isEmpty(personalWxId)){
            return userList;
        }
        String sql = "select * from "+ ZCDB + " where personal_no_wx_id = '"+personalWxId+"' and deleted = 0 limit "+page.getOffset()+","+page.getLimit();
        List<PersonalNoFriends> personalNoFriends = friendsMapper.list(sql);
        sql = DaoGetSql.getSql("select count(*) from "+ZCDB);
        Long count = friendsMapper.getCount(sql);
        page.setTotal(count.intValue());
        page.setRecords(personalNoFriends);
        if(!VerifyUtils.isEmpty(nickName)){
            for (PersonalNoFriends record : page.getRecords()) {
                PersonalNoUser byWxId = userService.getByWxId(record.getUserWxId());
                if(!VerifyUtils.isEmpty(byWxId)) {
                    log.info("条件查找存在好友信息的情况");
                    if (!VerifyUtils.isEmpty(byWxId.getNickName()) && byWxId.getNickName().contains(nickName)) {
                        userList.add(byWxId);
                    }
                }else {
                    log.info("条件查找不存在好友信息的情况");
                    if(record.getPersonalNoWxId().equals(nickName)){
                        PersonalNoUser user = new PersonalNoUser();
                        user.setWxId(record.getUserWxId());
                        user.setNickName(record.getPersonalNoWxId());
                        userList.add(byWxId);
                    }
                }
            }
        }else {
            for (PersonalNoFriends record : page.getRecords()) {
                PersonalNoUser byWxId = userService.getByWxId(record.getUserWxId());
                if(VerifyUtils.isEmpty(byWxId)){
                    log.info("非条件查找不存在好友信息的情况");
                    PersonalNoUser user = new PersonalNoUser();
                    user.setWxId(record.getUserWxId());
                    user.setNickName(record.getPersonalNoWxId());
                    userList.add(user);
                }else {
                    log.info("非条件查找存在好友信息的情况");
                    userList.add(byWxId);
                }
            }
        }
        return userList;
    }
    /**
     * 根据好友微信id列表删除好友
     * @param users
     * @return
     */
    @Transactional
    @Override
    public boolean deleteFriends(String personalWxId, List<PersonalNoUser> users) {
        log.info("开始添加删除好友任务");
        toDeleteTask(personalWxId, users);
        log.info("删除对应任务粉丝表的数据和任务好友表的数据");
        for (PersonalNoUser user : users) {
            deletePeopleAndFriends(personalWxId, user);
        }
        return true;
    }
    /**
     * 添加用户到黑名单
     * @param personalWxId
     * @param user
     * @return
     */
    @Override
    public boolean blackFriends(String personalWxId, PersonalNoUser user) {
        log.info("判断是否在黑名单");
        String sql = DaoGetSql.getSql("select * from " + DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_blacklist) + " where wx_id = ?", user.getWxId());
        PersonalNoBlacklist byWxId = blacklistService.getOne(sql);
        if(VerifyUtils.isEmpty(byWxId)) {
            log.info("将用户添加到黑名单");
            PersonalNoBlacklist blacklist = new PersonalNoBlacklist();
            blacklist.setNickName(user.getNickName());
            blacklist.setWxId(user.getWxId());
            blacklist.setDb(DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_blacklist));
            blacklistService.add(blacklist);
        }
        List<PersonalNoUser> users = new ArrayList<>();
        users.add(user);
        log.info("将用户删除");
        toDeleteTask(personalWxId, users);
        log.info("将任务粉丝表数据和个人号好友表数据删除");
        deletePeopleAndFriends(personalWxId, user);
        return true;
    }
    /**
     * 添加删除好友任务
     * @param personalWxId
     * @param users
     */
    private void toDeleteTask(String personalWxId, List<PersonalNoUser> users) {
        log.info("添加删除好友任务到数据库");
        PersonalNoPhoneTaskGroup taskGroup = new PersonalNoPhoneTaskGroup();
        taskGroup.setStatus("未下发");
        taskGroup.setCurrentRobotId(personalWxId);
        taskGroup.setNextStep(1);
        taskGroup.setTotalStep(users.size());
        taskGroup.setTname(personalWxId + "删除好友集合");
        taskGroup.setCreateTime(new Date());
        taskGroup.setTaskOrder(0);
        taskGroup.setDb(ZCDBTaskGroup);
        int save = taskGroupService.add(taskGroup);
        if(save!=0){
            for (int i = 0; i < users.size(); i++) {
                PersonalNoPhoneTask task = new PersonalNoPhoneTask();
                task.setTaskGroupId(taskGroup.getId());
                task.setCreateTime(new Date());
                task.setStatus("未下发");
                task.setRobotId(users.get(i).getWxId());
                task.setTaskType(SunTaskType.DELETE_FRIEND);
                task.setStep(i+1);
                task.setTname(personalWxId + "删除" + users.get(i).getNickName());
                task.setDb(ZCDBTask);
                int save1 = taskService.add(task);
                if(save1 == 0){
                    log.error("添加删除好友任务失败");
                    throw new RuntimeException("添加删除好友任务失败");
                }
            }
        }
    }
    /**
     * 根据个人号微信id和用户微信id删除任务粉丝和和人号好友
     * @param personalWxId
     * @param user
     */
    private void deletePeopleAndFriends(String personalWxId, PersonalNoUser user) {
        String sql = DaoGetSql.getSql("SELECT id FROM "+ZCDBNoPeople+" WHERE personal_no_wx_id = ? and personal_friend_wx_id = ?",personalWxId,user.getWxId());
        List<String> integers = peopleService.listString(sql);
        String ids1 = DaoGetSql.getIds(integers);
        sql = "delete from "+ZCDBNoPeople+" where id in "+ids1;
        peopleService.delete(sql);
        sql = DaoGetSql.getSql("select id from " + DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_friends) + " where personal_no_wx_id = ? and user_wx_id = ?", personalWxId, user.getWxId());
        List<String> list = friendsMapper.listString(sql);
        String ids = DaoGetSql.getIds(list);
        sql = "delete from "+DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_friends)+" where id in "+ids;
        friendsMapper.delete(sql);
    }

    @Override
    public Integer add(PersonalNoFriends entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return friendsMapper.add(entity);
        return friendsMapper.updateOne(entity);
    }

    @Override
    public Integer delete(String sql) {
        return friendsMapper.delete(sql);
    }

    @Override
    public List<PersonalNoFriends> list(String sql) {
        return friendsMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return friendsMapper.listString(sql);
    }

    @Override
    public PersonalNoFriends getOne(String sql) {
        return friendsMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return friendsMapper.getCount(sql);
    }


}
