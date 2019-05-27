package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoTaskBeginRemind;
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
public interface PersonalNoTaskBeginRemindMapper extends BaseMapper<PersonalNoTaskBeginRemind> {


    Integer add(@Param("entity") PersonalNoTaskBeginRemind entity);

    Integer updateOne(@Param("entity")PersonalNoTaskBeginRemind entity);

    @Delete("${sql}")
    void deleteBySql(Sql sql);

    @Select("${sql}")
    List<PersonalNoTaskBeginRemind> listBySql(Sql sql);
}
