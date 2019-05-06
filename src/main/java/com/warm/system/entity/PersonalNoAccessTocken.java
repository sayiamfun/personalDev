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
 * 微信登录验证
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Data
@TableName("personal_no_access_tocken")
public class PersonalNoAccessTocken extends Model<PersonalNoAccessTocken> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;
    /**
     * 是否拿到验证参数
     */
    private Integer flag;
    /**
     * tocken值
     */
    @TableField("access_token")
    private String accessToken;
    /**
     * openid
     */
    private String openid;
    /**
     * 逻辑删除状态
     */
    private Integer deleted;
    /**
     * refreshtoken
     */
    private String refreshtoken;

    @TableField(exist = false)
    private String db;



    @Override
    protected Serializable pkVal() {
        return this.Id;
    }

    @Override
    public String toString() {
        return "PersonalNoAccessTocken{" +
        "Id=" + Id +
        ", flag=" + flag +
        ", accessToken=" + accessToken +
        ", openid=" + openid +
        ", deleted=" + deleted +
        ", refreshtoken=" + refreshtoken +
        "}";
    }
}
