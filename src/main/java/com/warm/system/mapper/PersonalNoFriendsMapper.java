package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoFriends;
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
public interface PersonalNoFriendsMapper extends BaseMapper<PersonalNoFriends> {
    //根据个人号id和用户微信id查询用户好友信息
    @Select("SELECT id,personal_no_id,personal_no_wx_id,user_id,user_wx_id,be_friend_time,remarks,deleted FROM `personal_no_friends` WHERE personal_no_id = #{param1} and user_wx_id = #{param2} and deleted=0 LIMIT 0,1")
    PersonalNoFriends getByPersonalIdAndUserWxId(Integer logicId, String username);
    //根据个人号微信id查询所有的个人号好友
    @Select("SELECT id,personal_no_id,personal_no_wx_id,user_id,user_wx_id,be_friend_time,remarks,deleted FROM `personal_no_friends` WHERE personal_no_wx_id = #{username} and deleted=0")
    List<PersonalNoFriends> listByPersonalWxId(String username);
    //根据个人号微信id查询所有的好友微信id
    @Select("SELECT user_wx_id FROM `personal_no_friends` WHERE personal_no_wx_id = #{personalWxId} and deleted=0")
    List<String> listUserWxIdByPersonalWxId(String personalWxId);
    //根据个人号微信id和用户微信id查询个人号好友
    @Select("SELECT id,personal_no_id,personal_no_wx_id,user_id,user_wx_id,be_friend_time,remarks,deleted FROM `personal_no_friends` WHERE personal_no_wx_id = #{param1} and user_wx_id = #{param2} and deleted=0 LIMIT 0,1")
    PersonalNoFriends getByPersonalWxIdAndUserWxId(String personalNoWxId, String wxId);
    //根据个人号微信id和用户微信id查询所有的个人号好友id
    @Select("SELECT id FROM `personal_no_friends` WHERE personal_no_wx_id = #{param1} and user_wx_id = #{param2} and deleted=0")
    List<Integer> listByPersonalWxIdAndUserWxId(String wxId, String fromUsername);
}
