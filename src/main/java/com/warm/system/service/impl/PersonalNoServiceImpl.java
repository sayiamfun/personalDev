package com.warm.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.query.QueryPersonal;
import com.warm.entity.requre.GetNoEntity;
import com.warm.entity.robot.common.SunTaskType;
import com.warm.system.entity.*;
import com.warm.system.mapper.PersonalNoMapper;
import com.warm.system.service.db1.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.DaoGetSql;
import com.warm.utils.TaskUtiles;
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
public class PersonalNoServiceImpl extends ServiceImpl<PersonalNoMapper, PersonalNo> implements PersonalNoService {

    private static Log log = LogFactory.getLog(PersonalNoServiceImpl.class);
    @Autowired
    private PersonalNoTaskLableService taskLableService;
    @Autowired
    private PersonalNoTaskPersonalService taskPersonalService;
    @Autowired
    private PersonalNoPhoneTaskGroupService taskGroupService;
    @Autowired
    private PersonalNoPhoneTaskService taskService;
    @Autowired
    private PersonalNoTaskService noTaskService;
    @Autowired
    private PersonalNoTaskDataService taskDataService;
    @Autowired
    private PersonalNoService noService;
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
    private PersonalNoPeopleService noPeopleService;
    @Autowired
    private PersonalNoFriendsService noFriendsService;
    @Autowired
    private PersonalNoMapper noMapper;

    private String ZCDBTaskGroup = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_phone_task_group);
    private String ZCDBTask = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_phone_task);
    private String ZCDBPersonalNo = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no);
    private String ZCDBNoPeople = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_people);
    private String ZCDBFriends = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_friends);
    /**
     * 根据任务id随机取一个个人号
     * 取得参数为任务粉丝id，因为需要修改任务粉丝的个人号微信id信息
     *
     * @param getNoEntity
     * @return
     */
    @Override
    public Map<String, Object> getPersonalByTaskId(GetNoEntity getNoEntity) {
        Map<String, Object> map = new HashMap<>();
        PersonalNoTask task = noTaskService.getTaskByIdLess(getNoEntity.getTaskId());
        PersonalNo no = null;
        String sql = null;
        Map<String, Object> map1 = TaskUtiles.getMap(peopleService, taskGroupService, noTaskService, taskService, keywordService);
        if(!VerifyUtils.isEmpty(task)){
            log.info("修改任务数据信息(推荐个人号人数)");
            log.info("修改任务数据信息(点击链接人数）");
            PersonalNoTaskData taskData = taskDataService.getByTaskId(getNoEntity.getTaskId());
            if(!VerifyUtils.isEmpty(taskData)){
                log.info("错略统计点击人数");
                taskData.setClickUrlNum(taskData.getClickUrlNum()==0?0:taskData.getClickUrlNum()+1);
                taskData.setToPeopleNum(taskData.getToPeopleNum()+1);
                taskDataService.updateById(taskData);
            }else {
                taskData = new PersonalNoTaskData();
                taskData.setClickUrlNum(1);
                taskData.setToPeopleNum(0);
                taskData.setPersonalNoTaskId(getNoEntity.getTaskId());
                taskData.setToPeopleNum(0);
                taskDataService.insert(taskData);
            }
            log.info("开始处理个人号信息");
            String getPeopleSql = DaoGetSql.getSql("SELECT id,personal_friend_wx_id,personal_task_id,personal_no_wx_id,channel_id,flag,be_friend_time,remarks,personal_friend_nick_name,deleted FROM " + ZCDBNoPeople + " WHERE personal_task_id = ? and personal_friend_nick_name = ? and deleted = 0 LIMIT 0,1",getNoEntity.getTaskId(),getNoEntity.getUnionId());
            PersonalNoPeople people = noPeopleService.getOne(getPeopleSql);
            if(!VerifyUtils.isEmpty(people)){
                if(VerifyUtils.isEmpty(people.getPersonalFriendWxId())){
                    PersonalNoUser user = userService.getByUnionId(getNoEntity.getUnionId());
                    if(!VerifyUtils.isEmpty(user.getWxId())){
                        people.setPersonalFriendWxId(user.getWxId());
                        people.setDb(ZCDBNoPeople);
                        noPeopleService.add(people);
                        TaskUtiles.toTask(map1, people.getPersonalNoWxId(), people.getPersonalFriendWxId(), getNoEntity.getTaskId(), Integer.parseInt(valueTableService.selectById(1).getValue()) * 1000);
                    }
                }else {
                    TaskUtiles.toTask(map1, people.getPersonalNoWxId(), people.getPersonalFriendWxId(), getNoEntity.getTaskId(), Integer.parseInt(valueTableService.selectById(1).getValue()) * 1000);
                }
                sql = DaoGetSql.getSql("select * from "+ZCDBPersonalNo+" where wx_id = ? limit 0,1",people.getPersonalNoWxId());
                no = noService.getOne(sql);
            }else {
                List<PersonalNoTaskPersonal> personalNoTaskPersonals1 = taskPersonalService.listByTaskId(getNoEntity.getTaskId());
                if (task.getAddFriendIndex() < personalNoTaskPersonals1.size()) {
                    PersonalNoTaskPersonal personalNoTaskPersonal = personalNoTaskPersonals1.get(task.getAddFriendIndex());
                    personalNoTaskPersonal.setToPeopleNum(VerifyUtils.isEmpty(personalNoTaskPersonal.getToPeopleNum()) ? 1 : personalNoTaskPersonal.getToPeopleNum() + 1);
                    taskPersonalService.updateById(personalNoTaskPersonal);
                    sql = DaoGetSql.getSql("select * from " + ZCDBPersonalNo + " where wx_id = ? limit 0,1", personalNoTaskPersonals1.get(task.getAddFriendIndex()).getPersonalNoWxId());
                    no = noMapper.getOne(sql);
                    if (task.getAddFriendIndex() + 1 < personalNoTaskPersonals1.size()) {
                        task.setAddFriendIndex(task.getAddFriendIndex() + 1);
                        noTaskService.updateById(task);
                    } else {
                        task.setAddFriendIndex(0);
                        noTaskService.updateById(task);
                    }
                }
                log.info("将用户添加到任务粉丝表");
                getPeopleSql = DaoGetSql.getSql("SELECT id,personal_friend_wx_id,personal_task_id,personal_no_wx_id,channel_id,flag,be_friend_time,remarks,personal_friend_nick_name,deleted FROM "+ZCDBNoPeople+" WHERE personal_task_id = ? and personal_friend_nick_name = ? and deleted = 0 LIMIT 0,1",getNoEntity.getTaskId(), getNoEntity.getUnionId());
                people = peopleService.getOne(getPeopleSql);
                if (VerifyUtils.isEmpty(people)) {
                    people = new PersonalNoPeople();
                    people.setPersonalTaskId(getNoEntity.getTaskId());
                    people.setFlag(0);
                    people.setDeleted(0);
                    people.setPersonalFriendNickName(getNoEntity.getUnionId());
                    people.setBeFriendTime(new Date());
                    people.setPersonalNoWxId(no.getWxId());
                    PersonalNoUser userByUnionid = userService.getByUnionId(people.getPersonalFriendNickName());
                    if (!VerifyUtils.isEmpty(userByUnionid.getWxId())) {
                        String sql1 = DaoGetSql.getSql("select * from " + ZCDBFriends + " where personal_no_wx_id = ? and user_wx_id = ? limit 0,1", people.getPersonalNoWxId(), userByUnionid.getWxId());
                        PersonalNoFriends one = noFriendsService.getOne(sql1);
                        if(!VerifyUtils.isEmpty(one)) {
                            people.setPersonalFriendWxId(userByUnionid.getWxId());
                            people.setFlag(2);
                            TaskUtiles.toTask(map1, people.getPersonalNoWxId(), userByUnionid.getWxId(), getNoEntity.getTaskId(), Integer.parseInt(valueTableService.selectById(1).getValue()) * 1000);
                        }
                    }
                    people.setDb(ZCDBNoPeople);
                    int save1 = peopleService.add(people);
                    if (save1 == 0) {
                        log.info("插入任务粉丝失败（未添加好友）");
                        throw new RuntimeException("插入任务粉丝失败（未添加好友）");
                    }
                } else {
                    if (VerifyUtils.isEmpty(people.getPersonalFriendWxId())) {
                        PersonalNoUser userByUnionid = userService.getByUnionId(people.getPersonalFriendNickName());
                        log.info("已经是任务粉丝，下发任务");
                        if (!VerifyUtils.isEmpty(userByUnionid)) {
                            TaskUtiles.toTask(map1, people.getPersonalNoWxId(), userByUnionid.getWxId(), getNoEntity.getTaskId(), Integer.parseInt(valueTableService.selectById(1).getValue()) * 1000);
                        }
                    } else {
                        log.info("已经是任务粉丝，下发任务");
                        TaskUtiles.toTask(map1, people.getPersonalNoWxId(), people.getPersonalFriendWxId(), getNoEntity.getTaskId(), Integer.parseInt(valueTableService.selectById(1).getValue()) * 1000);
                    }
                    people.setFlag(2);
                    peopleService.updateById(people);
                }
                PersonalNoTaskRemindFlag remindFlag = remindFlagService.getByPersonalWxIdAndUserWxIdAndTaskId(people.getPersonalNoWxId(), people.getPersonalFriendWxId(), people.getPersonalTaskId());
                if (VerifyUtils.isEmpty(remindFlag)) {
                    TaskUtiles.toRemindTask(map1, remindFlagService, people.getPersonalNoWxId(), people.getPersonalFriendWxId(), people.getPersonalTaskId(), 0);
                }
            }
            map.put("task", task);
            map.put("no", no);
        }
        return map;
    }


    /**
     * 根据标签id查询个人号
     *
     * @param lableId
     * @return
     */
    @Override
    public Set<PersonalNo> listByLableName(Integer lableId) {
        log.info("根据标签id查询个人号名称");
        Set<Integer> taskIdSet = new HashSet();
        log.info("根据标签id查询任务标签列表");
        List<PersonalNoTaskLable> personalNoTaskLableList = taskLableService.listByLableId(lableId);
        if (!VerifyUtils.collectionIsEmpty(personalNoTaskLableList)) {
            for (PersonalNoTaskLable personalNoTaskLable : personalNoTaskLableList) {
                taskIdSet.add(personalNoTaskLable.getPersonalNoTaskId());
            }
        }
        Set<PersonalNo> noSet = new HashSet<>();
        //根据个人号任务id查询粉丝数量
        log.info("根据个人号任务id查询个人号数量");
        if (!VerifyUtils.collectionIsEmpty(taskIdSet)) {
            String sql = null;
            for (Integer o : taskIdSet) {
                List<PersonalNoTaskPersonal> listByTaskId = taskPersonalService.listByTaskId(o);
                if (!VerifyUtils.collectionIsEmpty(listByTaskId)) {
                    for (PersonalNoTaskPersonal personalNoTaskPersonal : listByTaskId) {
                        sql = DaoGetSql.getSql("select * from "+ZCDBPersonalNo+" where wx_id = ? limit 0,1",personalNoTaskPersonal.getPersonalNoWxId());
                        PersonalNo no = noMapper.getOne(sql);
                        noSet.add(no);
                    }
                }
            }
        }
        //清空集合空数据
        VerifyUtils.cleaNull(noSet);
        log.info("查找成功，返回数据");
        return noSet;
    }
    /*
     * 根据条件分页查询个人号
     */
    @Override
    public Page<PersonalNo> pageQuery(Page<PersonalNo> page, QueryPersonal searchObj) {
        String nickname = searchObj.getNickname();
        String personalNoCategoryName = searchObj.getPersonalNoCategoryName();
        String equipmentId = searchObj.getEquipmentId();
        String equipmentStatus = searchObj.getEquipmentStatus();
        StringBuffer temp = new StringBuffer("select * from "+ZCDBPersonalNo);
        log.info("开始判断查找参数是否为空");
        boolean F = false;
        if(!VerifyUtils.isEmpty(equipmentId)){
            temp = DaoGetSql.getTempSql(temp, F);
            temp.append(" equipment_id = '"+equipmentId+"'");
            F = true;
        }
        if(!VerifyUtils.isEmpty(equipmentStatus)){
            temp = DaoGetSql.getTempSql(temp, F);
            temp.append(" equipment_status = '"+equipmentStatus+"'");
            F = true;
        }
        if(!VerifyUtils.isEmpty(personalNoCategoryName)){
            temp = DaoGetSql.getTempSql(temp, F);
            temp.append(" personal_no_category = '"+personalNoCategoryName+"'");
            F = true;
        }
        if(!VerifyUtils.isEmpty(nickname)){
            temp = DaoGetSql.getTempSql(temp, F);
            temp.append(" nickname = '"+nickname+"'");
        }
        temp.append(" order by id desc limit "+page.getOffset()+","+page.getLimit());
        List<PersonalNo> noList = noMapper.list(temp.toString());
        String sql = "select count(*) from "+ZCDBPersonalNo;
        Long count = noMapper.getCount(sql);
        page.setTotal(count.intValue());
        page.setRecords(noList);
        log.info("数据库分页查找个人号数据成功");
        return page;
    }

    /*
     * 批量修改个人号类别
     */
    @Transactional
    @Override
    public boolean batchUpdateCategory(List<PersonalNo> personalIdList, String categoryName) {
        log.info("根据id集合查询个人号列表");
        List<PersonalNo> nos = getPersonalNosByIds(personalIdList);
        for (PersonalNo no : nos) {
            log.info("开始修改查询到的个人号类别名称");
            no.setPersonalNoCategory(categoryName);
            int i = noMapper.updateOne(no);
            if (i == 0) {
                log.info("数据库批量修改个人号类别名称失败");
                return false;
            }
        }
        log.info("数据库批量修改个人号类别名称成功");
        return true;
    }

    /*
     * 批量修改个人号销售组
     */
    @Transactional
    @Override
    public boolean batchUpdateGroup(List<PersonalNo> personalIdList, String object) {
        log.info("根据id集合查询个人号列表");
        List<PersonalNo> nos = getPersonalNosByIds(personalIdList);
        for (PersonalNo no : nos) {
            log.info("开始修改查询到的个人号销售组名称");
            no.setSalesGroup(object);
            int i = noMapper.updateOne(no);
            if (i == 0) {
                log.info("数据库批量修改个人号销售组名称失败");
                return false;
            }
        }
        log.info("数据库批量修改个人号销售组名称成功");
        return true;
    }

    private List<PersonalNo> getPersonalNosByIds(List<PersonalNo> personalIdList) {
        List<Integer> idList = getIdList(personalIdList);
        String ids = DaoGetSql.getIds(idList);
        String sql = DaoGetSql.getSql("select * from " + ZCDBPersonalNo + " where id in " + ids);
        return noMapper.list(sql);
    }

    /*
     *  根据    个人号集合     获得       个人号id集合
     */
    private List<Integer> getIdList(List<PersonalNo> list) {
        List<Integer> idList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            idList.add(list.get(i).getId());
        }
        return idList;
    }
    /**
     * 上报机器人的好友列表
     *
     * @param personalId
     * @return
     */
    @Override
    public boolean updateFriends(Integer personalId) {
        PersonalNo personal = selectById(personalId);
        log.info("下发任务上报好友列表信息");
        PersonalNoPhoneTaskGroup taskGroup = new PersonalNoPhoneTaskGroup();
        taskGroup.setCreateTime(new Date());
        taskGroup.setCurrentRobotId(personal.getWxId());
        taskGroup.setNextStep(1);
        taskGroup.setStatus("未下发");
        taskGroup.setTaskOrder(0);
        taskGroup.setTname(personal.getWxId() + "上传好友列表");
        taskGroup.setTotalStep(1);
        taskGroup.setDb(ZCDBTaskGroup);
        int save = taskGroupService.add(taskGroup);
        if (save==0) {
            log.info("插入任务组失败");
            throw new RuntimeException("插入任务组失败");
        }
        PersonalNoPhoneTask task = new PersonalNoPhoneTask();
        //上传好友列表信息
        task.setTaskType(SunTaskType.UPLOAD_FRIEND_LIST);
        task.setCreateTime(new Date());
        task.setTaskGroupId(taskGroup.getId());
        task.setTname(personal.getWxId() + "上传好友列表");
        task.setRobotId(personal.getWxId());
        task.setStatus("未下发");
        task.setStep(1);
        task.setDb(ZCDBTask);
        int save1 = taskService.add(task);
        if (save1 == 0) {
            throw new RuntimeException(personal.getWxId() + "添加上传好友列表失败");
        }
        return true;
    }

    @Override
    public Integer add(PersonalNo entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return noMapper.add(entity);
        return noMapper.updateOne(entity);
    }

    @Override
    public Integer delete(String sql) {
        return noMapper.delete(sql);
    }

    @Override
    public List<PersonalNo> list(String sql) {
        return noMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return noMapper.listString(sql);
    }

    @Override
    public PersonalNo getOne(String sql) {
        return noMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return noMapper.getCount(sql);
    }
}
