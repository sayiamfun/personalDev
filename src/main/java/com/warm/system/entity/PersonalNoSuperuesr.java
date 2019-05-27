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

    @TableField(exist = false)
    private String db;

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
