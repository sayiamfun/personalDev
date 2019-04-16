package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoTaskLable;
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
public interface PersonalNoTaskLableMapper extends BaseMapper<PersonalNoTaskLable> {

    @Select("SELECT id,personal_no_task_id,lable_id,lable_name,deleted FROM personal_no_task_lable WHERE lable_id = #{lableId} and deleted=0")
    List<PersonalNoTaskLable> listByLableId(Integer lableId);
}
