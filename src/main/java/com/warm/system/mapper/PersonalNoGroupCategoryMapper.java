package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoGroupCategory;
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
public interface PersonalNoGroupCategoryMapper extends BaseMapper<PersonalNoGroupCategory> {

    List<PersonalNoGroupCategory> list(@Param("sql") String sql);

    List<String> listString(@Param("sql") String sql);

    PersonalNoGroupCategory getOne(@Param("sql") String sql);

    @Update("${sql}")
    Integer deleteBySql(Sql sql);

    Long getCount(@Param("sql") String sql);

    Integer add(@Param("entity") PersonalNoGroupCategory entity);

    Integer updateOne(@Param("entity")PersonalNoGroupCategory entity);

}
