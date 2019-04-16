package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Data
@TableName("personal_no_task_message_send")
public class PersonalNoTaskMessageSend extends Model<PersonalNoTaskMessageSend> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 个人号任务id
     */
    @TableField("persona_no_task_id")
    private Integer personaNoTaskId;
    /**
     * 个人号任务消息发送任务名称
     */
    @TableField("personal_no_task_message_name")
    private String personalNoTaskMessageName;
    /**
     * 个人号任务发送消息内容概览
     */
    @TableField("personal_no_task_message_content_show")
    private String personalNoTaskMessageContentShow;
    /**
     * 个人号任务消息发送状态
     */
    @TableField("persona_nol_task_message_status")
    private String personaNolTaskMessageStatus;
    /**
     * 超级用户id
     */
    @TableField("super_id")
    private Integer superId;
    /**
     * 发送时间，null为立即发送，否则为定时发送
     */
    @TableField("send_time")
    private Date sendTime;
    /**
     * 逻辑删除
     */
    private Integer deleted;
    /**
     * 是否是自动回复消息
     */
    @TableField("friend_send")
    private Integer friendSend;


    @ApiModelProperty(value = "个人号发送消息内容列表")
    @TableField(exist = false)
    private List<PersonalNoTaskMessageSendContent> personalNoTaskMessageSendContentList;

    @ApiModelProperty(value = "起始时间")
    @TableField(exist = false)
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @TableField(exist = false)
    private Date endTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoTaskMessageSend{" +
        "id=" + id +
        ", personaNoTaskId=" + personaNoTaskId +
        ", personalNoTaskMessageName=" + personalNoTaskMessageName +
        ", personalNoTaskMessageContentShow=" + personalNoTaskMessageContentShow +
        ", personaNolTaskMessageStatus=" + personaNolTaskMessageStatus +
        ", superId=" + superId +
        ", sendTime=" + sendTime +
        ", deleted=" + deleted +
        ", friendSend=" + friendSend +
        "}";
    }
}
