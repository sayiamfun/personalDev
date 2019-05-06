package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoPassageClickRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 点击通道链接的记录 Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoPassageClickRecordMapper extends BaseMapper<PersonalNoPassageClickRecord> {

    List<PersonalNoPassageClickRecord> list(@Param("sql") String sql);

    List<String> listString(@Param("sql") String sql);

    Integer add(@Param("entity") PersonalNoPassageClickRecord entity);

    PersonalNoPassageClickRecord getOne(@Param("sql") String sql);

    Integer updateOne(@Param("entity")PersonalNoPassageClickRecord entity);

    Integer delete(@Param("sql") String sql);

    Long getCount(@Param("sql") String sql);
}
