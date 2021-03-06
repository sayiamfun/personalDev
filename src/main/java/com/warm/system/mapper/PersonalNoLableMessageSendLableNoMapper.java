package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoLableMessageSendLableNo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
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
public interface PersonalNoLableMessageSendLableNoMapper extends BaseMapper<PersonalNoLableMessageSendLableNo> {

    List<PersonalNoLableMessageSendLableNo> list(@Param("sql") String sql);

    List<String> listString(@Param("sql") String sql);

    Integer add(@Param("entity") PersonalNoLableMessageSendLableNo entity);

    PersonalNoLableMessageSendLableNo getOne(@Param("sql") String sql);

    Integer updateOne(@Param("entity")PersonalNoLableMessageSendLableNo entity);

    Integer delete(@Param("sql") String sql);

    Long getCount(@Param("sql") String sql);
}
