package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.entity.result.ResultPersonalData;
import com.warm.system.entity.ShortUrl;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-05-09
 */
public interface ShortUrlMapper extends BaseMapper<ShortUrl> {


    Integer add(@Param("entity") ShortUrl entity);

    Integer updateOne(@Param("entity")ShortUrl entity);

    @Select("${sql}")
    ShortUrl getBySql(Sql sql);
}
