package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoTaskChannel;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Select;
import sun.tools.tree.IntExpression;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoTaskChannelMapper extends BaseMapper<PersonalNoTaskChannel> {

    @Select("select * from personal_zc_01.personal_no_task_channel where personal_no_task_id = #{param1} and channel_id = #{param2} and road_or_task = #{param3} limit 0,1")
    PersonalNoTaskChannel selectByTaskIdAndChannelId(String roadId, Integer channelId, Integer roadOrTask);
}
