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
@TableName("personal_no_lable_message_send")
public class PersonalNoLableMessageSend extends Model<PersonalNoLableMessageSend> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标签消息发送标签集合(标签之间 | 隔开)
     */
    @TableField("personal_no_lable_message_lable_list")
    private String personalNoLableMessageLableList;
    /**
     * 标签消息发送内容概览
     */
    @TableField("personal_no_lable_message_content_show")
    private String personalNoLableMessageContentShow;
    /**
     * 标签消息发送状态
     */
    @TableField("persona_nol_lable_message_status")
    private String personaNolLableMessageStatus;
    /**
     * 超级用户id
     */
    @TableField("super_id")
    private Integer superId;
    /**
     * 发送时间
     */
    @TableField("send_time")
    private Date sendTime;
    /**
     * 将要发送的好友人数
     */
    @TableField("send_num")
    private Integer sendNum;
    /**
     * 逻辑删除
     */
    private Integer deleted;

    @ApiModelProperty(value = "标签发送消息内容列表")
    @TableField(exist = false)
    private List<PersonalNoLableMessageSendContent> personalNoLableMessageSendContentList;

    @ApiModelProperty(value = "标签列表")
    @TableField(exist = false)
    private List<String> lableList;

    @ApiModelProperty(value = "个人号列表")
    @TableField(exist = false)
    private List<PersonalNo> noList;

    @ApiModelProperty(value = "起始时间")
    @TableField(exist = false)
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @TableField(exist = false)
    private Date endTime;

    @TableField(exist = false)
    private String groupName;

    @TableField(exist = false)
    private String db;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoLableMessageSend{" +
        "id=" + id +
        ", personalNoLableMessageLableList=" + personalNoLableMessageLableList +
        ", personalNoLableMessageContentShow=" + personalNoLableMessageContentShow +
        ", personaNolLableMessageStatus=" + personaNolLableMessageStatus +
        ", superId=" + superId +
        ", sendTime=" + sendTime +
        ", sendNum=" + sendNum +
        ", deleted=" + deleted +
        "}";
    }
}
