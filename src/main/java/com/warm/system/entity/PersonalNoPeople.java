package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Data
@TableName("personal_no_people")
public class PersonalNoPeople extends Model<PersonalNoPeople> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 个人号好友微信id
     */
    @TableField("personal_friend_wx_id")
    private String personalFriendWxId;
    /**
     * 个人号好友昵称
     */
    @TableField("personal_friend_nick_name")
    private String personalFriendNickName;
    /**
     * 个人号微信id
     */
    @TableField("personal_no_wx_id")
    private String personalNoWxId;
    /**
     * 个人号任务id
     */
    @TableField("personal_task_id")
    private Integer personalTaskId;
    /**
     * 渠道id
     */
    @TableField("channel_id")
    private Integer channelId;
    /**
     * 加好友时间
     */
    @TableField("be_friend_time")
    private Date beFriendTime;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 逻辑删除
     */
    private Integer deleted;
    /**
     * 是否已经添加好友0为未添加
     */
    private Integer flag;

    @TableField("lable")
    private String lable;

    @TableField(exist = false)
    private String db;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoPeople{" +
        "id=" + id +
        ", personalFriendWxId=" + personalFriendWxId +
        ", personalFriendNickName=" + personalFriendNickName +
        ", personalNoWxId=" + personalNoWxId +
        ", personalTaskId=" + personalTaskId +
        ", channelId=" + channelId +
        ", beFriendTime=" + beFriendTime +
        ", remarks=" + remarks +
        ", deleted=" + deleted +
        ", flag=" + flag +
        "}";
    }
}
