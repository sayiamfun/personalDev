package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPersonalFriendWxId() {
        return personalFriendWxId;
    }

    public void setPersonalFriendWxId(String personalFriendWxId) {
        this.personalFriendWxId = personalFriendWxId;
    }

    public String getPersonalFriendNickName() {
        return personalFriendNickName;
    }

    public void setPersonalFriendNickName(String personalFriendNickName) {
        this.personalFriendNickName = personalFriendNickName;
    }

    public String getPersonalNoWxId() {
        return personalNoWxId;
    }

    public void setPersonalNoWxId(String personalNoWxId) {
        this.personalNoWxId = personalNoWxId;
    }

    public Integer getPersonalTaskId() {
        return personalTaskId;
    }

    public void setPersonalTaskId(Integer personalTaskId) {
        this.personalTaskId = personalTaskId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Date getBeFriendTime() {
        return beFriendTime;
    }

    public void setBeFriendTime(Date beFriendTime) {
        this.beFriendTime = beFriendTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

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
