package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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

    @TableField(exist = false)
    private Integer FriendsNum;

    @TableField(exist = false)
    private Integer WaitingNums;

    @TableField(exist = false)
    private String category;

    @TableField(exist = false)
    private String group;

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
