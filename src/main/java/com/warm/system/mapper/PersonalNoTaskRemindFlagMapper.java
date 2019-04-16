package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoTaskRemindFlag;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-04-10
 */
public interface PersonalNoTaskRemindFlagMapper extends BaseMapper<PersonalNoTaskRemindFlag> {

    @Select("select id,personal_no_wx_id,user_wx_id,personal_no_task_id from personal_zc_01.personal_no_task_remind_flag where personal_no_wx_id = #{param1} and user_wx_id = #{param2} and personal_no_task_id = #{param3}")
    PersonalNoTaskRemindFlag getByPersonalWxIdAndUserWxIdAndTaskId(String personalNoWxId, String personalFriendWxId, Integer personalTaskId);
}
