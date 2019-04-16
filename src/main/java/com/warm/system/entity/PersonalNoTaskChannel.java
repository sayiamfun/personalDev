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
@TableName("personal_no_task_channel")
public class PersonalNoTaskChannel extends Model<PersonalNoTaskChannel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 个人号任务id
     */
    @TableField("personal_no_task_id")
    private Integer personalNoTaskId;
    /**
     * 渠道id
     */
    @TableField("channel_id")
    private Integer channelId;
    /**
     * 渠道名称
     */
    @TableField("channel_name")
    private String channelName;
    /**
     * 逻辑删除
     */
    private Integer deleted;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPersonalNoTaskId() {
        return personalNoTaskId;
    }

    public void setPersonalNoTaskId(Integer personalNoTaskId) {
        this.personalNoTaskId = personalNoTaskId;
    }

    public Integer getChannelId() {
        return channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoTaskChannel{" +
        "id=" + id +
        ", personalNoTaskId=" + personalNoTaskId +
        ", channelId=" + channelId +
        ", channelName=" + channelName +
        ", deleted=" + deleted +
        "}";
    }
}
