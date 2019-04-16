package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
@TableName("personal_no_phone_task")
public class PersonalNoPhoneTask extends Model<PersonalNoPhoneTask> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 任务组id
     */
    @TableField("task_group_id")
    private Integer taskGroupId;
    /**
     * 任务信息主图
     */
    private String tname;
    /**
     * 当前步数
     */
    private Integer step;
    /**
     * 好友wxid
     */
    @TableField("robot_id")
    private String robotId;
    /**
     * 状态
     */
    private String status;
    private String tdescription;
    @TableField("task_json")
    private String taskJson;
    @TableField("group_pool_id")
    private Integer groupPoolId;
    @TableField("wx_group_id")
    private Integer wxGroupId;
    @TableField("task_finished_tag")
    private String taskFinishedTag;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 下发任务时间
     */
    @TableField("pickup_time")
    private Date pickupTime;
    @TableField("is_client_feedback_received")
    private Integer isClientFeedbackReceived;
    @TableField("feedback_received_time")
    private Date feedbackReceivedTime;
    @TableField("is_client_feedback_finished")
    private Integer isClientFeedbackFinished;
    @TableField("feedback_finished_time")
    private Date feedbackFinishedTime;
    @TableField("failed_reason")
    private String failedReason;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 消息内容类型
     */
    @TableField("content_type")
    private String contentType;
    /**
     * 任务类型
     */
    @TableField("task_type")
    private Integer taskType;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTaskGroupId() {
        return taskGroupId;
    }

    public void setTaskGroupId(Integer taskGroupId) {
        this.taskGroupId = taskGroupId;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getRobotId() {
        return robotId;
    }

    public void setRobotId(String robotId) {
        this.robotId = robotId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTdescription() {
        return tdescription;
    }

    public void setTdescription(String tdescription) {
        this.tdescription = tdescription;
    }

    public String getTaskJson() {
        return taskJson;
    }

    public void setTaskJson(String taskJson) {
        this.taskJson = taskJson;
    }

    public Integer getGroupPoolId() {
        return groupPoolId;
    }

    public void setGroupPoolId(Integer groupPoolId) {
        this.groupPoolId = groupPoolId;
    }

    public Integer getWxGroupId() {
        return wxGroupId;
    }

    public void setWxGroupId(Integer wxGroupId) {
        this.wxGroupId = wxGroupId;
    }

    public String getTaskFinishedTag() {
        return taskFinishedTag;
    }

    public void setTaskFinishedTag(String taskFinishedTag) {
        this.taskFinishedTag = taskFinishedTag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(Date pickupTime) {
        this.pickupTime = pickupTime;
    }

    public Integer getIsClientFeedbackReceived() {
        return isClientFeedbackReceived;
    }

    public void setIsClientFeedbackReceived(Integer isClientFeedbackReceived) {
        this.isClientFeedbackReceived = isClientFeedbackReceived;
    }

    public Date getFeedbackReceivedTime() {
        return feedbackReceivedTime;
    }

    public void setFeedbackReceivedTime(Date feedbackReceivedTime) {
        this.feedbackReceivedTime = feedbackReceivedTime;
    }

    public Integer getIsClientFeedbackFinished() {
        return isClientFeedbackFinished;
    }

    public void setIsClientFeedbackFinished(Integer isClientFeedbackFinished) {
        this.isClientFeedbackFinished = isClientFeedbackFinished;
    }

    public Date getFeedbackFinishedTime() {
        return feedbackFinishedTime;
    }

    public void setFeedbackFinishedTime(Date feedbackFinishedTime) {
        this.feedbackFinishedTime = feedbackFinishedTime;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoPhoneTask{" +
        "id=" + id +
        ", taskGroupId=" + taskGroupId +
        ", tname=" + tname +
        ", step=" + step +
        ", robotId=" + robotId +
        ", status=" + status +
        ", tdescription=" + tdescription +
        ", taskJson=" + taskJson +
        ", groupPoolId=" + groupPoolId +
        ", wxGroupId=" + wxGroupId +
        ", taskFinishedTag=" + taskFinishedTag +
        ", createTime=" + createTime +
        ", pickupTime=" + pickupTime +
        ", isClientFeedbackReceived=" + isClientFeedbackReceived +
        ", feedbackReceivedTime=" + feedbackReceivedTime +
        ", isClientFeedbackFinished=" + isClientFeedbackFinished +
        ", feedbackFinishedTime=" + feedbackFinishedTime +
        ", failedReason=" + failedReason +
        ", content=" + content +
        ", contentType=" + contentType +
        ", taskType=" + taskType +
        "}";
    }
}
