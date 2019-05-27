package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoSmallFace;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 表情库 Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoSmallFaceMapper extends BaseMapper<PersonalNoSmallFace> {

    Integer add(@Param("entity") PersonalNoSmallFace entity);

    Integer updateOne(@Param("entity")PersonalNoSmallFace entity);

    @Select("${sql}")
    List<PersonalNoSmallFace> listBySql(Sql sql);

    @Delete("${sql}")
    void deleteBySql(Sql sql);
}
