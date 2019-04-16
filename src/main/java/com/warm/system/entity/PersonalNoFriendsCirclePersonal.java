package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
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
@TableName("personal_no_friends_circle_personal")
public class PersonalNoFriendsCirclePersonal extends Model<PersonalNoFriendsCirclePersonal> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 朋友圈id
     */
    @TableField("friends_circle_id")
    private Integer friendsCircleId;
    /**
     * 个人号id
     */
    @TableField("personal_no_id")
    private Integer personalNoId;
    /**
     * 个人号微信id
     */
    @TableField("personal_no_wx_id")
    private String personalNoWxId;
    /**
     * 个人号名称
     */
    @TableField("personal_no_name")
    private String personalNoName;
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

    public Integer getFriendsCircleId() {
        return friendsCircleId;
    }

    public void setFriendsCircleId(Integer friendsCircleId) {
        this.friendsCircleId = friendsCircleId;
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

    public String getPersonalNoName() {
        return personalNoName;
    }

    public void setPersonalNoName(String personalNoName) {
        this.personalNoName = personalNoName;
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
        return "PersonalNoFriendsCirclePersonal{" +
        "id=" + id +
        ", friendsCircleId=" + friendsCircleId +
        ", personalNoId=" + personalNoId +
        ", personalNoWxId=" + personalNoWxId +
        ", personalNoName=" + personalNoName +
        ", deleted=" + deleted +
        "}";
    }
}
