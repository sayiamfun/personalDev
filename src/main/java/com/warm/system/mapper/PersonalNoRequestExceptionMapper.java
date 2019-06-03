package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoRequestException;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoRequestExceptionMapper extends BaseMapper<PersonalNoRequestException> {

    Integer add(@Param("entity") PersonalNoRequestException entity);

    Integer updateOne(@Param("entity") PersonalNoRequestException entity);

}
