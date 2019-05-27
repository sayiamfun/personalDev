package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoTaskRemindFlag;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-04-10
 */
public interface PersonalNoTaskRemindFlagMapper extends BaseMapper<PersonalNoTaskRemindFlag> {

    @Select("${sql}")
    PersonalNoTaskRemindFlag getBySql(Sql sql);

    Integer add(@Param("entity") PersonalNoTaskRemindFlag entity);

    Integer updateOne(@Param("entity")PersonalNoTaskRemindFlag entity);

}
