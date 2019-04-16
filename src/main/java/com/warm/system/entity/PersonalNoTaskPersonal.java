package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("personal_no_task_personal")
public class PersonalNoTaskPersonal extends Model<PersonalNoTaskPersonal> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 个人号任务id
     */
    @TableField("personal_no_task_id")
    private Integer personalNoTaskId;
    /**
     * 个人号微信id
     */
    @TableField("personal_no_wx_id")
    private String personalNoWxId;
    /**
     * 个人号名称
     */
    @TableField("personal_no_name")
    private String personalNoName;
    /**
     * 逻辑删除
     */
    private Integer deleted;
    /**
     * 设备id
     */
    @TableField("equipment_id")
    private String equipmentId;
    /**
     * 推送个人号人数
     */
    @TableField("to_people_num")
    private Integer toPeopleNum;


    @ApiModelProperty(value = "渠道数量")
    @TableField(exist = false)
    private Integer peopleNum;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoTaskPersonal{" +
        "id=" + id +
        ", personalNoTaskId=" + personalNoTaskId +
        ", personalNoWxId=" + personalNoWxId +
        ", personalNoName=" + personalNoName +
        ", deleted=" + deleted +
        ", equipmentId=" + equipmentId +
        ", toPeopleNum=" + toPeopleNum +
        "}";
    }
}
