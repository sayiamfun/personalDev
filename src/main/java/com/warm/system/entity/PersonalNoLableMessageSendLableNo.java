package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
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
@TableName("personal_no_lable_message_send_lable_no")
public class PersonalNoLableMessageSendLableNo extends Model<PersonalNoLableMessageSendLableNo> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标签消息id
     */
    @TableField("personal_no_lable_message_send_id")
    private Integer personalNoLableMessageSendId;
    /**
     * 标签id
     */
    @TableField("lable_id")
    private Integer lableId;
    /**
     * 标签名称
     */
    @TableField("lable_name")
    private String lableName;
    /**
     * 个人号id
     */
    @TableField("personal_no_id")
    private Integer personalNoId;
    /**
     * 个人号微信id
     */
    @TableField("wx_id")
    private String wxId;
    /**
     * 逻辑删除
     */
    private Integer deleted;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPersonalNoLableMessageSendId() {
        return personalNoLableMessageSendId;
    }

    public void setPersonalNoLableMessageSendId(Integer personalNoLableMessageSendId) {
        this.personalNoLableMessageSendId = personalNoLableMessageSendId;
    }

    public Integer getLableId() {
        return lableId;
    }

    public void setLableId(Integer lableId) {
        this.lableId = lableId;
    }

    public String getLableName() {
        return lableName;
    }

    public void setLableName(String lableName) {
        this.lableName = lableName;
    }

    public Integer getPersonalNoId() {
        return personalNoId;
    }

    public void setPersonalNoId(Integer personalNoId) {
        this.personalNoId = personalNoId;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoLableMessageSendLableNo{" +
        "id=" + id +
        ", personalNoLableMessageSendId=" + personalNoLableMessageSendId +
        ", lableId=" + lableId +
        ", lableName=" + lableName +
        ", personalNoId=" + personalNoId +
        ", wxId=" + wxId +
        ", deleted=" + deleted +
        "}";
    }
}
