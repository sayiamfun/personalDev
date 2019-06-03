package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

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
@TableName("personal_no_keyword")
public class PersonalNoKeyword extends Model<PersonalNoKeyword> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 关键词
     */
    @NotBlank(message = "关键词不能为空")
    private String keyword;
    /**
     * 逻辑删除
     */
    private Integer deleted;

    @TableField(exist = false)
    private List<PersonalNoKeywordContent> keywordContentList;

    @TableField(exist = false)
    private String keywordContentShow;

    @TableField(exist = false)
    private List<Integer> personalIdList;

    @TableField(exist = false)
    private List<PersonalNoOperationStockWechatAccount> personalList;

    @TableField(exist = false)
    private String nickNames;

    @TableField(exist = false)
    private String groupName;

    @TableField(exist = false)
    private String db;

    @TableField(exist = false)
    private List<Integer> taskIds;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoKeyword{" +
        "id=" + id +
        ", keyword=" + keyword +
        ", deleted=" + deleted +
        "}";
    }
}
