package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
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
@TableName("personal_no_blacklist")
public class PersonalNoBlacklist extends Model<PersonalNoBlacklist> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户微信id
     */
    @TableField("wx_id")
    private String wxId;
    /**
     * 用户昵称
     */
    @TableField("nick_name")
    private String nickName;
    /**
     * 逻辑删除状态
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
        return "PersonalNoBlacklist{" +
        "id=" + id +
        ", wxId=" + wxId +
        ", nickName=" + nickName +
        ", deleted=" + deleted +
        "}";
    }
}
