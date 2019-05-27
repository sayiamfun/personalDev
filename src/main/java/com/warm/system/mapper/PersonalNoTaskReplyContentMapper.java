package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoTaskReplyContent;
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
public interface PersonalNoTaskReplyContentMapper extends BaseMapper<PersonalNoTaskReplyContent> {

    @Select("${sql}")
    PersonalNoTaskReplyContent getBySql(Sql sql);

    @Delete("${sql}")
    Integer deleteBySql(Sql sql);

    @Select("${sql}")
    List<PersonalNoTaskReplyContent> listBySql(Sql sql);

    Integer add(@Param("entity") PersonalNoTaskReplyContent entity);

    Integer updateOne(@Param("entity")PersonalNoTaskReplyContent entity);

}
