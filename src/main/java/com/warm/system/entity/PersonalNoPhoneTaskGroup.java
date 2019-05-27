package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.SafeHtml;

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
@TableName("personal_no_phone_task_group")
public class PersonalNoPhoneTaskGroup extends Model<PersonalNoPhoneTaskGroup> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 任务组主题
     */
    private String tname;
    /**
     * 任务总步数
     */
    @TableField("total_step")
    private Integer totalStep;
    /**
     * 下一步是第几步
     */
    @TableField("next_step")
    private Integer nextStep;
    /**
     * 当前的状态
     */
    private String status;
    /**
     * 要给他发送消息的wxid
     */
    @TableField("current_robot_id")
    private String currentRobotId;
    @TableField("group_pool_id")
    private Integer groupPoolId;
    @TableField("wx_group_id")
    private Integer wxGroupId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 下发时间
     */
    @TableField("pickup_time")
    private Date pickupTime;
    /**
     * 结束时间
     */
    @TableField("finish_time")
    private Date finishTime;
    /**
     * 任务消息id
     */
    @TableField("task_send_id")
    private Integer taskSendId;
    /**
     * 标签消息id
     */
    @TableField("lable_send_id")
    private Integer lableSendId;
    /**
     * 任务优先级
     */
    @TableField("task_order")
    private Integer taskOrder;

    @TableField(exist = false)
    private String db;

    @TableField("message_send_id")
    private Integer messageSendId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoPhoneTaskGroup{" +
        "id=" + id +
        ", tname=" + tname +
        ", totalStep=" + totalStep +
        ", nextStep=" + nextStep +
        ", status=" + status +
        ", currentRobotId=" + currentRobotId +
        ", groupPoolId=" + groupPoolId +
        ", wxGroupId=" + wxGroupId +
        ", createTime=" + createTime +
        ", pickupTime=" + pickupTime +
        ", finishTime=" + finishTime +
        ", taskSendId=" + taskSendId +
        ", lableSendId=" + lableSendId +
        ", taskOrder=" + taskOrder +
        "}";
    }
}
