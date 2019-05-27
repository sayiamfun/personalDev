package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoAccessTocken;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 微信登录验证 Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoAccessTockenMapper extends BaseMapper<PersonalNoAccessTocken> {

    int add(@Param("entity") PersonalNoAccessTocken entity);

    List<String> listOne(@Param("sql") String sql);

    PersonalNoAccessTocken getOne(@Param("sql") String sql);

    int updateOneById(@Param("entity")PersonalNoAccessTocken entity);

    int delete(@Param("sql") String sql);
}
