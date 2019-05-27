package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoUser;
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
public interface PersonalNoUserMapper extends BaseMapper<PersonalNoUser> {

    @Select("${sql}")
    PersonalNoUser getBySql(Sql sql);

    Integer add(@Param("entity") PersonalNoUser entity);

    Integer updateOne(@Param("entity")PersonalNoUser entity);

}
