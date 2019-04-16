package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoPhoneTaskGroup;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoPhoneTaskGroupService extends IService<PersonalNoPhoneTaskGroup> {

    List<PersonalNoPhoneTaskGroup> listBycurrent_robot_idAndStatusGoingAndTime(String currRobotWxid, Date date, Integer order);

    List<PersonalNoPhoneTaskGroup> listBycurrent_robot_idAndStatusWating(String wxid_o72bs8evoigc22, Integer order);

    List<PersonalNoPhoneTaskGroup> listByTaskMessageId(Integer id);

    PersonalNoPhoneTaskGroup listBycurrent_robot_idAndStatusWatingDesc(String wxId, int i);

    PersonalNoPhoneTaskGroup getByPersonalWxIdAndTheme(String wxId, String s, String status);
}
