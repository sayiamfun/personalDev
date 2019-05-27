package com.warm.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoTaskAndKeyword;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liwenjie123
 * @since 2019-04-28
 */
public interface PersonalNoTaskAndKeywordMapper extends BaseMapper<PersonalNoTaskAndKeyword> {


    @Select("${sql}")
    List<PersonalNoTaskAndKeyword> listBySql(Sql sql);

    @Select("${sql}")
    Long countBySql(Sql sql);

    void add(@Param("entity") PersonalNoTaskAndKeyword personalNoTaskAndKeyword);

    @Select("${sql}")
    Integer deleteBySql(Sql sql);
}
