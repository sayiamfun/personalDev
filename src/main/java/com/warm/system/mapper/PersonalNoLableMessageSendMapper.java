package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoLableMessageSend;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoLableMessageSendMapper extends BaseMapper<PersonalNoLableMessageSend> {

    List<PersonalNoLableMessageSend> list(@Param("sql") String sql);

    List<String> listString(@Param("sql") String sql);

    Integer add(@Param("entity") PersonalNoLableMessageSend entity);

    PersonalNoLableMessageSend getOne(@Param("sql") String sql);

    Integer updateOne(@Param("entity")PersonalNoLableMessageSend entity);

    Integer delete(@Param("sql") String sql);

    Long getCount(@Param("sql") String sql);
}
