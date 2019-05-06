package com.warm.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.warm.system.entity.PersonalNoAndKeyword;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-04-17
 */
public interface PersonalNoAndKeywordMapper extends BaseMapper<PersonalNoAndKeyword> {

    List<PersonalNoAndKeyword> list(@Param("sql") String sql);

    List<String> listString(@Param("sql") String sql);

    int add(@Param("entity") PersonalNoAndKeyword sql);

    PersonalNoAndKeyword getOne(@Param("sql") String sql);

    int updateOneById(@Param("entity")PersonalNoAndKeyword user);

    int delete(@Param("sql") String sql);
}
