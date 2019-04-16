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
@TableName("personal_no_prism_record")
public class PersonalNoPrismRecord extends Model<PersonalNoPrismRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("beta_type")
    private Integer betaType;
    @TableField("by_owner")
    private Integer byOwner;
    @TableField("by_qrcode")
    private Integer byQrcode;
    private String chatroom;
    private String content;
    @TableField("create_time")
    private Integer createTime;
    @TableField("ext_content")
    private String extContent;
    @TableField("from_nickname")
    private String fromNickname;
    @TableField("is_reporter_mentioned")
    private Integer isReporterMentioned;
    @TableField("is_robot_mentioned")
    private Integer isRobotMentioned;
    @TableField("is_sent")
    private Integer isSent;
    @TableField("logic_id")
    private Integer logicId;
    private String md5;
    @TableField("package_id")
    private Integer packageId;
    @TableField("to_user_type")
    private Integer toUserType;
    @TableField("to_user_name_list")
    private String toUserNameList;
    private Integer type;
    private Integer unit;
    @TableField("we_chatmsg_type")
    private Integer weChatmsgType;
    private String whatever;
    @TableField("from_username")
    private String fromUsername;
    @TableField("report_logic_id")
    private Integer reportLogicId;
    @TableField("report_internet_ip")
    private String reportInternetIp;
    @TableField("report_time")
    private Date reportTime;
    @TableField("get_logic_id")
    private Integer getLogicId;
    /**
     * 获取该id的时候，请求客户端的ip
     */
    @TableField("get_internet_ip")
    private String getInternetIp;
    /**
     * 获取该ip的时间
     */
    @TableField("get_time")
    private Date getTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBetaType() {
        return betaType;
    }

    public void setBetaType(Integer betaType) {
        this.betaType = betaType;
    }

    public Integer getByOwner() {
        return byOwner;
    }

    public void setByOwner(Integer byOwner) {
        this.byOwner = byOwner;
    }

    public Integer getByQrcode() {
        return byQrcode;
    }

    public void setByQrcode(Integer byQrcode) {
        this.byQrcode = byQrcode;
    }

    public String getChatroom() {
        return chatroom;
    }

    public void setChatroom(String chatroom) {
        this.chatroom = chatroom;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getExtContent() {
        return extContent;
    }

    public void setExtContent(String extContent) {
        this.extContent = extContent;
    }

    public String getFromNickname() {
        return fromNickname;
    }

    public void setFromNickname(String fromNickname) {
        this.fromNickname = fromNickname;
    }

    public Integer getIsReporterMentioned() {
        return isReporterMentioned;
    }

    public void setIsReporterMentioned(Integer isReporterMentioned) {
        this.isReporterMentioned = isReporterMentioned;
    }

    public Integer getIsRobotMentioned() {
        return isRobotMentioned;
    }

    public void setIsRobotMentioned(Integer isRobotMentioned) {
        this.isRobotMentioned = isRobotMentioned;
    }

    public Integer getIsSent() {
        return isSent;
    }

    public void setIsSent(Integer isSent) {
        this.isSent = isSent;
    }

    public Integer getLogicId() {
        return logicId;
    }

    public void setLogicId(Integer logicId) {
        this.logicId = logicId;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getToUserType() {
        return toUserType;
    }

    public void setToUserType(Integer toUserType) {
        this.toUserType = toUserType;
    }

    public String getToUserNameList() {
        return toUserNameList;
    }

    public void setToUserNameList(String toUserNameList) {
        this.toUserNameList = toUserNameList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getWeChatmsgType() {
        return weChatmsgType;
    }

    public void setWeChatmsgType(Integer weChatmsgType) {
        this.weChatmsgType = weChatmsgType;
    }

    public String getWhatever() {
        return whatever;
    }

    public void setWhatever(String whatever) {
        this.whatever = whatever;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public Integer getReportLogicId() {
        return reportLogicId;
    }

    public void setReportLogicId(Integer reportLogicId) {
        this.reportLogicId = reportLogicId;
    }

    public String getReportInternetIp() {
        return reportInternetIp;
    }

    public void setReportInternetIp(String reportInternetIp) {
        this.reportInternetIp = reportInternetIp;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Integer getGetLogicId() {
        return getLogicId;
    }

    public void setGetLogicId(Integer getLogicId) {
        this.getLogicId = getLogicId;
    }

    public String getGetInternetIp() {
        return getInternetIp;
    }

    public void setGetInternetIp(String getInternetIp) {
        this.getInternetIp = getInternetIp;
    }

    public Date getGetTime() {
        return getTime;
    }

    public void setGetTime(Date getTime) {
        this.getTime = getTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoPrismRecord{" +
        "id=" + id +
        ", betaType=" + betaType +
        ", byOwner=" + byOwner +
        ", byQrcode=" + byQrcode +
        ", chatroom=" + chatroom +
        ", content=" + content +
        ", createTime=" + createTime +
        ", extContent=" + extContent +
        ", fromNickname=" + fromNickname +
        ", isReporterMentioned=" + isReporterMentioned +
        ", isRobotMentioned=" + isRobotMentioned +
        ", isSent=" + isSent +
        ", logicId=" + logicId +
        ", md5=" + md5 +
        ", packageId=" + packageId +
        ", toUserType=" + toUserType +
        ", toUserNameList=" + toUserNameList +
        ", type=" + type +
        ", unit=" + unit +
        ", weChatmsgType=" + weChatmsgType +
        ", whatever=" + whatever +
        ", fromUsername=" + fromUsername +
        ", reportLogicId=" + reportLogicId +
        ", reportInternetIp=" + reportInternetIp +
        ", reportTime=" + reportTime +
        ", getLogicId=" + getLogicId +
        ", getInternetIp=" + getInternetIp +
        ", getTime=" + getTime +
        "}";
    }
}
