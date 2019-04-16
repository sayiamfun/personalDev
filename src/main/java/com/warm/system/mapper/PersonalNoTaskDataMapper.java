package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoTaskData;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoTaskDataMapper extends BaseMapper<PersonalNoTaskData> {
    //根据任务id查询任务数据
    @Select("SELECT id,personal_no_task_id,click_url_num,to_people_num,deleted FROM personal_no_task_data WHERE personal_no_task_id = #{taskId} and deleted=0 LIMIT 0,1")
    PersonalNoTaskData getByTaskId(Integer taskId);
}
