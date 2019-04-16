package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoChannel;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoChannelMapper extends BaseMapper<PersonalNoChannel> {

    @Select("SELECT id,channel_name,super_id,super_name,create_time,remarks,deleted FROM personal_no_channel WHERE channel_name = #{s} AND deleted = 0")
    PersonalNoChannel getByName(String s);
}
