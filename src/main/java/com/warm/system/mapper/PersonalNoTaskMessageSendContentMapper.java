package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoTaskMessageSendContent;
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
public interface PersonalNoTaskMessageSendContentMapper extends BaseMapper<PersonalNoTaskMessageSendContent> {

    @Select("SELECT id,personal_no_task_message_send_id,content_type,content,deleted FROM personal_no_task_message_send_content WHERE personal_no_task_message_send_id = #{id} AND deleted = 0")
    List<PersonalNoTaskMessageSendContent> listByTaskMessageContentId(Integer id);
    @Select("UPDATE personal_no_task_message_send_content SET deleted = 1 WHERE personal_no_task_message_send_id = #{id} AND deleted = 0")
    void deleteByTaskMessageSend(Integer id);
}
