package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
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
@TableName("personal_no_friends_circle_photo")
public class PersonalNoFriendsCirclePhoto extends Model<PersonalNoFriendsCirclePhoto> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 朋友圈配图
     */
    private String photo;
    /**
     * 朋友圈id
     */
    @TableField("friends_circle_id")
    private Integer friendsCircleId;
    /**
     * 逻辑删除
     */
    private Integer deleted;

    @TableField(exist = false)
    private String db;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoFriendsCirclePhoto{" +
        "id=" + id +
        ", photo=" + photo +
        ", friendsCircleId=" + friendsCircleId +
        ", deleted=" + deleted +
        "}";
    }
}
