package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoValueTable;
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
public interface PersonalNoValueTableMapper extends BaseMapper<PersonalNoValueTable> {

    @Select("${sql}")
    PersonalNoValueTable getBySql(Sql sql);

    @Select("${sql}")
    List<PersonalNoValueTable> listBySql(Sql sql);

    Integer add(@Param("entity") PersonalNoValueTable entity);

    Integer updateOne(@Param("entity")PersonalNoValueTable entity);

    @Delete("${sql}")
    Integer deleteBySql(Sql sql);
}
