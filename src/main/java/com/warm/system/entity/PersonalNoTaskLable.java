package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@TableName("personal_no_task_lable")
public class PersonalNoTaskLable extends Model<PersonalNoTaskLable> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 个人号任务id
     */
    @TableField("personal_no_task_id")
    private Integer personalNoTaskId;
    /**
     * 标签id
     */
    @TableField("lable_id")
    private Integer lableId;
    /**
     * 标签名称
     */
    @TableField("lable_name")
    private String lableName;
    /**
     * 逻辑删除
     */
    private Integer deleted;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPersonalNoTaskId() {
        return personalNoTaskId;
    }

    public void setPersonalNoTaskId(Integer personalNoTaskId) {
        this.personalNoTaskId = personalNoTaskId;
    }

    public Integer getLableId() {
        return lableId;
    }

    public void setLableId(Integer lableId) {
        this.lableId = lableId;
    }

    public String getLableName() {
        return lableName;
    }

    public void setLableName(String lableName) {
        this.lableName = lableName;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoTaskLable{" +
        "id=" + id +
        ", personalNoTaskId=" + personalNoTaskId +
        ", lableId=" + lableId +
        ", lableName=" + lableName +
        ", deleted=" + deleted +
        "}";
    }
}
