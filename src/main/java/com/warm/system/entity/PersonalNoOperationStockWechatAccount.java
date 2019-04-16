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
@TableName("personal_no_operation_stock_wechat_account")
public class PersonalNoOperationStockWechatAccount extends Model<PersonalNoOperationStockWechatAccount> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 资产编号
     */
    @TableField("asset_id")
    private String assetId;
    /**
     * 微信号采购表ID
     */
    @TableField("wx_buy_id")
    private Integer wxBuyId;
    /**
     * 微信ID
     */
    @TableField("wx_id")
    private String wxId;
    /**
     * 微信ID别名
     */
    @TableField("wx_id_bie_ming")
    private String wxIdBieMing;
    /**
     * 绑定手机号
     */
    @TableField("assign_phone")
    private String assignPhone;
    private String email;
    /**
     * 实名认证身份证号
     */
    @TableField("id_card")
    private String idCard;
    /**
     * 绑定银行卡
     */
    @TableField("yin_hang_ka")
    private String yinHangKa;
    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;
    private Integer sex;
    private String province;
    private String country;
    private String city;
    /**
     * 地区
     */
    private String area;
    @TableField("whats_up")
    private String whatsUp;
    /**
     * 二维码
     */
    @TableField("qr_code")
    private String qrCode;
    /**
     * 头像
     */
    private String avatar;
    @TableField("is_have_realname")
    private Integer isHaveRealname;
    @TableField("is_slient_download")
    private Integer isSlientDownload;
    @TableField("operation_project_instance_id")
    private Integer operationProjectInstanceId;
    /**
     * 类别
     */
    @TableField("wx_class")
    private String wxClass;
    /**
     * 手机库存表
     */
    @TableField("phone_stock_id")
    private Integer phoneStockId;
    /**
     * 当前状态
     */
    private String status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 密码
     */
    private String password;
    /**
     * 上号时间
     */
    @TableField("wx_login_time")
    private Date wxLoginTime;
    @TableField("project_instance_reg_time")
    private Date projectInstanceRegTime;
    /**
     * 最后pickTask的时间
     */
    @TableField("last_request_job_time")
    private Date lastRequestJobTime;
    @TableField("last_finished_job_time")
    private Date lastFinishedJobTime;
    /**
     * 最后链接服务器的时间
     */
    @TableField("last_connect_time")
    private Date lastConnectTime;
    @TableField("last_update_local_ip")
    private String lastUpdateLocalIp;
    @TableField("last_connect_internet_ip")
    private String lastConnectInternetIp;
    @TableField("click_id")
    private String clickId;
    @TableField("current_client_wehook_version")
    private String currentClientWehookVersion;
    @TableField("current_client_wechat_version")
    private String currentClientWechatVersion;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public Integer getWxBuyId() {
        return wxBuyId;
    }

    public void setWxBuyId(Integer wxBuyId) {
        this.wxBuyId = wxBuyId;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getWxIdBieMing() {
        return wxIdBieMing;
    }

    public void setWxIdBieMing(String wxIdBieMing) {
        this.wxIdBieMing = wxIdBieMing;
    }

    public String getAssignPhone() {
        return assignPhone;
    }

    public void setAssignPhone(String assignPhone) {
        this.assignPhone = assignPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getYinHangKa() {
        return yinHangKa;
    }

    public void setYinHangKa(String yinHangKa) {
        this.yinHangKa = yinHangKa;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getWhatsUp() {
        return whatsUp;
    }

    public void setWhatsUp(String whatsUp) {
        this.whatsUp = whatsUp;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getIsHaveRealname() {
        return isHaveRealname;
    }

    public void setIsHaveRealname(Integer isHaveRealname) {
        this.isHaveRealname = isHaveRealname;
    }

    public Integer getIsSlientDownload() {
        return isSlientDownload;
    }

    public void setIsSlientDownload(Integer isSlientDownload) {
        this.isSlientDownload = isSlientDownload;
    }

    public Integer getOperationProjectInstanceId() {
        return operationProjectInstanceId;
    }

    public void setOperationProjectInstanceId(Integer operationProjectInstanceId) {
        this.operationProjectInstanceId = operationProjectInstanceId;
    }

    public String getWxClass() {
        return wxClass;
    }

    public void setWxClass(String wxClass) {
        this.wxClass = wxClass;
    }

    public Integer getPhoneStockId() {
        return phoneStockId;
    }

    public void setPhoneStockId(Integer phoneStockId) {
        this.phoneStockId = phoneStockId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getWxLoginTime() {
        return wxLoginTime;
    }

    public void setWxLoginTime(Date wxLoginTime) {
        this.wxLoginTime = wxLoginTime;
    }

    public Date getProjectInstanceRegTime() {
        return projectInstanceRegTime;
    }

    public void setProjectInstanceRegTime(Date projectInstanceRegTime) {
        this.projectInstanceRegTime = projectInstanceRegTime;
    }

    public Date getLastRequestJobTime() {
        return lastRequestJobTime;
    }

    public void setLastRequestJobTime(Date lastRequestJobTime) {
        this.lastRequestJobTime = lastRequestJobTime;
    }

    public Date getLastFinishedJobTime() {
        return lastFinishedJobTime;
    }

    public void setLastFinishedJobTime(Date lastFinishedJobTime) {
        this.lastFinishedJobTime = lastFinishedJobTime;
    }

    public Date getLastConnectTime() {
        return lastConnectTime;
    }

    public void setLastConnectTime(Date lastConnectTime) {
        this.lastConnectTime = lastConnectTime;
    }

    public String getLastUpdateLocalIp() {
        return lastUpdateLocalIp;
    }

    public void setLastUpdateLocalIp(String lastUpdateLocalIp) {
        this.lastUpdateLocalIp = lastUpdateLocalIp;
    }

    public String getLastConnectInternetIp() {
        return lastConnectInternetIp;
    }

    public void setLastConnectInternetIp(String lastConnectInternetIp) {
        this.lastConnectInternetIp = lastConnectInternetIp;
    }

    public String getClickId() {
        return clickId;
    }

    public void setClickId(String clickId) {
        this.clickId = clickId;
    }

    public String getCurrentClientWehookVersion() {
        return currentClientWehookVersion;
    }

    public void setCurrentClientWehookVersion(String currentClientWehookVersion) {
        this.currentClientWehookVersion = currentClientWehookVersion;
    }

    public String getCurrentClientWechatVersion() {
        return currentClientWechatVersion;
    }

    public void setCurrentClientWechatVersion(String currentClientWechatVersion) {
        this.currentClientWechatVersion = currentClientWechatVersion;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoOperationStockWechatAccount{" +
        "id=" + id +
        ", assetId=" + assetId +
        ", wxBuyId=" + wxBuyId +
        ", wxId=" + wxId +
        ", wxIdBieMing=" + wxIdBieMing +
        ", assignPhone=" + assignPhone +
        ", email=" + email +
        ", idCard=" + idCard +
        ", yinHangKa=" + yinHangKa +
        ", nickName=" + nickName +
        ", sex=" + sex +
        ", province=" + province +
        ", country=" + country +
        ", city=" + city +
        ", area=" + area +
        ", whatsUp=" + whatsUp +
        ", qrCode=" + qrCode +
        ", avatar=" + avatar +
        ", isHaveRealname=" + isHaveRealname +
        ", isSlientDownload=" + isSlientDownload +
        ", operationProjectInstanceId=" + operationProjectInstanceId +
        ", wxClass=" + wxClass +
        ", phoneStockId=" + phoneStockId +
        ", status=" + status +
        ", remark=" + remark +
        ", password=" + password +
        ", wxLoginTime=" + wxLoginTime +
        ", projectInstanceRegTime=" + projectInstanceRegTime +
        ", lastRequestJobTime=" + lastRequestJobTime +
        ", lastFinishedJobTime=" + lastFinishedJobTime +
        ", lastConnectTime=" + lastConnectTime +
        ", lastUpdateLocalIp=" + lastUpdateLocalIp +
        ", lastConnectInternetIp=" + lastConnectInternetIp +
        ", clickId=" + clickId +
        ", currentClientWehookVersion=" + currentClientWehookVersion +
        ", currentClientWechatVersion=" + currentClientWechatVersion +
        "}";
    }
}
