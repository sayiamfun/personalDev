package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import com.warm.entity.requre.RecommendedReasons;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
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
@TableName("personal_no_task")
public class PersonalNoTask extends Model<PersonalNoTask> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 任务名称
     */
    @NotBlank(message = "任务名称不能为空")
    @TableField("task_name")
    private String taskName;
    /**
     * 任务主题
     */
    @NotBlank(message = "任务主题不能为空")
    @TableField("task_theme")
    private String taskTheme;
    /**
     * 推广类型
     */
    @TableField("popularize_category")
    private String popularizeCategory;
    /**
     * 课程名称
     */
    @TableField("subject_name")
    private String subjectName;
    /**
     * 推荐理由
     */
    @TableField("recommended_reasons")
    private String recommendedReasons;
    /**
     * 快速入群
     */
    @TableField("fast_group")
    private String fastGroup;
    /**
     * 快速入群分群标志(第几个群满时开始合群)
     */
    @TableField("sales_group_separate_flag")
    private String salesGroupSeparateFlag;
    /**
     * 建立销售任务(0:不建立,1:建立)
     */
    @TableField("create_sales_task")
    private String createSalesTask;
    /**
     * 任务链接
     */
    @TableField("task_url")
    private String taskUrl;
    /**
     * 任务截止时间
     */
    @NotNull(message = "任务结束时间不能为空")
    @TableField("task_end_time")
    private Date taskEndTime;
    /**
     * 是否自动关闭(0:不关闭,大于0达到指定群个数后关闭)
     */
    @TableField("group_auto_close")
    private String groupAutoClose;
    /**
     * 拉粉任务人id,(0为未开启)
     */
    @TableField("pull_people_user_id")
    private Integer pullPeopleUserId;
    /**
     * 开课提醒时间(0为未开启)
     */
    @TableField("auto_remind")
    private String autoRemind;
    /**
     * 0:进行中;1:已结束;
     */
    @TableField("activity_type")
    private Integer activityType;
    /**
     * 任务结束后指向二维码
     */
    @TableField("task_end_url")
    private String taskEndUrl;
    /**
     * 超级用户id
     */
    @NotNull(message = "登录已过期，请退出重新登录")
    @TableField("super_id")
    private Integer superId;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 逻辑删除
     */
    private Integer deleted;
    /**
     * 开课时间
     */
    @NotNull(message = "任务开课时间不能为空")
    @TableField("task_start_time")
    private Date taskStartTime;
    /**
     * 该添加好友的个人号下标
     */
    @TableField("add_friend_index")
    private Integer addFriendIndex;
    /**
     * 任务通道id
     */
    @NotNull(message = "任务通道不能为空")
    @TableField("road_id")
    private Integer roadId;
    /**
     * 审核回复消息
     */
    private String message;


    @ApiModelProperty(value = "回复内容列表")
    @TableField(exist = false)
    private List<PersonalNoTaskReplyContent> noTaskReplyContentList;

    @ApiModelProperty(value = "开课提醒内容列表")
    @TableField(exist = false)
    private List<PersonalNoTaskBeginRemind> noTaskBeginRemindList;

    @ApiModelProperty(value = "个人号列表")
    @TableField(exist = false)
    private List<PersonalNoTaskPersonal> noList;

    @ApiModelProperty(value = "渠道列表")
    @TableField(exist = false)
    private List<PersonalNoTaskChannel> personalnoChannelList;

    @ApiModelProperty(value = "渠道名称列表")
    @TableField(exist = false)
    private List<String> channelNameList;

    @ApiModelProperty(value = "所有渠道名称列表")
    @TableField(exist = false)
    private List<String> allChannelNameList;

    @ApiModelProperty(value = "粉丝标签列表字符串")
    @TableField(exist = false)
    private String noLable;

    @ApiModelProperty(value = "粉丝标签列表")
    @TableField(exist = false)
    private List<PersonalNoTaskLable> noLableList;

    @ApiModelProperty(value = "推荐理由集合")
    @TableField(exist = false)
    private List<RecommendedReasons> recommendedReasonsList;

    @ApiModelProperty(value = "个人号数量")
    @TableField(exist = false)
    private Integer personalNum;

    @ApiModelProperty(value = "渠道数量")
    @TableField(exist = false)
    private Integer channelNum;

    @ApiModelProperty(value = "自动回复群类别名")
    @TableField(exist = false)
    private String categoryName1;

    @ApiModelProperty(value = "开课提醒群类别名")
    @TableField(exist = false)
    private String categoryName2;

    @TableField(exist = false)
    private String db;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoTask{" +
        "id=" + id +
        ", taskName=" + taskName +
        ", taskTheme=" + taskTheme +
        ", popularizeCategory=" + popularizeCategory +
        ", subjectName=" + subjectName +
        ", recommendedReasons=" + recommendedReasons +
        ", fastGroup=" + fastGroup +
        ", salesGroupSeparateFlag=" + salesGroupSeparateFlag +
        ", createSalesTask=" + createSalesTask +
        ", taskUrl=" + taskUrl +
        ", taskEndTime=" + taskEndTime +
        ", groupAutoClose=" + groupAutoClose +
        ", pullPeopleUserId=" + pullPeopleUserId +
        ", autoRemind=" + autoRemind +
        ", activityType=" + activityType +
        ", taskEndUrl=" + taskEndUrl +
        ", superId=" + superId +
        ", remarks=" + remarks +
        ", createTime=" + createTime +
        ", deleted=" + deleted +
        ", taskStartTime=" + taskStartTime +
        ", addFriendIndex=" + addFriendIndex +
        ", roadId=" + roadId +
        ", message=" + message +
        "}";
    }
}
