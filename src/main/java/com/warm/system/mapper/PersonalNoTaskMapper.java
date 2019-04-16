package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoTask;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoTaskMapper extends BaseMapper<PersonalNoTask> {

    //flag为1证明已经发送过了
    @Select("SELECT DISTINCT id FROM `personal_no_task_personal` WHERE personal_no_wx_id = #{wxId} and flag = 0")
    List<Integer> listByPersonalWxId(String wxId);

    @Select("SELECT id,task_name,task_theme,popularize_category,recommended_reasons AS recommendedReason,subject_name,fast_group,sales_group_separate_flag,create_sales_task,task_start_time,task_end_time,group_auto_close,pull_people_user_id,auto_remind,activity_type,task_url,task_end_url,super_id,remarks,create_time,message,road_id,add_friend_index,deleted FROM personal_no_task WHERE road_id = #{id} and activity_type = 0 and deleted = 0")
    List<PersonalNoTask> listByRoadId(Integer id);

    @Update("update personal_no_task set activity_type = 1 where id = #{taskId} and deleted = 0")
    void stopTaskById(Integer taskId);

    @Select("select task_name from `personal_no_task` order by id desc")
    List<String> listAllTaskName();
}
