package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoPeople;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoPeopleMapper extends BaseMapper<PersonalNoPeople> {

    List<PersonalNoPeople> list(@Param("sql") String sql);

    List<String> listString(@Param("sql") String sql);

    Integer add(@Param("entity") PersonalNoPeople entity);

    PersonalNoPeople getOne(@Param("sql") String sql);

    Integer updateOne(@Param("entity")PersonalNoPeople entity);

    Integer delete(@Param("sql") String sql);

    Long getCount(@Param("sql") String sql);

}
