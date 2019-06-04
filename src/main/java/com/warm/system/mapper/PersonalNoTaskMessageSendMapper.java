package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoTaskMessageSend;
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
public interface PersonalNoTaskMessageSendMapper extends BaseMapper<PersonalNoTaskMessageSend> {

    Integer add(@Param("entity") PersonalNoTaskMessageSend entity);

    Integer updateOne(@Param("entity") PersonalNoTaskMessageSend entity);

    @Select("${sql}")
    List<PersonalNoTaskMessageSend> listBySql(Sql sql);

    @Select("${sql}")
    Long countBySql(Sql sql);
}
