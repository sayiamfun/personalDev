package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author dgd123
 * @since 2019-04-10
 */
@Data
@TableName("personal_no_task_remind_flag")
public class PersonalNoTaskRemindFlag extends Model<PersonalNoTaskRemindFlag> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 个人号微信id
     */
    @TableField("personal_no_wx_id")
    private String personalNoWxId;
    /**
     * 用户微信id
     */
    @TableField("user_wx_id")
    private String userWxId;
    /**
     * 任务id
     */
    @TableField("personal_no_task_id")
    private Integer personalNoTaskId;

    @TableField(exist = false)
    private String db;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoTaskRemindFlag{" +
        "id=" + id +
        ", personalNoWxId=" + personalNoWxId +
        ", userWxId=" + userWxId +
        ", personalNoTaskId=" + personalNoTaskId +
        "}";
    }
}
