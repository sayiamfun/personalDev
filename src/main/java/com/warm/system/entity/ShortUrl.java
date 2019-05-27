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
@TableName("short_url")
public class ShortUrl extends Model<ShortUrl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 生成的短链接
     */
    @TableField("short_url")
    private String shortUrl;
    /**
     * 原始链接
     */
    @TableField("org_url")
    private String orgUrl;
    /**
     * 渠道id
     */
    @TableField("channel_id")
    private Integer channelId;
    /**
     * 通道id（入群自动回复只能弃用，不能删除编辑。因变量包含话术和海报）
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
    private String ext;
    @TableField("short_url_create_time")
    private Date shortUrlCreateTime;
    /**
     * 短链生成时间戳（主要用来寻找潜在高价值用户，不同用户分享的短链不同）
     */
    @TableField("create_time")
    private Date createTime;

    @TableField("detail_data_last_update_time")
    private Date detailDataLastUpdateTime;
    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private String db;




    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "ShortUrl{" +
        "id=" + id +
        ", shortUrl=" + shortUrl +
        ", orgUrl=" + orgUrl +
        ", channelId=" + channelId +
        ", passageId=" + passageId +
        ", taskId=" + taskId +
        ", ext=" + ext +
        ", shortUrlCreateTime=" + shortUrlCreateTime +
        ", createTime=" + createTime +
        ", detailDataLastUpdateTime=" + detailDataLastUpdateTime +
        ", remark=" + remark +
        "}";
    }
}
