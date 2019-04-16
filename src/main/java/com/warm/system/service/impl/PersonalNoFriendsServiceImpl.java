package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.robot.common.SunTaskType;
import com.warm.system.entity.*;
import com.warm.system.mapper.PersonalNoFriendsMapper;
import com.warm.system.service.db1.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
    /**
     * 根据个人号id查询所有的个人号好友列表
     * @param personalNoId
     * @return
     */
    @Override
    public List<PersonalNoFriends> listByPersonalId(Integer personalNoId) {
        log.info("数据库开始根据个人号id查询个人号好友列表");
        EntityWrapper<PersonalNoFriends> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        if(VerifyUtils.isEmpty(personalNoId)){
            entityWrapper = null;
        }else {
            entityWrapper.eq("personal_no_id" , personalNoId);
        }
        List<PersonalNoFriends> friendsList = baseMapper.selectList(entityWrapper);
        log.info("数据库开始根据个人号id查询个人号好友列表成功");
        return friendsList;
    }
    /**
     * 根据个人号集合查询个人号粉丝集合
     * 去重
     * @param list
     * @return
     */
    @Override
    public Set<Integer> listByPersonalList(List<PersonalNo> list) {
        log.info("根据个人号集合查询个人号粉丝集合");
        Set<Integer> set = new HashSet<>();
        for (PersonalNo no : list) {
            List<PersonalNoFriends> friendsList = listByPersonalId(no.getId());
            if(!VerifyUtils.collectionIsEmpty(friendsList)){
                for (PersonalNoFriends personalNoFriends : friendsList) {
                    set.add(personalNoFriends.getUserId());
                }
            }
        }
        log.info("根据个人号集合查询个人号粉丝集合结束");
        VerifyUtils.cleaNull(set);
        return set;
    }

    /**
     * 添加好友个人表信息
     * @param friends
     * @return
     */
    @Override
    public boolean insert(PersonalNoFriends friends) {
        if(VerifyUtils.isEmpty(friends.getId())){
            log.info("该个人号好友不存在，插入表");
            return baseMapper.insert(friends)>0;
        }
        log.info("该个人号好友存在，更新表");
        return baseMapper.updateById(friends)>0;
    }

    /**
     * 根据个人号id和好友wxId查询对应的个人号好友
     * @param logicId
     * @param username
     * @return
     */
    @Override
    public PersonalNoFriends getByPersonalIdAndUserWxId(Integer logicId, String username) {
        log.info("根据个人号id和用户wxId查询个人号好友");
        return friendsMapper.getByPersonalIdAndUserWxId(logicId, username);
    }

    /**
     * 根据个人号好友列表删除
     * @param friends
     */
    @Override
    public void removeByFriends(List<PersonalNoFriends> friends) {
        for (PersonalNoFriends friend : friends) {
            baseMapper.deleteById(friend.getId());
        }
    }

    /**
     * 根据个人号微信id查询所有的个人号好友
     * @param username
     * @return
     */
    @Override
    public List<PersonalNoFriends> listByPersonalWxId(String username) {
        return friendsMapper.listByPersonalWxId(username);
    }

    /**
     * 根据微信id和用户微信id查询好友信息
     * @param personalNoWxId
     * @param wxId
     * @return
     */
    @Override
    public PersonalNoFriends getByPersonalWxIdAndUserWxId(String personalNoWxId, String wxId) {
        return friendsMapper.getByPersonalWxIdAndUserWxId(personalNoWxId, wxId);
    }

    /**
     * 根据个人号wxid和用户wxid查找所有的个人号好友
     * @param wxId
     * @param fromUsername
     * @return
     */
    @Override
    public List<Integer> listByPersonalWxIdAndUserWxId(String wxId, String fromUsername) {
        return friendsMapper.listByPersonalWxIdAndUserWxId(wxId, fromUsername);
    }

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
        EntityWrapper<PersonalNoFriends> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        if(VerifyUtils.isEmpty(personalWxId)){
            return userList;
        }
        entityWrapper.eq("personal_no_wx_id", personalWxId);
        entityWrapper.eq("deleted", 0);
        List<PersonalNoFriends> personalNoFriends = baseMapper.selectPage(page, entityWrapper);
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
        PersonalNoBlacklist byWxId = blacklistService.getByWxId(user.getWxId());
        if(VerifyUtils.isEmpty(byWxId)) {
            log.info("将用户添加到黑名单");
            PersonalNoBlacklist blacklist = new PersonalNoBlacklist();
            blacklist.setNickName(user.getNickName());
            blacklist.setWxId(user.getWxId());
            boolean save = blacklistService.insert(blacklist);
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
        boolean save = taskGroupService.insert(taskGroup);
        if(save){
            for (int i = 0; i < users.size(); i++) {
                PersonalNoPhoneTask task = new PersonalNoPhoneTask();
                task.setTaskGroupId(taskGroup.getId());
                task.setCreateTime(new Date());
                task.setStatus("未下发");
                task.setRobotId(users.get(i).getWxId());
                task.setTaskType(SunTaskType.DELETE_FRIEND);
                task.setStep(i+1);
                task.setTname(personalWxId + "删除" + users.get(i).getNickName());
                boolean save1 = taskService.insert(task);
                if(!save1){
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
        List<Integer> integers = peopleService.listIdByPersonalWxIdAndUserWxId(personalWxId, user.getWxId());
        if(!VerifyUtils.collectionIsEmpty(integers)) {
            peopleService.deleteBatchIds(integers);
        }
        List<Integer> integers1 = friendsMapper.listByPersonalWxIdAndUserWxId(personalWxId, user.getWxId());
        if(!VerifyUtils.collectionIsEmpty(integers1)) {
            baseMapper.deleteBatchIds(integers1);
        }
    }


}
