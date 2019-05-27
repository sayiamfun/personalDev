package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PassageVisitorRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-05-09
 */
public interface PassageVisitorRecordMapper extends BaseMapper<PassageVisitorRecord> {

    @Select("${sql}")
    PassageVisitorRecord getBySl(Sql sql);

    Integer add(@Param("entity") PassageVisitorRecord entity);

    Integer updateOne(@Param("entity") PassageVisitorRecord entity);
}
