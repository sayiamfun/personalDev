package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
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
@TableName("personal_no_task_reply_content")
public class PersonalNoTaskReplyContent extends Model<PersonalNoTaskReplyContent> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 任务id
     */
    @TableField("personal_no_task_id")
    private Integer personalNoTaskId;
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
     * 超级用户id
     */
    @TableField("super_id")
    private Integer superId;
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
        return "PersonalNoTaskReplyContent{" +
        "id=" + id +
        ", personalNoTaskId=" + personalNoTaskId +
        ", contentType=" + contentType +
        ", content=" + content +
        ", superId=" + superId +
        ", deleted=" + deleted +
        "}";
    }
}
