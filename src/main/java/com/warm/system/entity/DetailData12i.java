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
@TableName("detail_data_12i")
public class DetailData12i extends Model<DetailData12i> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("short_url_id")
    private Integer shortUrlId;
    @TableField("click_time")
    private Date clickTime;
    private String ip;
    private String country;
    private String province;
    private String city;
    @TableField("web_browser")
    private String webBrowser;
    @TableField("access_source")
    private String accessSource;
    @TableField("is_new")
    private Integer isNew;
    private String isp;
    @TableField("device_type")
    private String deviceType;
    @TableField("device_model")
    private String deviceModel;
    private Integer width;
    private Integer height;
    @TableField("user_uuid")
    private String userUuid;
    @TableField("create_time")
    private Date createTime;

    @TableField(exist = false)
    private String test;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "DetailData12i{" +
        "id=" + id +
        ", shortUrlId=" + shortUrlId +
        ", clickTime=" + clickTime +
        ", ip=" + ip +
        ", country=" + country +
        ", province=" + province +
        ", city=" + city +
        ", webBrowser=" + webBrowser +
        ", accessSource=" + accessSource +
        ", isNew=" + isNew +
        ", isp=" + isp +
        ", deviceType=" + deviceType +
        ", deviceModel=" + deviceModel +
        ", width=" + width +
        ", height=" + height +
        ", userUuid=" + userUuid +
        ", createTime=" + createTime +
        "}";
    }
}
