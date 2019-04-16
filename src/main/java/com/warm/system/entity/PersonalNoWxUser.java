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
@TableName("personal_no_wx_user")
public class PersonalNoWxUser extends Model<PersonalNoWxUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("wx_id")
    private String wxId;
    @TableField("open_id")
    private String openId;
    /**
     * 未知
     */
    private String unionid;
    private String alias;
    @TableField("is_assistant")
    private Integer isAssistant;
    @TableField("nick_name")
    private String nickName;
    @TableField("real_name")
    private String realName;
    @TableField("softbank_name")
    private String softbankName;
    @TableField("head_photo")
    private String headPhoto;
    @TableField("wx_qrcode")
    private String wxQrcode;
    private Integer sex;
    private String province;
    private String country;
    private String city;
    private String email;
    @TableField("whats_up")
    private String whatsUp;
    @TableField("con_remark")
    private String conRemark;
    /**
     * 未知
     */
    private String privilege;
    @TableField("create_time")
    private Date createTime;
    @TableField("is_black")
    private Integer isBlack;
    @TableField("black_reason")
    private String blackReason;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getIsAssistant() {
        return isAssistant;
    }

    public void setIsAssistant(Integer isAssistant) {
        this.isAssistant = isAssistant;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSoftbankName() {
        return softbankName;
    }

    public void setSoftbankName(String softbankName) {
        this.softbankName = softbankName;
    }

    public String getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

    public String getWxQrcode() {
        return wxQrcode;
    }

    public void setWxQrcode(String wxQrcode) {
        this.wxQrcode = wxQrcode;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWhatsUp() {
        return whatsUp;
    }

    public void setWhatsUp(String whatsUp) {
        this.whatsUp = whatsUp;
    }

    public String getConRemark() {
        return conRemark;
    }

    public void setConRemark(String conRemark) {
        this.conRemark = conRemark;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(Integer isBlack) {
        this.isBlack = isBlack;
    }

    public String getBlackReason() {
        return blackReason;
    }

    public void setBlackReason(String blackReason) {
        this.blackReason = blackReason;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoWxUser{" +
        "id=" + id +
        ", wxId=" + wxId +
        ", openId=" + openId +
        ", unionid=" + unionid +
        ", alias=" + alias +
        ", isAssistant=" + isAssistant +
        ", nickName=" + nickName +
        ", realName=" + realName +
        ", softbankName=" + softbankName +
        ", headPhoto=" + headPhoto +
        ", wxQrcode=" + wxQrcode +
        ", sex=" + sex +
        ", province=" + province +
        ", country=" + country +
        ", city=" + city +
        ", email=" + email +
        ", whatsUp=" + whatsUp +
        ", conRemark=" + conRemark +
        ", privilege=" + privilege +
        ", createTime=" + createTime +
        ", isBlack=" + isBlack +
        ", blackReason=" + blackReason +
        "}";
    }
}
