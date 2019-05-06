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
@TableName("personal_no")
public class PersonalNo extends Model<PersonalNo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 微信id
     */
    @TableField("wx_id")
    private String wxId;
    /**
     * 微信别名
     */
    @TableField("wx_name")
    private String wxName;
    /**
     * 二维码
     */
    @TableField("qr_code")
    private String qrCode;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 设备id
     */
    @TableField("equipment_id")
    private String equipmentId;
    /**
     * 等待通过验证数量
     */
    @TableField("waiting_pass_num")
    private Integer waitingPassNum;
    /**
     * 好友数量
     */
    @TableField("friends_num")
    private Integer friendsNum;
    /**
     * 设备状态
     */
    @TableField("equipment_status")
    private String equipmentStatus;
    /**
     * 销售组
     */
    @TableField("sales_group")
    private String salesGroup;
    /**
     * 个人号类别名称
     */
    @TableField("personal_no_category")
    private String personalNoCategory;
    /**
     * 头像url
     */
    @TableField("head_portrait_url")
    private String headPortraitUrl;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 超级用户id
     */
    @TableField("super_id")
    private Integer superId;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 逻辑删除
     */
    private Integer deleted;

    @TableField(exist = false)
    private String db;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNo{" +
        "id=" + id +
        ", wxId=" + wxId +
        ", wxName=" + wxName +
        ", qrCode=" + qrCode +
        ", nickname=" + nickname +
        ", equipmentId=" + equipmentId +
        ", waitingPassNum=" + waitingPassNum +
        ", friendsNum=" + friendsNum +
        ", equipmentStatus=" + equipmentStatus +
        ", salesGroup=" + salesGroup +
        ", personalNoCategory=" + personalNoCategory +
        ", headPortraitUrl=" + headPortraitUrl +
        ", createTime=" + createTime +
        ", superId=" + superId +
        ", remarks=" + remarks +
        ", deleted=" + deleted +
        "}";
    }
}
