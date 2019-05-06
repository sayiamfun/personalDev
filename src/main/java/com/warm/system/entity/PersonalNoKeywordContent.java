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
@TableName("personal_no_keyword_content")
public class PersonalNoKeywordContent extends Model<PersonalNoKeywordContent> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 关键词id
     */
    @TableField("personal_no_keyword_id")
    private Integer personalNoKeywordId;
    /**
     * 内容类型
     */
    @TableField("content_type")
    private String contentType;
    /**
     * 内容
     */
    private String content;
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
        return "PersonalNoKeywordContent{" +
        "id=" + id +
        ", personalNoKeywordId=" + personalNoKeywordId +
        ", contentType=" + contentType +
        ", content=" + content +
        ", deleted=" + deleted +
        "}";
    }
}
