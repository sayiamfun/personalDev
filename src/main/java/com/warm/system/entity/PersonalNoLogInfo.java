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
 * @since 2019-04-25
 */
@Data
@TableName("personal_no_log_info")
public class PersonalNoLogInfo extends Model<PersonalNoLogInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @TableField("super_user_id")
    private Integer superUserId;
    /**
     * 用户名称
     */
    @TableField("super_user_name")
    private String superUserName;
    /**
     * 请求地址
     */
    @TableField("request_url")
    private String requestUrl;
    /**
     * 时间
     */
    private Date date;

    @TableField("request_ip")
    private String requestIp;

    @TableField(exist = false)
    private String db;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoLogInfo{" +
        "id=" + id +
        ", superUserId=" + superUserId +
        ", superUserName=" + superUserName +
        ", requestUrl=" + requestUrl +
        ", date=" + date +
        "}";
    }
}
