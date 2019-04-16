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
@TableName("personal_no_task_url")
public class PersonalNoTaskUrl extends Model<PersonalNoTaskUrl> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 推广渠道
     */
    @TableField("personal_no_task_channel")
    private String personalNoTaskChannel;
    /**
     * 推广二维码链接
     */
    @TableField("personal_no_task_channel_code")
    private String personalNoTaskChannelCode;
    /**
     * 推广二维码图片
     */
    @TableField("personal_no_task_channel_photo")
    private String personalNoTaskChannelPhoto;
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

    public String getPersonalNoTaskChannel() {
        return personalNoTaskChannel;
    }

    public void setPersonalNoTaskChannel(String personalNoTaskChannel) {
        this.personalNoTaskChannel = personalNoTaskChannel;
    }

    public String getPersonalNoTaskChannelCode() {
        return personalNoTaskChannelCode;
    }

    public void setPersonalNoTaskChannelCode(String personalNoTaskChannelCode) {
        this.personalNoTaskChannelCode = personalNoTaskChannelCode;
    }

    public String getPersonalNoTaskChannelPhoto() {
        return personalNoTaskChannelPhoto;
    }

    public void setPersonalNoTaskChannelPhoto(String personalNoTaskChannelPhoto) {
        this.personalNoTaskChannelPhoto = personalNoTaskChannelPhoto;
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
        return "PersonalNoTaskUrl{" +
        "id=" + id +
        ", personalNoTaskChannel=" + personalNoTaskChannel +
        ", personalNoTaskChannelCode=" + personalNoTaskChannelCode +
        ", personalNoTaskChannelPhoto=" + personalNoTaskChannelPhoto +
        ", deleted=" + deleted +
        "}";
    }
}
