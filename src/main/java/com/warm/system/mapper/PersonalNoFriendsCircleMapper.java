package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoFriendsCircle;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import groovy.transform.SourceURI;
import org.apache.ibatis.annotations.Param;
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
public interface PersonalNoFriendsCircleMapper extends BaseMapper<PersonalNoFriendsCircle> {

    List<PersonalNoFriendsCircle> list(@Param("sql") String sql);

    List<String> listString(@Param("sql") String sql);

    Integer add(@Param("entity") PersonalNoFriendsCircle entity);

    PersonalNoFriendsCircle getOne(@Param("sql") String sql);

    Integer updateOne(@Param("entity")PersonalNoFriendsCircle entity);

    @Update("${sql}")
    Integer deleteBySql(Sql sql);

    Long getCount(@Param("sql") String sql);
}
