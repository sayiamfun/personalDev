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
@TableName("personal_no_request_exception")
public class PersonalNoRequestException extends Model<PersonalNoRequestException> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String method;
    private String url;
    @TableField("request_body")
    private String requestBody;
    @TableField("response_body")
    private String responseBody;
    @TableField("status_code")
    private Integer statusCode;
    @TableField("create_time")
    private Date createTime;
    @TableField("remarks")
    private String remarks;
    @TableField("exception")
    private String exception;
    @TableField(exist = false)
    private String db;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoRequestException{" +
        "id=" + id +
        ", method=" + method +
        ", url=" + url +
        ", requestBody=" + requestBody +
        ", responseBody=" + responseBody +
        ", statusCode=" + statusCode +
        ", createTime=" + createTime +
        "}";
    }
}
