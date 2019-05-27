package com.warm.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoMessage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liwenjie123
 * @since 2019-05-06
 */
public interface PersonalNoMessageMapper extends BaseMapper<PersonalNoMessage> {

    List<PersonalNoMessage> list(@Param("sql") String sql);

    Integer add(@Param("entity") PersonalNoMessage entity);

    List<String> listString(@Param("sql") String sql);

    PersonalNoMessage getOne(@Param("sql") String sql);

    Integer updateOne(@Param("entity")PersonalNoMessage entity);

    Integer delete(@Param("sql") String sql);

    Long getCount(@Param("sql") String sql);

    @Delete("${sql}")
    void deleteBySql(Sql sql);
}
