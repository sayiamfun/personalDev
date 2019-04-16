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
@TableName("personal_no_superuesr")
public class PersonalNoSuperuesr extends Model<PersonalNoSuperuesr> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 管理员账号
     */
    @TableField("super_name")
    private String superName;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    @TableField("head_portrait")
    private String headPortrait;
    /**
     * 专属码
     */
    private String code;
    /**
     * 逻辑删除
     */
    private Integer deleted;
    /**
     * 微信id
     */
    @TableField("wx_id")
    private String wxId;
    /**
     * openid
     */
    private String openid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoSuperuesr{" +
        "id=" + id +
        ", superName=" + superName +
        ", password=" + password +
        ", headPortrait=" + headPortrait +
        ", code=" + code +
        ", deleted=" + deleted +
        ", wxId=" + wxId +
        ", openid=" + openid +
        "}";
    }
}
