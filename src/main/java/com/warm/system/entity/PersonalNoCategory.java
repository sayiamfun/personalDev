package com.warm.system.entity;


import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import java.lang.*;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

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
@TableName("personal_no_category")
public class PersonalNoCategory extends Model<PersonalNoCategory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 个人号类别名称
     */
    @NotBlank(message = "类别名称不能为空")
    @TableField("personal_no_category")
    private String personalNoCategory;
    /**
     * 超级用户id
     */
    @NotNull(message = "请退出重新登录")
    @TableField("super_id")
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
        return "PersonalNoCategory{" +
        "id=" + id +
        ", personalNoCategory=" + personalNoCategory +
        ", superId=" + superId +
        ", createTime=" + createTime +
        ", remarks=" + remarks +
        ", deleted=" + deleted +
        "}";
    }
}
