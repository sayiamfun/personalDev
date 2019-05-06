package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoKeyword;
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
public interface PersonalNoKeywordMapper extends BaseMapper<PersonalNoKeyword> {

    List<PersonalNoKeyword> list(@Param("sql") String sql);

    List<String> listString(@Param("sql") String sql);

    Integer add(@Param("entity") PersonalNoKeyword entity);

    PersonalNoKeyword getOne(@Param("sql") String sql);

    Integer updateOne(@Param("entity")PersonalNoKeyword entity);

    Integer delete(@Param("sql") String sql);

    Long getCount(@Param("sql") String sql);
}
