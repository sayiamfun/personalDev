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
 * @since 2019-05-09
 */
@Data
@TableName("passage_visitor_record")
public class PassageVisitorRecord extends Model<PassageVisitorRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 渠道id
     */
    @TableField("channel_id")
    private Integer channelId;
    /**
     * 通道id
     */
    @TableField("passage_id")
    private Integer passageId;
    /**
     * 任务id
     */
    @TableField("task_id")
    private Integer taskId;
    /**
     * 扩展的url参数
     */
    @TableField("short_url_ext")
    private String shortUrlExt;
    /**
     * url时间戳
     */
    @TableField("short_url_create_time")
    private Date shortUrlCreateTime;
    /**
     * 用户微信id
     */
    @TableField("user_wx_id")
    private String userWxId;
    /**
     * openid
     */
    @TableField("open_id")
    private String openId;
    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;
    /**
     * 性别
     */
    private String sex;
    /**
     * 国家
     */
    private String country;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 头像
     */
    @TableField("head_image")
    private String headImage;
    /**
     * 不知道
     */
    private String privilege;
    /**
     * unionId
     */
    @TableField("union_id")
    private String unionId;
    /**
     * 扫码时间
     */
    @TableField("scan_qrcode_time")
    private Date scanQrcodeTime;
    /**
     * ip地址
     */
    @TableField("current_ip")
    private String currentIp;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    @TableField(exist = false)
    private String db;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PassageVisitorRecord{" +
        "id=" + id +
        ", channelId=" + channelId +
        ", passageId=" + passageId +
        ", taskId=" + taskId +
        ", shortUrlExt=" + shortUrlExt +
        ", shortUrlCreateTime=" + shortUrlCreateTime +
        ", userWxId=" + userWxId +
        ", openId=" + openId +
        ", nickName=" + nickName +
        ", sex=" + sex +
        ", country=" + country +
        ", province=" + province +
        ", city=" + city +
        ", headImage=" + headImage +
        ", privilege=" + privilege +
        ", unionId=" + unionId +
        ", scanQrcodeTime=" + scanQrcodeTime +
        ", currentIp=" + currentIp +
        ", createTime=" + createTime +
        "}";
    }
}
