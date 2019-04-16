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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

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
