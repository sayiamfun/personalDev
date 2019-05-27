package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoSuperuesr;
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
public interface PersonalNoSuperuesrMapper extends BaseMapper<PersonalNoSuperuesr> {

    Integer add(@Param("entity") PersonalNoSuperuesr entity);

    Integer updateOne(@Param("entity")PersonalNoSuperuesr entity);

    @Select("${sql}")
    PersonalNoSuperuesr getBySql(Sql sql);

    @Select("${sql}")
    List<PersonalNoSuperuesr> listBySql(Sql sql);
}
