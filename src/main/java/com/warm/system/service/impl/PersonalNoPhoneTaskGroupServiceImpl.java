package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.system.entity.PersonalNoPhoneTaskGroup;
import com.warm.system.mapper.PersonalNoPhoneTaskGroupMapper;
import com.warm.system.service.db1.PersonalNoPhoneTaskGroupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.WebConst;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Service
public class PersonalNoPhoneTaskGroupServiceImpl extends ServiceImpl<PersonalNoPhoneTaskGroupMapper, PersonalNoPhoneTaskGroup> implements PersonalNoPhoneTaskGroupService {
    private static Log log = LogFactory.getLog(PersonalNoPhoneTaskGroupServiceImpl.class);
    @Autowired
    private PersonalNoPhoneTaskGroupMapper taskGroupMapper;
    /**
     * 根据current_robot_id查询正在执行的列表
     * @param currRobotWxid
     * @return
     */
    @Override
    public List<PersonalNoPhoneTaskGroup> listBycurrent_robot_idAndStatusGoingAndTime(String currRobotWxid, Date date, Integer order) {
        log.info("根据机器人微信id和状态，任务级别查找任务组（执行中的任务组）");
        Page<PersonalNoPhoneTaskGroup> page = new Page<>(1, 10);
        EntityWrapper<PersonalNoPhoneTaskGroup> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("current_robot_id", currRobotWxid);
        entityWrapper.eq("status", "未下发");
        entityWrapper.eq("task_order", order);
        long time = date.getTime();
        long startTime = time - 180000L;
        long endTime = time + 180000L;
        entityWrapper.between("create_time", new Date(startTime), new Date(endTime));
        log.info("根据机器人微信id和状态，任务级别查找任务组（执行中的任务组）结束");
        baseMapper.selectPage(page, entityWrapper);
        return page.getRecords();
    }
    /**
     * 根据current_robot_id查询正在等待的列表
     * @param currRobotWxid
     * @return
     */
    @Override
    public List<PersonalNoPhoneTaskGroup> listBycurrent_robot_idAndStatusWating(String currRobotWxid, Integer order) {
        log.info("根据机器人微信id和状态，任务级别查找任务组（未下发的任务组）");
       EntityWrapper<PersonalNoPhoneTaskGroup> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("current_robot_id", currRobotWxid);
        entityWrapper.eq("status", "未下发");
        entityWrapper.eq("task_order", order);
        entityWrapper.le("create_time", new Date());
        log.info("根据机器人微信id和状态，任务级别查找任务组（未下发的任务组）结束");
        return baseMapper.selectList(entityWrapper);
    }

    /**
     * 根据任务消息id或者是标签消息id查询所有的任务组
     * @param id
     * @return
     */
    @Override
    public List<PersonalNoPhoneTaskGroup> listByTaskMessageId(Integer id) {
        log.info("根据任务消息或者标签消息id查询任务组");
        return taskGroupMapper.listByTaskMessageId(id);
    }

    /**
     * 查询最后一个任务
     * @param wxId
     * @param i
     * @return
     */
    @Override
    public PersonalNoPhoneTaskGroup listBycurrent_robot_idAndStatusWatingDesc(String wxId, int i) {
        return taskGroupMapper.listBycurrent_robot_idAndStatusWatingDesc(wxId, i);
    }

    /**
     * 根据个人号微信id和任务主题查找添加好友任务
     * @param wxId
     * @param s
     * @return
     */
    @Override
    public PersonalNoPhoneTaskGroup getByPersonalWxIdAndTheme(String wxId, String s, String status) {
        String nowDate = WebConst.getNowDate(new Date(new Date().getTime() - 5 * 60 * 1000));
        String nowDate1 = WebConst.getNowDate(new Date());
        return taskGroupMapper.getByPersonalWxIdAndTheme(wxId, s, status, nowDate, nowDate1);
    }
}
