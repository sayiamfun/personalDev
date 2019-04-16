package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoTaskBeginRemind;
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
public interface PersonalNoTaskBeginRemindMapper extends BaseMapper<PersonalNoTaskBeginRemind> {
    @Select("SELECT id,personal_no_task_id,content_type,content,super_id,deleted FROM personal_no_task_begin_remind WHERE personal_no_task_id = #{id} AND deleted = 0")
    List<PersonalNoTaskBeginRemind> listByTaskId(Integer id);
    @Update("UPDATE personal_no_task_begin_remind SET deleted = 1 WHERE personal_no_task_id = #{taskId} AND deleted = 0")
    void deleteByTaskId(Integer taskId);
}
