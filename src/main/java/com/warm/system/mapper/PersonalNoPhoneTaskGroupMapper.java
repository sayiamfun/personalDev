package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoPhoneTaskGroup;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoPhoneTaskGroupMapper extends BaseMapper<PersonalNoPhoneTaskGroup> {
    @Select("SELECT id,tname,total_step,next_step,status,current_robot_id,group_pool_id,wx_group_id,create_time,pickup_time,finish_time,task_send_id,lable_send_id,task_order FROM personal_no_phone_task_group WHERE task_send_id = #{id}")
    List<PersonalNoPhoneTaskGroup> listByTaskMessageId(Integer id);

    @Select("SELECT id,tname,total_step,next_step,status,current_robot_id,group_pool_id,wx_group_id,create_time,pickup_time,finish_time,task_send_id,lable_send_id,task_order FROM personal_no_phone_task_group WHERE lable_send_id = #{id}")
    List<PersonalNoPhoneTaskGroup> listByLableMessageId(Integer id);

    @Select("SELECT id,tname,total_step,next_step,status,current_robot_id,group_pool_id,wx_group_id,create_time,pickup_time,finish_time,task_send_id,lable_send_id,task_order FROM personal_no_phone_task_group WHERE current_robot_id = #{param1} AND task_order = #{param2} AND status = '未下发' ORDER BY id DESC LIMIT 0,1")
    PersonalNoPhoneTaskGroup listBycurrent_robot_idAndStatusWatingDesc(String wxId, int i);

    @Select("SELECT id,tname,total_step,next_step,status,current_robot_id,group_pool_id,wx_group_id,create_time,pickup_time,finish_time,task_send_id,lable_send_id,task_order FROM personal_no_phone_task_group WHERE current_robot_id = #{param1} AND tname = #{param2} AND status = #{param3} and create_time BETWEEN #{param4} and #{param5}  ORDER BY id DESC LIMIT 0,1")
    PersonalNoPhoneTaskGroup getByPersonalWxIdAndTheme(String wxId, String s, String status, String noeDate, String noeDate1);
}
