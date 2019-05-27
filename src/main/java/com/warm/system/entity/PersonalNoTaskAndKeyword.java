package com.warm.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author liwenjie123
 * @since 2019-04-28
 */
@Data
@TableName("personal_no_task_and_keyword")
public class PersonalNoTaskAndKeyword extends Model<PersonalNoTaskAndKeyword> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("task_id")
    private Integer taskId;
    @TableField("task_name")
    private String taskName;
    @TableField("keyword_id")
    private Integer keywordId;
    @TableField("keyword_name")
    private String keywordName;

    @TableField(exist = false)
    private String db;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoTaskAndKeyword{" +
        "id=" + id +
        ", taskId=" + taskId +
        ", taskName=" + taskName +
        ", keywordId=" + keywordId +
        ", keywordName=" + keywordName +
        "}";
    }
}
