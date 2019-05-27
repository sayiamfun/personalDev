package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoTask;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
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
public interface PersonalNoTaskMapper extends BaseMapper<PersonalNoTask> {

    @Select("${sql}")
    List<PersonalNoTask> listByQueryPersonalData(Sql sql);

    @Select("${sql}")
    List<PersonalNoTask> listBySql(Sql sql);

    Integer add(@Param("entity") PersonalNoTask entity);

    Integer updateOne(@Param("entity")PersonalNoTask entity);

    @Select("${sql}")
    List<String> listtaskNamesBySql(Sql sql);

    @Update("${sql}")
    boolean updateBySql(Sql sql);

    @Select("${sql}")
    Long countBySql(Sql sql);

    @Select("${sql}")
    PersonalNoTask getbySql(Sql sql);

    @Delete("${sql}")
    int deleteBySql(Sql sql);
}
