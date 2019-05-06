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
@TableName("wx_group")
public class PersonalNoWxGroup extends Model<PersonalNoWxGroup> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("group_category_id")
    private Integer groupCategoryId;
    @TableField("wx_group_office_id")
    private String wxGroupOfficeId;
    private String gname;
    private String notice;
    @TableField("notice_publish_time")
    private Date noticePublishTime;
    @TableField("group_owner")
    private String groupOwner;
    @TableField("robot_a1")
    private String robotA1;
    @TableField("robot_a2")
    private String robotA2;
    @TableField("group_holder_c1")
    private String groupHolderC1;
    @TableField("group_manage_c2")
    private String groupManageC2;
    @TableField("group_holder_b1")
    private String groupHolderB1;
    @TableField("group_manage_b2")
    private String groupManageB2;
    /**
     * 跟updateWeChatroom事件绑定
     */
    @TableField("group_member_last_update_time")
    private Date groupMemberLastUpdateTime;
    private String tag;
    @TableField("qrcode_url")
    private String qrcodeUrl;
    @TableField("qrcode_create_time")
    private Date qrcodeCreateTime;
    @TableField("qrcode_uploder_wxid")
    private String qrcodeUploderWxid;
    @TableField("create_time")
    private Date createTime;
    /**
     * 跟writePrismRecord事件绑定
     */
    @TableField("last_active_time")
    private Date lastActiveTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupCategoryId() {
        return groupCategoryId;
    }

    public void setGroupCategoryId(Integer groupCategoryId) {
        this.groupCategoryId = groupCategoryId;
    }

    public String getWxGroupOfficeId() {
        return wxGroupOfficeId;
    }

    public void setWxGroupOfficeId(String wxGroupOfficeId) {
        this.wxGroupOfficeId = wxGroupOfficeId;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public Date getNoticePublishTime() {
        return noticePublishTime;
    }

    public void setNoticePublishTime(Date noticePublishTime) {
        this.noticePublishTime = noticePublishTime;
    }

    public String getGroupOwner() {
        return groupOwner;
    }

    public void setGroupOwner(String groupOwner) {
        this.groupOwner = groupOwner;
    }

    public String getRobotA1() {
        return robotA1;
    }

    public void setRobotA1(String robotA1) {
        this.robotA1 = robotA1;
    }

    public String getRobotA2() {
        return robotA2;
    }

    public void setRobotA2(String robotA2) {
        this.robotA2 = robotA2;
    }

    public String getGroupHolderC1() {
        return groupHolderC1;
    }

    public void setGroupHolderC1(String groupHolderC1) {
        this.groupHolderC1 = groupHolderC1;
    }

    public String getGroupManageC2() {
        return groupManageC2;
    }

    public void setGroupManageC2(String groupManageC2) {
        this.groupManageC2 = groupManageC2;
    }

    public String getGroupHolderB1() {
        return groupHolderB1;
    }

    public void setGroupHolderB1(String groupHolderB1) {
        this.groupHolderB1 = groupHolderB1;
    }

    public String getGroupManageB2() {
        return groupManageB2;
    }

    public void setGroupManageB2(String groupManageB2) {
        this.groupManageB2 = groupManageB2;
    }

    public Date getGroupMemberLastUpdateTime() {
        return groupMemberLastUpdateTime;
    }

    public void setGroupMemberLastUpdateTime(Date groupMemberLastUpdateTime) {
        this.groupMemberLastUpdateTime = groupMemberLastUpdateTime;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public Date getQrcodeCreateTime() {
        return qrcodeCreateTime;
    }

    public void setQrcodeCreateTime(Date qrcodeCreateTime) {
        this.qrcodeCreateTime = qrcodeCreateTime;
    }

    public String getQrcodeUploderWxid() {
        return qrcodeUploderWxid;
    }

    public void setQrcodeUploderWxid(String qrcodeUploderWxid) {
        this.qrcodeUploderWxid = qrcodeUploderWxid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoWxGroup{" +
        "id=" + id +
        ", groupCategoryId=" + groupCategoryId +
        ", wxGroupOfficeId=" + wxGroupOfficeId +
        ", gname=" + gname +
        ", notice=" + notice +
        ", noticePublishTime=" + noticePublishTime +
        ", groupOwner=" + groupOwner +
        ", robotA1=" + robotA1 +
        ", robotA2=" + robotA2 +
        ", groupHolderC1=" + groupHolderC1 +
        ", groupManageC2=" + groupManageC2 +
        ", groupHolderB1=" + groupHolderB1 +
        ", groupManageB2=" + groupManageB2 +
        ", groupMemberLastUpdateTime=" + groupMemberLastUpdateTime +
        ", tag=" + tag +
        ", qrcodeUrl=" + qrcodeUrl +
        ", qrcodeCreateTime=" + qrcodeCreateTime +
        ", qrcodeUploderWxid=" + qrcodeUploderWxid +
        ", createTime=" + createTime +
        ", lastActiveTime=" + lastActiveTime +
        "}";
    }
}
