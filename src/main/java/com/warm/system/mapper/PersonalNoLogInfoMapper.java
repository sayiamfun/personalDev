package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoLogInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-04-25
 */
public interface PersonalNoLogInfoMapper extends BaseMapper<PersonalNoLogInfo> {

    Integer add(@Param("entity") PersonalNoLogInfo entity);

    Integer updateOne(@Param("entity")PersonalNoLogInfo entity);

}
