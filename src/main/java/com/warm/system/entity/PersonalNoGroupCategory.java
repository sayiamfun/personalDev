package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
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
@TableName("personal_no_group_category")
public class PersonalNoGroupCategory extends Model<PersonalNoGroupCategory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("group_category_set_id")
    private Integer groupCategorySetId;
    private String cname;
    @TableField("up_limit")
    private Integer upLimit;
    private String prefix;
    private String postfix;
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupCategorySetId() {
        return groupCategorySetId;
    }

    public void setGroupCategorySetId(Integer groupCategorySetId) {
        this.groupCategorySetId = groupCategorySetId;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Integer getUpLimit() {
        return upLimit;
    }

    public void setUpLimit(Integer upLimit) {
        this.upLimit = upLimit;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }

    public Integer getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(Integer beginIndex) {
        this.beginIndex = beginIndex;
    }

    public Integer getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(Integer currentIndex) {
        this.currentIndex = currentIndex;
    }

    public String getAssistantIds() {
        return assistantIds;
    }

    public void setAssistantIds(String assistantIds) {
        this.assistantIds = assistantIds;
    }

    public String getFullVerify() {
        return fullVerify;
    }

    public void setFullVerify(String fullVerify) {
        this.fullVerify = fullVerify;
    }

    public String getCdescription() {
        return cdescription;
    }

    public void setCdescription(String cdescription) {
        this.cdescription = cdescription;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

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
