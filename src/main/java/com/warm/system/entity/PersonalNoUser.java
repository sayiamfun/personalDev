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
@TableName("personal_no_user")
public class PersonalNoUser extends Model<PersonalNoUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 微信id
     */
    @TableField("wx_id")
    private String wxId;
    /**
     * 微信别名
     */
    @TableField("wx_name")
    private String wxName;
    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;
    /**
     * 头像
     */
    @TableField("head_portrait")
    private String headPortrait;
    /**
     * 地区
     */
    private String address;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 性别
     */
    private String gender;
    /**
     * 逻辑删除
     */
    private Integer deleted;
    /**
     * 添加时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * openid
     */
    private String openid;
    /**
     * unionid
     */
    private String unionid;

    @TableField(exist = false)
    private String db;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoUser{" +
        "id=" + id +
        ", wxId=" + wxId +
        ", wxName=" + wxName +
        ", nickName=" + nickName +
        ", headPortrait=" + headPortrait +
        ", address=" + address +
        ", phone=" + phone +
        ", gender=" + gender +
        ", deleted=" + deleted +
        ", createTime=" + createTime +
        ", openid=" + openid +
        ", unionid=" + unionid +
        "}";
    }
}
