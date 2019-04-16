package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoPeople;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoPeopleMapper extends BaseMapper<PersonalNoPeople> {

    //  根据任务id和个人号微信id查询所有的任务粉丝微信id列表 如果flag为0证明暂时不是好友
    @Select("SELECT DISTINCT personal_friend_wx_id  FROM `personal_no_people` WHERE personal_task_id = #{param1} AND personal_no_wx_id = #{param2} and flag <> 0")
    List<String> listUserWxIdByTaskIdAndPersonalWxId(Integer taskId, String username);
    // 根据个人号wxid查询所有的任务粉丝好友wxid
    @Select("SELECT personal_friend_wx_id FROM `personal_no_people` WHERE personal_no_wx_id = #{wxId} and flag <> 0")
    List<String> listUserWxIdByPersonalWxId(String wxId);
    //根据个人号wxid和添加还有时间查询所有的任务粉丝好友wxid
    @Select("SELECT personal_friend_wx_id FROM `personal_no_people` WHERE personal_no_wx_id = #{param1} and be_friend_time between #{param2} and #{param3} and flag <> 0")
    List<String> listUserWxIdByPersonalWxIdAndTime(String wxId, Date startTime, Date endTime);
    //根据个人号wxid和好友wxId查询不是好友的任务粉丝
    @Select("SELECT id,personal_friend_wx_id,personal_task_id,personal_no_wx_id,channel_id,flag,be_friend_time,remarks,personal_friend_nick_name,deleted FROM `personal_no_people` WHERE personal_no_wx_id = #{param1} and personal_friend_wx_id = #{param2} and deleted = 0 and flag = #{param3} LIMIT 0,1")
    PersonalNoPeople getByPersonalIdAndUserId(String wxId, String username, Integer flag);
    // 根据任务id和用户昵称查找任务粉丝
    @Select("SELECT id,personal_friend_wx_id,personal_task_id,personal_no_wx_id,channel_id,flag,be_friend_time,remarks,personal_friend_nick_name,deleted FROM `personal_no_people` WHERE personal_task_id = #{param1} and personal_friend_nick_name = #{param2} and deleted = 0 LIMIT 0,1")
    PersonalNoPeople getByTaskIdAndUserNickName(int parseInt, String nickname);
    //查询一个任务下的所有粉丝数量
    @Select("SELECT count(*) FROM `personal_no_people` WHERE personal_task_id = #{personaNoTaskId}")
    Integer getPeopleCountByTaskId(Integer personaNoTaskId);
    //查询一个任务下某个时间段的所有粉丝数量
    @Select("SELECT count(*) FROM `personal_no_people` WHERE personal_task_id = #{param1} and be_friend_time between #{param2} and #{param3} and deleted = 0")
    Integer getPeopleCountByTaskIdAndTime(Integer personaNoTaskId, Date startTime, Date endTime);
    //根据个人号微信id和用户微信id查询所有的用户粉丝信息id
    @Select("SELECT id FROM `personal_no_people` WHERE personal_no_wx_id = #{param1} and personal_friend_wx_id = #{param2}")
    List<Integer> listIdByPersonalWxIdAndUserWxId(String username, String userWxId);
    //根据个人号微信id和用户微信id取得最新的一条数据
    @Select("SELECT id,personal_friend_wx_id,personal_task_id,personal_no_wx_id,channel_id,flag,be_friend_time,remarks,personal_friend_nick_name,deleted FROM `personal_no_people` WHERE personal_no_wx_id = #{param1} and personal_friend_wx_id = #{param2} and deleted = 0 order by id desc LIMIT 0,1")
    PersonalNoPeople getByPersonalWxIdAndUserWxId(String username, String userWxId);
    //根据任务id和个人号微信id
    @Select("SELECT DISTINCT personal_friend_wx_id  FROM `personal_no_people` WHERE personal_task_id = #{param1} AND personal_no_wx_id = #{param2} and be_friend_time between #{param3} and #{param4} and flag <> 0")
    List<String> listUserWxIdByTaskIdAndPersonalWxIdAndTime(Integer taskId, String username, Date startTime, Date endTime);
    //根据任务id和用户微信id查询任务粉丝
    @Select("SELECT id,personal_friend_wx_id,personal_task_id,personal_no_wx_id,channel_id,flag,be_friend_time,remarks,personal_friend_nick_name,deleted FROM `personal_no_people` WHERE personal_task_id = #{param1} and personal_friend_wx_id = #{param2} and deleted = 0 LIMIT 0,1")
    PersonalNoPeople getByTaskIdAndUserWxId(int parseInt, String wxId);
    //根据任务id和个人号微信id查询任务粉丝
    @Select("SELECT id,personal_friend_wx_id,personal_task_id,personal_no_wx_id,channel_id,flag,be_friend_time,remarks,personal_friend_nick_name,deleted FROM `personal_no_people` WHERE personal_task_id = #{param1} and personal_no_wx_id = #{param2} and deleted = 0")
    List<PersonalNoPeople> ListByTaskIdAndPersonalWxId(Integer taskId, String personalNoWxId);
}
