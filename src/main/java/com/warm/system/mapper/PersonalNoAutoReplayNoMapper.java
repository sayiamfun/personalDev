package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoAutoReplayNo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-04-03
 */
public interface PersonalNoAutoReplayNoMapper extends BaseMapper<PersonalNoAutoReplayNo> {
    List<PersonalNoAutoReplayNo> list(@Param("sql") String sql);

    List<String> listString(@Param("sql") String sql);

    int add(@Param("entity") PersonalNoAutoReplayNo entity);

    PersonalNoAutoReplayNo getOne(@Param("sql") String sql);

    int updateOne(@Param("entity")PersonalNoAutoReplayNo entity);

    int delete(@Param("sql") String sql);

    long getCount(@Param("sql") String sql);
}
