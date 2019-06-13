package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoRoad;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoRoadMapper extends BaseMapper<PersonalNoRoad> {

    @Select("${sql}")
    List<PersonalNoRoad> listBySql(Sql sql);

    @Select("${sql}")
    PersonalNoRoad getBySql(Sql sql);

    Integer add(@Param("entity") PersonalNoRoad entity);

    Integer updateOne(@Param("entity") PersonalNoRoad entity);

    @Select("${sql}")
    Long countBySql(Sql sql);

    @Select("${sql}")
    List<PersonalNoRoad> listByQueryPersonalData(Sql sql);

    @Select("${sql}")
    List<String> listStringBySql(Sql sql);
}
