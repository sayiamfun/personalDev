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
 * 微信登录验证
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
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


    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getRefreshtoken() {
        return refreshtoken;
    }

    public void setRefreshtoken(String refreshtoken) {
        this.refreshtoken = refreshtoken;
    }

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
