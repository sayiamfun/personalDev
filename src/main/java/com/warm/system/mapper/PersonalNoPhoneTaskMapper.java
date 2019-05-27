package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoPhoneTask;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
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
public interface PersonalNoPhoneTaskMapper extends BaseMapper<PersonalNoPhoneTask> {

    List<PersonalNoPhoneTask> list(@Param("sql") String sql);

    int add(@Param("entity") PersonalNoPhoneTask entity);

    List<String> listString(@Param("sql") String sql);

    PersonalNoPhoneTask getOne(@Param("sql") String sql);

    int updateOne(@Param("entity")PersonalNoPhoneTask entity);

    int delete(@Param("sql") String sql);

    Long getCount(@Param("sql") String sql);

    @Update("${sql}")
    Integer updateBySql(Sql sql);
}
