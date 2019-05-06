package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.query.QueryFriendsCircle;
import com.warm.entity.robot.common.SunTaskType;
import com.warm.system.entity.*;
import com.warm.system.mapper.PersonalNoFriendsCircleMapper;
import com.warm.system.service.db1.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.DaoGetSql;
import com.warm.utils.JsonObjectUtils;
import com.warm.utils.VerifyUtils;
import com.warm.utils.WebConst;
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
    private PersonalNoFriendsCircleMapper noFriendsCircleMapper;
    @Autowired
    private PersonalNoFriendsCirclePersonalService noFriendsCirclePersonalService;
    @Autowired
    private PersonalNoFriendsCirclePhotoService noFriendsCirclePhotoService;
    @Autowired
    private PersonalNoPhoneTaskGroupService taskGroupService;
    @Autowired
    private PersonalNoPhoneTaskService taskService;

    private String ZCDBTask = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_phone_task);
    private String ZCDBTaskGroup = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_phone_task_group);
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
        //根据个人号id得到相关联的所有的朋友圈id列表存入hashSet集合,去重
        StringBuffer temp = new StringBuffer("select * from " + DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_friends_circle));
        boolean F = false;
        if(!VerifyUtils.isEmpty(personal_no_id)){
            log.info("个人号id不为空,开始查询对应的个人号相关朋友圈id信息");
            //查询朋友圈个人号条件
            String sql = "select DISTINCT friends_circle_id from "+DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_friends_circle_personal)+" where personal_no_id = "+personal_no_id;
            //根据个人号id查询所有的朋友圈-个人号信息
            List<String> list = noFriendsCirclePersonalService.listString(sql);
            String ids = DaoGetSql.getIds(list);
            temp.append(" where id in "+ids);
            F= true;
        }
        log.info("开始处理查询朋友圈条件");
        if(!VerifyUtils.isEmpty(friendsCircleTheme)){
            log.info("朋友圈主题模糊查询");
            temp = DaoGetSql.getTempSql(temp, F);
            temp.append(" friends_circle_theme like '%"+friendsCircleTheme+"%'");
            F = true;
        }
        if(!VerifyUtils.isEmpty(status)){
            log.info("根据朋友圈状态查询朋友根据朋友圈主题模糊查询朋友圈圈");
            temp = DaoGetSql.getTempSql(temp, F);
            temp.append(" status = '"+status+"'");
            F = true;
        }
        if(!VerifyUtils.isEmpty(sendTime)){
            log.info("根据开始时间查询朋友圈");
            temp = DaoGetSql.getTempSql(temp, F);
            temp.append(" create_time > '"+WebConst.getNowDate(sendTime)+"'");
            F = true;
        }
        if(!VerifyUtils.isEmpty(endTime)){
            log.info("根据结束时间查询朋友圈");
            temp = DaoGetSql.getTempSql(temp, F);
            temp.append(" create_time < '"+WebConst.getNowDate(sendTime)+"'");
        }
        temp.append(" order by id desc limit "+page.getOffset()+","+page.getLimit());
        List<PersonalNoFriendsCircle> personalNoFriendsCircles = noFriendsCircleMapper.list(temp.toString());
        page.setRecords(personalNoFriendsCircles);
        String sql = DaoGetSql.getSql("select count(*) from " + DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_friends_circle_personal));
        Long count = noFriendsCircleMapper.getCount(sql);
        page.setTotal(count.intValue());
        log.info("数据库分页查询朋友圈结束");
        return page;
    }

    /*
     *  新增朋友圈消息
     */
    @Transactional
    @Override
    public Integer add(PersonalNoFriendsCircle noFriendsCircle) {
        log.info("数据库开始添加朋友圈消息");
        noFriendsCircle.setStatus("0/"+noFriendsCircle.getPersonalList().size());
        noFriendsCircle.setCreateTime(new Date());
        noFriendsCircle.setDb(DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_friends_circle));
        int insert = noFriendsCircleMapper.add(noFriendsCircle);
        if(insert == 0){
            log.info("数据库添加朋友圈失败");
            return 0;
        }
        boolean b = false;
        log.info("批量添加朋友圈个人号");
        b = noFriendsCirclePersonalService.batchSave(noFriendsCircle);
        if(!b){
            log.info("批量添加朋友圈个人号失败");
            return 0;
        }
        log.info("批量添加朋友圈照片");
        b = noFriendsCirclePhotoService.batchSave(noFriendsCircle);
        if(!b){
            log.info("批量添加朋友圈照片信息失败");
            return 0;
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
                taskGroup.setDb(ZCDBTaskGroup);
                int save = taskGroupService.add(taskGroup);
                if (save == 0) {
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
                task.setDb(ZCDBTask);
                int save1 = taskService.add(task);
                if (save1 == 0) {
                    log.info("插入朋友圈任务失败");
                    throw new RuntimeException("插入朋友圈任务失败");
                }
            }
        }
        return 1;
    }

    @Override
    public Integer delete(String sql) {
        return noFriendsCircleMapper.delete(sql);
    }

    @Override
    public List<PersonalNoFriendsCircle> list(String sql) {
        return noFriendsCircleMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return noFriendsCircleMapper.listString(sql);
    }

    @Override
    public PersonalNoFriendsCircle getOne(String sql) {
        return noFriendsCircleMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return noFriendsCircleMapper.getCount(sql);
    }

    /**
     * 根据id查询朋友圈
     * @param id
     * @return
     */
    @Override
    public PersonalNoFriendsCircle getCircleById(Integer id) {
        log.info("数据库根据id查询朋友圈开始");
        String byId = DaoGetSql.getById(DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_friends_circle), id);
        PersonalNoFriendsCircle noFriendsCircle = noFriendsCircleMapper.getOne(byId);
        if(!VerifyUtils.isEmpty(noFriendsCircle)){
            log.info("根据朋友圈ID查询朋友圈个人号");
            String sql = DaoGetSql.getSql("select * from " + DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_friends_circle_personal) + " where friends_circle_id = ?", noFriendsCircle.getId());
            List<PersonalNoFriendsCirclePersonal> personalList = noFriendsCirclePersonalService.list(sql);
            noFriendsCircle.setPersonalNum(personalList.size());
            noFriendsCircle.setPersonalList(personalList);
            log.info("根据朋友圈id查询朋友圈照片");
            sql = DaoGetSql.getSql("select * from "+DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_friends_circle_photo)+" where `friends_circle_id` = ?",id);
            List<PersonalNoFriendsCirclePhoto> photoList = noFriendsCirclePhotoService.list(sql);
            noFriendsCircle.setPhotoList(photoList);
        }
        log.info("数据库根据id查询朋友圈结束");
        return noFriendsCircle;
    }
}
