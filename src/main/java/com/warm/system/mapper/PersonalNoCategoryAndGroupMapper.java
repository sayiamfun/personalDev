package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoCategoryAndGroup;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-05-16
 */
public interface PersonalNoCategoryAndGroupMapper extends BaseMapper<PersonalNoCategoryAndGroup> {

    Integer add(@Param("entity") PersonalNoCategoryAndGroup entity);

    Integer updateOne(@Param("entity")PersonalNoCategoryAndGroup entity);

    @Select("${sql}")
    PersonalNoCategoryAndGroup getBySql(Sql sql);

    @Select("${sql}")
    List<String> listStringBySql(Sql sql);

    @Update("${sql}")
    void updateBySql(Sql sql);

    @Select("${sql}")
    Long countBySql(Sql sql);
}
