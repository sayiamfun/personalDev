package com.warm.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liwenjie123
 * @since 2019-05-06
 */
@Data
@TableName("personal_no_message_content")
public class PersonalNoMessageContent extends Model<PersonalNoMessageContent> {

    private static final long serialVersionUID = 1L;

    /**
     * 消息内容表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 关键词id
     */
    @TableField("message_id")
    private Integer messageId;
    /**
     * 内容类型
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

    @TableField(exist = false)
    private String db;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoMessageContent{" +
        "id=" + id +
        ", messageId=" + messageId +
        ", contentType=" + contentType +
        ", content=" + content +
        ", deleted=" + deleted +
        "}";
    }
}
