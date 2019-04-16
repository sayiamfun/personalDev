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
@TableName("personal_no_task_message_send_content")
public class PersonalNoTaskMessageSendContent extends Model<PersonalNoTaskMessageSendContent> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 个人号任务消息id
     */
    @TableField("personal_no_task_message_send_id")
    private Integer personalNoTaskMessageSendId;
    /**
     * 内容类型(0:文字,1:名片,2:图片,3:链接,4:邀请入群,5:小程序)
     */
    @TableField("content_type")
    private String contentType;
    /**
     * 内容
     */
    private String content;
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

    public Integer getPersonalNoTaskMessageSendId() {
        return personalNoTaskMessageSendId;
    }

    public void setPersonalNoTaskMessageSendId(Integer personalNoTaskMessageSendId) {
        this.personalNoTaskMessageSendId = personalNoTaskMessageSendId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        return "PersonalNoTaskMessageSendContent{" +
        "id=" + id +
        ", personalNoTaskMessageSendId=" + personalNoTaskMessageSendId +
        ", contentType=" + contentType +
        ", content=" + content +
        ", deleted=" + deleted +
        "}";
    }
}
