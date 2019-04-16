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
@TableName("personal_no_friends")
public class PersonalNoFriends extends Model<PersonalNoFriends> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 个人号Id
     */
    @TableField("personal_no_id")
    private Integer personalNoId;
    /**
     * 个人号微信id
     */
    @TableField("personal_no_wx_id")
    private String personalNoWxId;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 用户微信id
     */
    @TableField("user_wx_id")
    private String userWxId;
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPersonalNoId() {
        return personalNoId;
    }

    public void setPersonalNoId(Integer personalNoId) {
        this.personalNoId = personalNoId;
    }

    public String getPersonalNoWxId() {
        return personalNoWxId;
    }

    public void setPersonalNoWxId(String personalNoWxId) {
        this.personalNoWxId = personalNoWxId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserWxId() {
        return userWxId;
    }

    public void setUserWxId(String userWxId) {
        this.userWxId = userWxId;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoFriends{" +
        "id=" + id +
        ", personalNoId=" + personalNoId +
        ", personalNoWxId=" + personalNoWxId +
        ", userId=" + userId +
        ", userWxId=" + userWxId +
        ", beFriendTime=" + beFriendTime +
        ", remarks=" + remarks +
        ", deleted=" + deleted +
        "}";
    }
}
