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
 * 存储用户的交互数据
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@TableName("personal_no_temp")
public class PersonalNoTemp extends Model<PersonalNoTemp> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;
    /**
     * 个人号微信id
     */
    @TableField("personal_no_wx_id")
    private String personalNoWxId;
    /**
     * 用户微信id
     */
    @TableField("user_wx_id")
    private String userWxId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 回复状态
     */
    private Integer flag;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getPersonalNoWxId() {
        return personalNoWxId;
    }

    public void setPersonalNoWxId(String personalNoWxId) {
        this.personalNoWxId = personalNoWxId;
    }

    public String getUserWxId() {
        return userWxId;
    }

    public void setUserWxId(String userWxId) {
        this.userWxId = userWxId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    @Override
    protected Serializable pkVal() {
        return this.Id;
    }

    @Override
    public String toString() {
        return "PersonalNoTemp{" +
        "Id=" + Id +
        ", personalNoWxId=" + personalNoWxId +
        ", userWxId=" + userWxId +
        ", createTime=" + createTime +
        ", flag=" + flag +
        "}";
    }
}
