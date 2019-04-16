package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoTaskPersonal;
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
public interface PersonalNoTaskPersonalMapper extends BaseMapper<PersonalNoTaskPersonal> {

    @Select("SELECT id,personal_no_task_id,personal_no_wx_id,personal_no_name,to_people_num,equipment_id,deleted FROM personal_no_task_personal WHERE personal_no_task_id = #{id} and deleted=0 ")
    List<PersonalNoTaskPersonal> listByTaskId(Integer id);
    @Update("UPDATE personal_no_task_personal SET deleted = 1 WHERE personal_no_task_id = #{id} AND deleted = 0")
    void deleteByTaskId(Integer id);
    @Select("SELECT id,personal_no_task_id,personal_no_wx_id,personal_no_name,to_people_num,equipment_id,deleted FROM personal_no_task_personal WHERE personal_no_wx_id = #{personalWxId} and deleted=0 ")
    List<PersonalNoTaskPersonal> listByPersonalId(String personalWxId);
    @Select("SELECT id,personal_no_task_id,personal_no_wx_id,personal_no_name,to_people_num,equipment_id,deleted FROM personal_no_task_personal WHERE personal_no_task_id = #{param1} and personal_no_wx_id = #{param2} and deleted=0 order by id desc limit 0,1")
    PersonalNoTaskPersonal getByTaskIdAndPersonalWxId(Integer integer, String personalWxId);
}
