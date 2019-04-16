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
 * 点击通道链接的记录
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@TableName("personal_no_passage_click_record")
public class PersonalNoPassageClickRecord extends Model<PersonalNoPassageClickRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 推广渠道
     */
    @TableField("passage_id")
    private Integer passageId;
    /**
     * 根据ip地址可以获取到访问地区
     */
    private String ip;
    @TableField("request_info")
    private String requestInfo;
    @TableField("click_time")
    private Date clickTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPassageId() {
        return passageId;
    }

    public void setPassageId(Integer passageId) {
        this.passageId = passageId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }

    public Date getClickTime() {
        return clickTime;
    }

    public void setClickTime(Date clickTime) {
        this.clickTime = clickTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoPassageClickRecord{" +
        "id=" + id +
        ", passageId=" + passageId +
        ", ip=" + ip +
        ", requestInfo=" + requestInfo +
        ", clickTime=" + clickTime +
        "}";
    }
}
