package com.warm.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author liwenjie123
 * @since 2019-05-06
 */
@Data
@TableName("personal_no_send_message")
public class PersonalNoSendMessage extends Model<PersonalNoSendMessage> {

    private static final long serialVersionUID = 1L;

    /**
     * 个人号发送消息设置
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 个人号微信id
     */
    @TableField("personal_wx_id")
    private String personalWxId;
    /**
     * 个人号昵称
     */
    @TableField("nick_name")
    private String nickName;
    /**
     * 0:立即，1：隔时，2：定时
     */
    private Integer timing;
    /**
     * 格式时间或者是定时时间
     */
    @TableField("timing_time")
    private String timingTime;
    /**
     * 是否根据用户回复情况发送
     * 0:都发送：1：不回复发送，2：回复发送
     */
    @TableField("initiative_flag")
    private Integer initiativeFlag;
    /**
     * 消息id
     */
    @TableField("message_id")
    private Integer messageId;

    @TableField(exist = false)
    private List<Integer> ids;

    @TableField(exist = false)
    private String db;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoSendMessage{" +
        "id=" + id +
        ", personalWxId=" + personalWxId +
        ", nickName=" + nickName +
        ", timing=" + timing +
        ", timingTime=" + timingTime +
        ", initiativeFlag=" + initiativeFlag +
        ", messageId=" + messageId +
        "}";
    }
}
