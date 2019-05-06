package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoWxGroup;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoWxGroupMapper extends BaseMapper<PersonalNoWxGroup> {
    List<PersonalNoWxGroup> list(@Param("sql") String sql);

    List<String> listString(@Param("sql") String sql);

    PersonalNoWxGroup getOne(@Param("sql") String sql);

    Long getCount(@Param("sql") String sql);
}
