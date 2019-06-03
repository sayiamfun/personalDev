package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.ArrayList;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Data
@TableName("group_category")
public class PersonalNoGroupCategory extends Model<PersonalNoGroupCategory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @NotNull(message = "群类别集合不能为空")
    @TableField("group_category_set_id")
    private Integer groupCategorySetId;
    @NotBlank(message = "群类别名称不能为空")
    private String cname;
    @TableField("up_limit")
    private Integer upLimit;
    @NotBlank(message = "群名前缀不能为空")
    private String prefix;
    @NotBlank(message = "群名后缀不能为空")
    private String postfix;
    @NotNull(message = "群名不能为空")
    @TableField("begin_index")

    private Integer beginIndex;
    @TableField("current_index")
    private Integer currentIndex;
    @TableField("assistant_ids")
    private String assistantIds;
    @TableField("full_verify")
    private String fullVerify;
    private String cdescription;
    @TableField("create_time")
    private Date createTime;

    @TableField(exist = false)
    private String personalIds;

    @TableField(exist = false)
    private List<String> personalWxidList = new ArrayList<>();

    @TableField(exist = false)
    private List<String> assistantList = new ArrayList<>();

    @TableField(exist = false)
    private Integer groupTotal;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoGroupCategory{" +
        "id=" + id +
        ", groupCategorySetId=" + groupCategorySetId +
        ", cname=" + cname +
        ", upLimit=" + upLimit +
        ", prefix=" + prefix +
        ", postfix=" + postfix +
        ", beginIndex=" + beginIndex +
        ", currentIndex=" + currentIndex +
        ", assistantIds=" + assistantIds +
        ", fullVerify=" + fullVerify +
        ", cdescription=" + cdescription +
        ", createTime=" + createTime +
        "}";
    }
}
