package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.query.QueryFriendsCircle;
import com.warm.entity.robot.common.SunTaskType;
import com.warm.system.entity.*;
import com.warm.system.mapper.PersonalNoFriendsCircleMapper;
import com.warm.system.service.db1.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.JsonObjectUtils;
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
public class PersonalNoFriendsCircleServiceImpl extends ServiceImpl<PersonalNoFriendsCircleMapper, PersonalNoFriendsCircle> implements PersonalNoFriendsCircleService {
    private static Log log = LogFactory.getLog(PersonalNoFriendsCircleServiceImpl.class);
    @Autowired
    private PersonalNoFriendsCirclePersonalService noFriendsCirclePersonalService;
    @Autowired
    private PersonalNoFriendsCirclePhotoService noFriendsCirclePhotoService;
    @Autowired
    private PersonalNoPhoneTaskGroupService taskGroupService;
    @Autowired
    private PersonalNoPhoneTaskService taskService;
    /*
     *  条件分页查询朋友圈
     */
    @Override
    public Page<PersonalNoFriendsCircle> pageQuery(Page<PersonalNoFriendsCircle> page, QueryFriendsCircle searchObj) {
        log.info("开始处理查询参数");
        Integer personal_no_id = searchObj.getPersonal_no_id();
        String friendsCircleTheme = searchObj.getFriendsCircleTheme();
        String status = searchObj.getStatus();
        Date sendTime = searchObj.getSendTime();
        Date endTime = searchObj.getEndTime();
        //查询个人号条件
        EntityWrapper<PersonalNoFriendsCircle> friendsClrcleQuery = new EntityWrapper<>();
        friendsClrcleQuery.orderDesc(Arrays.asList(new String[]{"id"}));
        //根据个人号id得到相关联的所有的朋友圈id列表存入hashSet集合,去重
        Set<Integer> friendsCircleIdList = new HashSet<>();
        if(!VerifyUtils.isEmpty(personal_no_id)){
            log.info("个人号id不为空,开始查询对应的个人号相关朋友圈id信息");
            //查询朋友圈个人号条件
            EntityWrapper<PersonalNoFriendsCirclePersonal> personalentityWrapper = new EntityWrapper<>();
            friendsClrcleQuery.orderDesc(Arrays.asList(new String[]{"id"}));
            log.info("根据个人号id查询朋友圈个人号信息");
            personalentityWrapper.eq("personal_no_id" , "personal_no_id");
            //根据个人号id查询所有的朋友圈-个人号信息
            List<PersonalNoFriendsCirclePersonal> list = noFriendsCirclePersonalService.selectList(personalentityWrapper);
            if(!VerifyUtils.collectionIsEmpty(list)){
                log.info("与个人号相关朋友圈不为空,将朋友圈id放入hashSet集合,做去重处理");
                //如果集合不为空,则将朋友圈id去重放入集合
                for (PersonalNoFriendsCirclePersonal noFriendsCirclePersonal : list) {
                    friendsCircleIdList.add(noFriendsCirclePersonal.getFriendsCircleId());
                }
                log.info("朋友圈id去重成功");
                //个人号id如果不为空,则查询朋友圈需要放入朋友圈id条件
                log.info("根据朋友圈id查询朋友圈");
                friendsClrcleQuery.in("id" , friendsCircleIdList);
            }
        }
        log.info("开始处理查询朋友圈条件");
        if(!VerifyUtils.isEmpty(friendsCircleTheme)){
            log.info("");
            friendsClrcleQuery.like("friends_circle_theme" , friendsCircleTheme);
        }
        if(!VerifyUtils.isEmpty(status)){
            log.info("根据朋友圈状态查询朋友根据朋友圈主题模糊查询朋友圈圈");
            friendsClrcleQuery.eq("status" , status);
        }
        if(!VerifyUtils.isEmpty(sendTime)){
            log.info("根据开始时间查询朋友圈");
            friendsClrcleQuery.ge("create_time" , sendTime);
        }
        if(!VerifyUtils.isEmpty(endTime)){
            log.info("根据结束时间查询朋友圈");
            friendsClrcleQuery.le("create_time" , endTime);
        }
        List<PersonalNoFriendsCircle> personalNoFriendsCircles = baseMapper.selectPage(page, friendsClrcleQuery);
        page.setRecords(personalNoFriendsCircles);
        log.info("数据库分页查询朋友圈结束");
        return page;
    }
    /*
     *  新增朋友圈消息
     */
    @Transactional
    @Override
    public boolean insertNoFriendCircle(PersonalNoFriendsCircle noFriendsCircle) {
        log.info("数据库开始添加朋友圈消息");
        noFriendsCircle.setStatus("0/"+noFriendsCircle.getPersonalList().size());
        noFriendsCircle.setCreateTime(new Date());
        int insert = baseMapper.insert(noFriendsCircle);
        if(insert != 1){
            log.info("数据库添加朋友圈失败");
            return false;
        }
        boolean b = false;
        log.info("批量添加朋友圈个人号");
        b = noFriendsCirclePersonalService.batchSave(noFriendsCircle);
        if(!b){
            log.info("批量添加朋友圈个人号失败");
            return false;
        }
        log.info("批量添加朋友圈照片");
        b = noFriendsCirclePhotoService.batchSave(noFriendsCircle);
        if(!b){
            log.info("批量添加朋友圈照片信息失败");
            return false;
        }
        log.info("开始处理朋友圈任务");
        if(VerifyUtils.isEmpty(noFriendsCircle.getAutoSend())){
            log.info("直接发送");
            for (PersonalNoFriendsCirclePersonal noFriendsCirclePersonal : noFriendsCircle.getPersonalList()) {
                PersonalNoPhoneTaskGroup taskGroup = new PersonalNoPhoneTaskGroup();
                taskGroup.setStatus("未下发");
                taskGroup.setCurrentRobotId(noFriendsCirclePersonal.getPersonalNoWxId());
                taskGroup.setTotalStep(noFriendsCircle.getPhotoList().size());
                taskGroup.setNextStep(1);
                taskGroup.setTname(noFriendsCirclePersonal.getPersonalNoWxId() + "发送朋友圈");
                taskGroup.setTaskOrder(0);
                taskGroup.setCreateTime(new Date());
                taskGroup.setTaskSendId(-1);
                taskGroup.setLableSendId(noFriendsCircle.getId());
                boolean save = taskGroupService.insert(taskGroup);
                if (!save) {
                    log.error("插入朋友圈任务组失败");
                    throw new RuntimeException("插入朋友圈任务组失败");
                }
                PersonalNoPhoneTask task = new PersonalNoPhoneTask();
                task.setTaskGroupId(taskGroup.getId());
                task.setCreateTime(new Date());
                task.setContent(noFriendsCircle.getFriendsCircleOfficial());
                task.setTaskType(SunTaskType.TIMELINE_NORMAL);
                task.setTname(noFriendsCirclePersonal.getPersonalNoWxId() + "发送照片朋友圈");
                List<String> list = new ArrayList<>();
                for (PersonalNoFriendsCirclePhoto noFriendsCirclePhoto : noFriendsCircle.getPhotoList()) {
                    list.add(noFriendsCirclePhoto.getPhoto());
                }
                task.setTaskJson(JsonObjectUtils.objectToJson(list));
                task.setStatus("未下发");
                task.setStep(1);
                boolean save1 = taskService.insert(task);
                if (!save1) {
                    log.info("插入朋友圈任务失败");
                    throw new RuntimeException("插入朋友圈任务失败");
                }
            }
        }
        return true;
    }

    /**
     * 根据id查询朋友圈
     * @param id
     * @return
     */
    @Override
    public PersonalNoFriendsCircle getCircleById(Integer id) {
        log.info("数据库根据id查询朋友圈开始");
        PersonalNoFriendsCircle noFriendsCircle = baseMapper.selectById(id);
        if(!VerifyUtils.isEmpty(noFriendsCircle)){
            log.info("根据朋友圈ID查询朋友圈个人号");
            List<PersonalNoFriendsCirclePersonal> personalList = noFriendsCirclePersonalService.listByCircleId(noFriendsCircle.getId());
            noFriendsCircle.setPersonalNum(personalList.size());
            noFriendsCircle.setPersonalList(personalList);
            log.info("根据朋友圈id查询朋友圈照片");
            List<PersonalNoFriendsCirclePhoto> photoList = noFriendsCirclePhotoService.listByCircleId(noFriendsCircle.getId());
            noFriendsCircle.setPhotoList(photoList);
        }
        log.info("数据库根据id查询朋友圈结束");
        return noFriendsCircle;
    }
}
