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
@TableName("personal_no_value_table")
public class PersonalNoValueTable extends Model<PersonalNoValueTable> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 微信名
     */
    @TableField("nick_name")
    private String nickName;
    /**
     * 微信id
     */
    @TableField("wx_id")
    private String wxId;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 逻辑删除标志
     */
    private Integer deleted;
    /**
     * 数据用处
     */
    private String name;
    /**
     * 数据
     */
    private String value;

    @TableField(exist = false)
    private String db;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoValueTable{" +
        "id=" + id +
        ", nickName=" + nickName +
        ", wxId=" + wxId +
        ", type=" + type +
        ", deleted=" + deleted +
        ", name=" + name +
        ", value=" + value +
        "}";
    }
}
