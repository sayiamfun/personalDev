package com.warm.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author liwenjie123
 * @since 2019-05-06
 */
@Data
@TableName("personal_no_message")
public class PersonalNoMessage extends Model<PersonalNoMessage> {

    private static final long serialVersionUID = 1L;

    /**
     * 消息表
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 关键词
     */
    private String keyword;
    /**
     * 逻辑删除
     */
    private Integer deleted;

    @TableField(exist = false)
    private List<PersonalNoMessageContent> messageContentList;

    @TableField(exist = false)
    private String db;

    @TableField(exist = false)
    private String groupName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoMessage{" +
        "id=" + id +
        ", keyword=" + keyword +
        ", deleted=" + deleted +
        "}";
    }
}
