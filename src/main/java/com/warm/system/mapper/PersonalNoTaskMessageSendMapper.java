package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoTaskMessageSend;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoTaskMessageSendMapper extends BaseMapper<PersonalNoTaskMessageSend> {

    Integer add(@Param("entity") PersonalNoTaskMessageSend entity);

    Integer updateOne(@Param("entity") PersonalNoTaskMessageSend entity);


}
