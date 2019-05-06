package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoKeywordContent;
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
public interface PersonalNoKeywordContentMapper extends BaseMapper<PersonalNoKeywordContent> {

    List<PersonalNoKeywordContent> list(@Param("sql") String sql);

    List<String> listString(@Param("sql") String sql);

    Integer add(@Param("entity") PersonalNoKeywordContent entity);

    PersonalNoKeywordContent getOne(@Param("sql") String sql);

    Integer updateOne(@Param("entity")PersonalNoKeywordContent entity);

    Integer delete(@Param("sql") String sql);

    Long getCount(@Param("sql") String sql);

}
