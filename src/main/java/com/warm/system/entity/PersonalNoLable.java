package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
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
@TableName("personal_no_lable")
public class PersonalNoLable extends Model<PersonalNoLable> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 标签名称
     */
    @NotBlank(message = "标签名称不能为空")
    @TableField("lable_name")
    private String lableName;
    /**
     * 标签类别
     */
    @TableField("lable_category_id")
    private Integer lableCategoryId;

    @NotBlank(message = "标签类别不能为空")
    @TableField("lable_category")
    private String lableCategory;
    /**
     * 超级用户id
     */
    private Integer superId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
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
        return "PersonalNoLable{" +
        "id=" + id +
        ", lableName=" + lableName +
        ", lableCategory=" + lableCategory +
        ", superId=" + superId +
        ", createTime=" + createTime +
        ", remarks=" + remarks +
        ", deleted=" + deleted +
        "}";
    }
}
