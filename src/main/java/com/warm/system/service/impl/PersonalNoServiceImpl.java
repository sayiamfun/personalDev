package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.query.QueryPersonal;
import com.warm.entity.requre.GetNoEntity;
import com.warm.entity.robot.common.SunTaskType;
import com.warm.system.entity.*;
import com.warm.system.mapper.PersonalNoMapper;
import com.warm.system.service.db1.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
            List<PersonalNoTaskPersonal> personalNoTaskPersonals1 = taskPersonalService.listByTaskId(getNoEntity.getTaskId());
            if(task.getAddFriendIndex()<personalNoTaskPersonals1.size()){
                no = noService.getByWxId(personalNoTaskPersonals1.get(task.getAddFriendIndex()).getPersonalNoWxId());
                if(task.getAddFriendIndex()+1<personalNoTaskPersonals1.size()){
                    task.setAddFriendIndex(task.getAddFriendIndex()+1);
                }
            }
            log.info("将用户添加到任务粉丝表");
            PersonalNoPeople people = peopleService.getByTaskIdAndUserNickName(getNoEntity.getTaskId(), getNoEntity.getOpenId());
            Map<String, Object> map1 = TaskUtiles.getMap(peopleService, taskGroupService, noTaskService, taskService, keywordService);
            if(VerifyUtils.isEmpty(people)){
                people = new PersonalNoPeople();
                people.setPersonalTaskId(getNoEntity.getTaskId());
                people.setFlag(0);
                people.setDeleted(0);
                people.setPersonalFriendNickName(getNoEntity.getOpenId());
                people.setBeFriendTime(new Date());
                people.setPersonalNoWxId(no.getWxId());
                PersonalNoUser userByOpenid = userService.getByOpenid(people.getPersonalFriendNickName());
                if(!VerifyUtils.isEmpty(userByOpenid)){
                    people.setPersonalFriendWxId(userByOpenid.getWxId());
                    people.setFlag(1);
                    TaskUtiles.toTask(map1, people.getPersonalNoWxId(),userByOpenid.getWxId(), getNoEntity.getTaskId(), Integer.parseInt(valueTableService.selectById(1).getValue())*1000);
                }
                boolean save1 = peopleService.insert(people);
                if(!save1){
                    log.info("插入任务粉丝失败（未添加好友）");
                    throw new RuntimeException("插入任务粉丝失败（未添加好友）");
                }
            }else {
                if(VerifyUtils.isEmpty(people.getPersonalFriendWxId())){
                    PersonalNoUser userByOpenid = userService.getByOpenid(people.getPersonalFriendNickName());
                    log.info("已经是任务粉丝，下发任务");
                    if(!VerifyUtils.isEmpty(userByOpenid)) {
                        TaskUtiles.toTask(map1, people.getPersonalNoWxId(), userByOpenid.getWxId(), getNoEntity.getTaskId(), Integer.parseInt(valueTableService.selectById(1).getValue()) * 1000);
                    }
                }else {
                    log.info("已经是任务粉丝，下发任务");
                    TaskUtiles.toTask(map1, people.getPersonalNoWxId(), people.getPersonalFriendWxId(), getNoEntity.getTaskId(), Integer.parseInt(valueTableService.selectById(1).getValue())*1000);
                }
                people.setFlag(1);
                peopleService.updateById(people);
            }
            PersonalNoTaskRemindFlag remindFlag = remindFlagService.getByPersonalWxIdAndUserWxIdAndTaskId(people.getPersonalNoWxId(),people.getPersonalFriendWxId(),people.getPersonalTaskId());
            if(VerifyUtils.isEmpty(remindFlag)) {
                TaskUtiles.toRemindTask(map1,remindFlagService,people.getPersonalNoWxId(), people.getPersonalFriendWxId(), people.getPersonalTaskId(), 0);
            }
            map.put("task", task);
            map.put("no", no);
        }
        return map;
    }

    /**
     * 根据昵称模糊查询
     * @param nickName
     * @return
     */
    @Override
    public List<PersonalNo> ListByNickName(String nickName) {
        EntityWrapper<PersonalNo> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        entityWrapper.like("nick_name", nickName);
        List<PersonalNo> noList = baseMapper.selectList(entityWrapper);
        return noList;
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
            for (Integer o : taskIdSet) {
                List<PersonalNoTaskPersonal> listByTaskId = taskPersonalService.listByTaskId(o);
                if (!VerifyUtils.collectionIsEmpty(listByTaskId)) {
                    for (PersonalNoTaskPersonal personalNoTaskPersonal : listByTaskId) {
                        PersonalNo no = getByWxId(personalNoTaskPersonal.getPersonalNoWxId());
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
    /**
     * 根据微信id查询个人号信息
     *
     * @param personalNoWxId
     * @return
     */
    @Override
    public PersonalNo getByWxId(String personalNoWxId) {
        return baseMapper.selectOneByWxId(personalNoWxId);
    }
    /*
     * 根据类别查询所有个人号
     */
    @Override
    public List<PersonalNo> listByCategory(String category) {
        log.info("开始判断类别参数是否为空");
        List<PersonalNo> noList = null;
        if (VerifyUtils.isEmpty(category)) {
            log.info("类别参数为空,查询所有");
            noList = baseMapper.selectList(null);
        } else {
            log.info("参数不为空,根据类别查询");
            noList = baseMapper.listByCategory(category);
        }
        log.info("根据类别从数据查找个人号成功,返回数据");
        return noList;
    }
    /*
     * 根据昵称模糊查询所有个人号
     */
    @Override
    public List<PersonalNo> listByNickName(String nickName) {
        log.info("开始判断参数是否为空");
        List<PersonalNo> noList = null;
        if (VerifyUtils.isEmpty(nickName)) {
            log.info("参数为空，查询所有");
            noList = baseMapper.selectList(null);
        } else {
            log.info("参数不为空，根据昵称模糊查询个人号");
            noList = baseMapper.listLikeNickName("%" + nickName + "%");
        }
        log.info("模糊查询个人号成功");
        return noList;
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
        EntityWrapper<PersonalNo> entityWrapper = new EntityWrapper<>(new PersonalNo());
        log.info("开始判断查找参数是否为空");
        if(!VerifyUtils.isEmpty(equipmentId)){
            entityWrapper.eq("equipment_id", equipmentId);
        }
        if(!VerifyUtils.isEmpty(equipmentStatus)){
            entityWrapper.eq("equipment_status", equipmentStatus);
        }
        if(!VerifyUtils.isEmpty(personalNoCategoryName)){
            entityWrapper.eq("personal_no_category", personalNoCategoryName);
        }
        if(!VerifyUtils.isEmpty(nickname)){
            entityWrapper.like("nickname", nickname);
        }
        List<String> list = new ArrayList<>();
        list.add("id");
        entityWrapper.orderDesc(list);
        List<PersonalNo> noList = baseMapper.selectPage(page, entityWrapper);
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
        List<Integer> idList = getIdList(personalIdList);
        List<PersonalNo> nos = baseMapper.selectBatchIds(idList);
        for (PersonalNo no : nos) {
            log.info("开始修改查询到的个人号类别名称");
            no.setPersonalNoCategory(categoryName);
            int i = baseMapper.updateById(no);
            if (i != 1) {
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
        List<Integer> idList = getIdList(personalIdList);
        List<PersonalNo> nos = baseMapper.selectBatchIds(idList);
        for (PersonalNo no : nos) {
            log.info("开始修改查询到的个人号销售组名称");
            no.setSalesGroup(object);
            int i = baseMapper.updateById(no);
            if (i != 1) {
                log.info("数据库批量修改个人号销售组名称失败");
                return false;
            }
        }
        log.info("数据库批量修改个人号销售组名称成功");
        return true;
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
        boolean save = taskGroupService.insert(taskGroup);
        if (!save) {
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
        task.setStatus("为下发");
        task.setStep(1);
        boolean save1 = taskService.insert(task);
        if (!save1) {
            throw new RuntimeException(personal.getWxId() + "添加上传好友列表失败");
        }
        return true;
    }
}
