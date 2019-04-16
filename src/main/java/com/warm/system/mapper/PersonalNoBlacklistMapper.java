package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoBlacklist;
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
public interface PersonalNoBlacklistMapper extends BaseMapper<PersonalNoBlacklist> {

    //根据微信id查询黑名单成员
    @Select("SELECT id,wx_id,nick_name,deleted FROM personal_no_blacklist WHERE wx_id = #{wxId} and deleted = 0")
    PersonalNoBlacklist getByWxId(String wxId);
}
