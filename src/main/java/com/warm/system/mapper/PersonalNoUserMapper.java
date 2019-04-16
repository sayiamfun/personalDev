package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoUserMapper extends BaseMapper<PersonalNoUser> {

    @Select("SELECT id,wx_id,wx_name,nick_name,head_portrait,address,phone,gender,create_time,openid,unionid,deleted FROM `personal_no_user` WHERE wx_id = #{username} and openid is not null and deleted=0")
    PersonalNoUser getByWxId(String username);

    @Select("SELECT id,wx_id,wx_name,nick_name,head_portrait,address,phone,gender,create_time,openid,unionid,deleted FROM `personal_no_user` WHERE openid = #{openid} and deleted=0")
    PersonalNoUser getByOpenid(String openid);

    @Select("SELECT id,wx_id,wx_name,nick_name,head_portrait,address,phone,gender,create_time,openid,unionid,deleted FROM `personal_no_user` WHERE wx_id = #{wxId} and deleted=0")
    List<PersonalNoUser> listByWxId(String wxId);

    @Select("SELECT id,wx_id,wx_name,nick_name,head_portrait,address,phone,gender,create_time,openid,unionid,deleted FROM `personal_no_user` WHERE unionid = #{unionid} and deleted=0")
    PersonalNoUser getByUnionId(String unionid);
}
