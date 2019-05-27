package com.warm.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoSendMessage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liwenjie123
 * @since 2019-05-06
 */
public interface PersonalNoSendMessageMapper extends BaseMapper<PersonalNoSendMessage> {


    Integer add(@Param("entity") PersonalNoSendMessage entity);

    Integer updateOne(@Param("entity")PersonalNoSendMessage entity);

    @Select("${sql}")
    List<PersonalNoSendMessage> listbySql(Sql sql);

    @Select("${sql}")
    List<String> listString(Sql sql);

    @Select("${sql}")
    PersonalNoSendMessage getBySql(Sql sql);

    @Select("${sql}")
    Integer delete(Sql sql);

    @Select("${sql}")
    List<PersonalNoSendMessage> pageByNickName(Sql sql);

    @Select("${sql}")
    Long getCount(Sql s);

    @Delete("${sql}")
    void deleteBySql(Sql sql);
}
