package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoBlacklist;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoBlacklistMapper extends BaseMapper<PersonalNoBlacklist> {

    List<PersonalNoBlacklist> list(@Param("sql") String sql);

    List<String> listString(@Param("sql") String sql);

    Integer add(@Param("entity") PersonalNoBlacklist entity);

    PersonalNoBlacklist getOne(@Param("sql") String sql);

    Integer updateOne(@Param("entity")PersonalNoBlacklist entity);

    @Update("${sql}")
    Integer deleteBySql(Sql sql);

    Long getCount(@Param("sql") String sql);
}
