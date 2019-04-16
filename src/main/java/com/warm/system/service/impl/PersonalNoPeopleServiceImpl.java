package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warm.entity.requre.PeopleNumReq;
import com.warm.entity.result.LableShow;
import com.warm.system.entity.PersonalNo;
import com.warm.system.entity.PersonalNoPeople;
import com.warm.system.entity.PersonalNoTaskLable;
import com.warm.system.mapper.PersonalNoPeopleMapper;
import com.warm.system.service.db1.PersonalNoPeopleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoTaskLableService;
import com.warm.utils.VerifyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class PersonalNoPeopleServiceImpl extends ServiceImpl<PersonalNoPeopleMapper, PersonalNoPeople> implements PersonalNoPeopleService {

    private static Log log = LogFactory.getLog(PersonalNoPeopleServiceImpl.class);
    @Autowired
    private PersonalNoTaskLableService noTaskLableService;
    @Autowired
    private PersonalNoPeopleMapper taskPeopleMapper;
    /**
     * 根据个人号wxid和任务id查询对应的粉丝数量
     * @param noSet
     * @param taskLableList
     * @return
     */
    @Override
    public Set<LableShow> listByPersonalIdAndTaskId(Set<LableShow> noSet, List<PersonalNoTaskLable> taskLableList) {
        if(!VerifyUtils.collectionIsEmpty(noSet)){
            for (LableShow lableShow : noSet) {
                EntityWrapper<PersonalNoPeople> entityWrapper = new EntityWrapper<>();
                entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
                entityWrapper.eq("personal_no_wx_id", lableShow.getPersonalWxId());
                if(!VerifyUtils.collectionIsEmpty(taskLableList)){
                    for (PersonalNoTaskLable personalNoTaskLable : taskLableList) {
                        entityWrapper = new EntityWrapper<>();
                        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
                        entityWrapper.eq("personal_task_id", personalNoTaskLable.getPersonalNoTaskId());
                        List<PersonalNoPeople> people = baseMapper.selectList(entityWrapper);
                        lableShow.setPeopleNum(lableShow.getPeopleNum() + people.size());
                    }
                }else {
                    List<PersonalNoPeople> people = baseMapper.selectList(entityWrapper);
                    lableShow.setPeopleNum(lableShow.getPeopleNum() + people.size());
                }
            }
        }
        return noSet;
    }
    /**
     * 根据个人号集合和标签名称集合查询粉丝id集合
     * 标签查询个人号任务id集合
     * 根据个人号wxid和个人号任务呀查询任务粉丝
     * @param peopleNumReq
     * @return
     */
    @Override
    public Set<String> getByNoListAndLableNameList(PeopleNumReq peopleNumReq) {
        log.info("用来存放y好友微信id");
        Set<String> peopleIdSet = new HashSet<>();
        log.info("取得所有的任务id");
        Set<Integer> taskIdSet = noTaskLableService.listByLableNameList(peopleNumReq.getLableNameList());
        if(!VerifyUtils.isEmpty(peopleNumReq.getNoList())){
            for (PersonalNo no : peopleNumReq.getNoList()) {
                if(!VerifyUtils.collectionIsEmpty(taskIdSet)){
                    for (Integer integer : taskIdSet) {
                        EntityWrapper<PersonalNoPeople> entityWrapper = new EntityWrapper<>();
                        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
                        entityWrapper.eq("personal_no_wx_id", no.getWxId());
                        entityWrapper.eq("personal_task_id", integer);
                        entityWrapper.ne("flag", 0);
                        if(!VerifyUtils.isEmpty(peopleNumReq.getStartTime()) && !VerifyUtils.isEmpty(peopleNumReq.getEndTime())){
                            entityWrapper.between("be_friend_time", peopleNumReq.getStartTime(), peopleNumReq.getEndTime());
                        }
                        List<PersonalNoPeople> people = baseMapper.selectList(entityWrapper);
                        if(!VerifyUtils.collectionIsEmpty(people)){
                            for (PersonalNoPeople person : people) {
                                peopleIdSet.add(person.getPersonalFriendWxId());
                            }
                        }
                    }
                }
            }
        }
        return peopleIdSet;
    }

    /**
     * 根据任务id和个人号id查询对应任务下的所有粉丝
     * @param taskId
     * @param username
     * @return
     */
    @Override
    public List<String> listUserWxIdByTaskIdAndPersonalWxId(Integer taskId, String username, Date startTime, Date endTime) {
        if(VerifyUtils.isEmpty(startTime) || VerifyUtils.isEmpty(endTime)) {
            return taskPeopleMapper.listUserWxIdByTaskIdAndPersonalWxId(taskId, username);
        }
        return taskPeopleMapper.listUserWxIdByTaskIdAndPersonalWxIdAndTime(taskId, username, startTime, endTime);
    }

    /**
     * 根据个人号查询所有的任务粉丝wxid
     * @param wxId
     * @return
     */
    @Override
    public List<String> listUserWxIdByPersonalWxId(String wxId, Date startTime, Date endTime) {
        if(VerifyUtils.isEmpty(startTime) || VerifyUtils.isEmpty(endTime)) {
            return taskPeopleMapper.listUserWxIdByPersonalWxId(wxId);
        }
        return taskPeopleMapper.listUserWxIdByPersonalWxIdAndTime(wxId, startTime, endTime);
    }

    /**
     * 根据个人号微信id和用户微信id查找不是好友的任务粉丝
     * @param wxId
     * @param username
     * @return
     */
    @Override
    public PersonalNoPeople getByPersonalIdAndUserId(String wxId, String username, Integer flag) {
        return taskPeopleMapper.getByPersonalIdAndUserId(wxId, username, flag);
    }
    /**
     * 根据个人号微信id和用户微信昵称查询任务粉丝
     * @param wxId
     * @param nickname
     * @return
     */
    @Override
    public List<PersonalNoPeople> getByPersonalWxIdAndUserName(String wxId, String nickname) {
        Date date = new Date();//取得当前时间
        EntityWrapper<PersonalNoPeople> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        entityWrapper.eq("personal_no_wx_id", wxId);
        entityWrapper.eq("personal_friend_nick_name", nickname);
        long time = date.getTime();
        Date date1 = new Date(time - 300000L);
        Date date2 = new Date(time + 10000L);
        entityWrapper.between("be_friend_time", date1, date2);
        return baseMapper.selectList(entityWrapper);
    }

    /**
     * 根据任务id和昵称获取任务粉丝
     * @param parseInt
     * @param nickname
     * @return
     */
    @Override
    public PersonalNoPeople getByTaskIdAndUserNickName(int parseInt, String nickname) {
        return taskPeopleMapper.getByTaskIdAndUserNickName(parseInt, nickname);
    }

    /**
     * 根据任务id获取任务粉丝数量
     * @param personaNoTaskId
     * @return
     */
    @Override
    public Integer getPeopleCountByTaskId(Integer personaNoTaskId) {
        return taskPeopleMapper.getPeopleCountByTaskId(personaNoTaskId);
    }

    /**
     * 根据个人号微信id和用户微信id查询所有的任务粉丝idlie
     * @param username
     * @param userWxId
     * @return
     */
    @Override
    public List<Integer> listIdByPersonalWxIdAndUserWxId(String username, String userWxId) {
        return taskPeopleMapper.listIdByPersonalWxIdAndUserWxId(username, userWxId);
    }

    /**
     * 根据个人号微信id和用户微信id和任务号查询任务粉丝
     * taskId为空，新用户取最后一个
     * @param username
     * @param userWxId
     * @return
     */
    @Override
    public PersonalNoPeople getByPersonalWxIdAndUserWxId(String username, String userWxId) {
        return taskPeopleMapper.getByPersonalWxIdAndUserWxId(username, userWxId);
    }

    /**
     * 根据任务id和用户微信id查询任务
     * @param parseInt
     * @param wxId
     * @return
     */
    @Override
    public PersonalNoPeople getByTaskIdAndUserWxId(int parseInt, String wxId) {
        return taskPeopleMapper.getByTaskIdAndUserWxId(parseInt, wxId);
    }

    /**
     * 根据任务id和个人号微信id查询任务粉丝
     * @param taskId
     * @param personalNoWxId
     * @return
     */
    @Override
    public List<PersonalNoPeople> ListByTaskIdAndPersonalWxId(Integer taskId, String personalNoWxId) {
        return taskPeopleMapper.ListByTaskIdAndPersonalWxId(taskId, personalNoWxId);
    }

    /**
     * 根据任务id和时间查询当天加好友人数
     * @param id
     * @param date
     * @param datTaskDate
     * @return
     */
    @Override
    public List<PersonalNoPeople> listByTaskIdAndTime(Integer id, Date date, Date datTaskDate) {
        log.info("根据任务id和时间查询当天加好友人数");
        EntityWrapper<PersonalNoPeople> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        entityWrapper.eq("personal_task_id", id);
        entityWrapper.between("be_friend_time", "" + date + " 00:00:00", "" + datTaskDate + " 00:00:00");
        return baseMapper.selectList(entityWrapper);
    }
}
