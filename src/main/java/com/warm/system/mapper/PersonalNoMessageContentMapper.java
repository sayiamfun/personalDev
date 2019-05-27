package com.warm.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoMessageContent;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.aspectj.lang.annotation.DeclareError;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liwenjie123
 * @since 2019-05-06
 */
public interface PersonalNoMessageContentMapper extends BaseMapper<PersonalNoMessageContent> {

    Integer add(@Param("entity")PersonalNoMessageContent entity);

    Integer updateOne(@Param("entity")PersonalNoMessageContent entity);

    @Delete("${sql}")
    void deleteBySql(Sql sql);

    @Select("${sql}")
    List<PersonalNoMessageContent> listBySql(Sql sql);
}
