package com.warm.system.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.query.QueryPersonal;
import com.warm.system.entity.PersonalNo;
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
public interface PersonalNoMapper extends BaseMapper<PersonalNo> {

    List<PersonalNo> list(@Param("sql") String sql);

    List<String> listString(@Param("sql") String sql);

    Integer add(@Param("entity") PersonalNo entity);

    PersonalNo getOne(@Param("sql") String sql);

    Integer updateOne(@Param("entity")PersonalNo entity);

    Integer delete(@Param("sql") String sql);

    Long getCount(@Param("sql") String sql);
}
