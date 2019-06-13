package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoTaskChannel;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import sun.tools.tree.IntExpression;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoTaskChannelMapper extends BaseMapper<PersonalNoTaskChannel> {

    @Select("${sql}")
    List<Integer> listChannelIdsBySql(Sql sql);

    @Select("${sql}")
    Integer getIdBySql(Sql sql);

    @Select("${sql}")
    List<PersonalNoTaskChannel> listBySql(Sql sql);

    @Delete("${sql}")
    boolean deleteBySql(Sql sql);

    Integer add(@Param("entity") PersonalNoTaskChannel entity);

    Integer updateOne(@Param("entity")PersonalNoTaskChannel entity);

    @Update("${sql}")
    void updateBySql(Sql sql);

    @Select("${sql}")
    PersonalNoTaskChannel getBySql(Sql sql);
}
