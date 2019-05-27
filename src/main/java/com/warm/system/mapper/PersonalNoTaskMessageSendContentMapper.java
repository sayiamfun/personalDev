package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoTaskMessageSendContent;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
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
public interface PersonalNoTaskMessageSendContentMapper extends BaseMapper<PersonalNoTaskMessageSendContent> {

    Integer add(@Param("entity") PersonalNoTaskMessageSendContent entity);

    Integer updateOne(@Param("entity")PersonalNoTaskMessageSendContent entity);

    @Delete("${sql}")
    void delBySql(Sql sql);

    @Select("${sql}")
    List<PersonalNoTaskMessageSendContent> listBySql(Sql sql);
}
