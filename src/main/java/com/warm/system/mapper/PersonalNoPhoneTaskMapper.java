package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoPhoneTask;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoPhoneTaskMapper extends BaseMapper<PersonalNoPhoneTask> {
    @Select("DELETE FROM personal_no_phone_task WHERE personal_no_phone_task_group_id = #{id}")
    void deleteByTaskGrouPId(Integer id);
    @Select("SELECT id,task_group_id,tname,step,robot_id,status,tdescription,task_json,group_pool_id,wx_group_id,task_finished_tag,create_time,pickup_time,is_client_feedback_received,feedback_received_time,is_client_feedback_finished,feedback_finished_time,failed_reason,content,content_type,task_type FROM personal_no_phone_task where task_group_id = #{param1} and step = #{param2} limit 0,1")
    PersonalNoPhoneTask getByTaskGroupIdAndStep(Integer id, Integer nextStep);
    @Select("SELECT id,task_group_id,tname,step,robot_id,status,tdescription,task_json,group_pool_id,wx_group_id,task_finished_tag,create_time,pickup_time,is_client_feedback_received,feedback_received_time,is_client_feedback_finished,feedback_finished_time,failed_reason,content,content_type,task_type FROM personal_no_phone_task where task_group_id = #{param1} limit 0,1")
    List<PersonalNoPhoneTask> getByTaskGroupId(Integer id);
}
