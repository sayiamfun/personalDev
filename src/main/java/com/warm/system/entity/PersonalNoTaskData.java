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
@TableName("personal_no_task_data")
public class PersonalNoTaskData extends Model<PersonalNoTaskData> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 任务id
     */
    @TableField("personal_no_task_id")
    private Integer personalNoTaskId;
    /**
     * 点击链接人数
     */
    @TableField("click_url_num")
    private Integer clickUrlNum;
    /**
     * 推送个人号人数
     */
    @TableField("to_people_num")
    private Integer toPeopleNum;
    /**
     * 逻辑删除标志
     */
    private Integer deleted;

    @ApiModelProperty(value = "个人号数量")
    @TableField(exist = false)
    private Integer peopleNum;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoTaskData{" +
        "id=" + id +
        ", personalNoTaskId=" + personalNoTaskId +
        ", clickUrlNum=" + clickUrlNum +
        ", toPeopleNum=" + toPeopleNum +
        ", deleted=" + deleted +
        "}";
    }
}
