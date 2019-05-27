package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoFriendsCirclePhoto;
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
public interface PersonalNoFriendsCirclePhotoMapper extends BaseMapper<PersonalNoFriendsCirclePhoto> {

    List<PersonalNoFriendsCirclePhoto> list(@Param("sql") String sql);

    List<String> listString(@Param("sql") String sql);

    Integer add(@Param("entity") PersonalNoFriendsCirclePhoto entity);

    PersonalNoFriendsCirclePhoto getOne(@Param("sql") String sql);

    Integer updateOne(@Param("entity")PersonalNoFriendsCirclePhoto entity);

    Integer delete(@Param("sql") String sql);

    Long getCount(@Param("sql") String sql);
}
