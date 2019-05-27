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
 * @since 2019-05-16
 */
@Data
@TableName("personal_no_category_and_group")
public class PersonalNoCategoryAndGroup extends Model<PersonalNoCategoryAndGroup> {

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
     * 个人号昵称
     */
    @TableField("nick_name")
    private String nickName;
    /**
     * 类别
     */
    private String category;
    /**
     * 销售组
     */
    private String group;
    /**
     * 逻辑删除标识
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
        return "PersonalNoCategoryAndGroup{" +
        "id=" + id +
        ", personalNoWxId=" + personalNoWxId +
        ", nickName=" + nickName +
        ", category=" + category +
        ", group=" + group +
        ", deleted=" + deleted +
        "}";
    }
}
