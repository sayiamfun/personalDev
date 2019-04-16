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
@TableName("personal_no_data")
public class PersonalNoData extends Model<PersonalNoData> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 当天日期
     */
    private Date date;
    /**
     * 推送人数
     */
    @TableField("to_people_num")
    private Integer toPeopleNum;
    /**
     * 添加好友人数
     */
    @TableField("add_friends_num")
    private Integer addFriendsNum;
    /**
     * 入群人数
     */
    @TableField("join_group_num")
    private Integer joinGroupNum;
    /**
     * 当日留存数
     */
    @TableField("today_keep")
    private Integer todayKeep;
    /**
     * 加好友率
     */
    @TableField("the_rate_of_add_friends")
    private Double theRateOfAddFriends;
    /**
     * 入群率
     */
    @TableField("the_rate_of_join_group")
    private Double theRateOfJoinGroup;
    /**
     * 全局转化率
     */
    @TableField("global_conversion_rate")
    private Double globalConversionRate;
    /**
     * 当日留存率
     */
    @TableField("the_rate_of_today_keep")
    private Double theRateOfTodayKeep;
    /**
     * 任务名称
     */
    @TableField("task_name")
    private String taskName;
    /**
     * 逻辑删除标志
     */
    private Integer deleted;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getToPeopleNum() {
        return toPeopleNum;
    }

    public void setToPeopleNum(Integer toPeopleNum) {
        this.toPeopleNum = toPeopleNum;
    }

    public Integer getAddFriendsNum() {
        return addFriendsNum;
    }

    public void setAddFriendsNum(Integer addFriendsNum) {
        this.addFriendsNum = addFriendsNum;
    }

    public Integer getJoinGroupNum() {
        return joinGroupNum;
    }

    public void setJoinGroupNum(Integer joinGroupNum) {
        this.joinGroupNum = joinGroupNum;
    }

    public Integer getTodayKeep() {
        return todayKeep;
    }

    public void setTodayKeep(Integer todayKeep) {
        this.todayKeep = todayKeep;
    }

    public Double getTheRateOfAddFriends() {
        return theRateOfAddFriends;
    }

    public void setTheRateOfAddFriends(Double theRateOfAddFriends) {
        this.theRateOfAddFriends = theRateOfAddFriends;
    }

    public Double getTheRateOfJoinGroup() {
        return theRateOfJoinGroup;
    }

    public void setTheRateOfJoinGroup(Double theRateOfJoinGroup) {
        this.theRateOfJoinGroup = theRateOfJoinGroup;
    }

    public Double getGlobalConversionRate() {
        return globalConversionRate;
    }

    public void setGlobalConversionRate(Double globalConversionRate) {
        this.globalConversionRate = globalConversionRate;
    }

    public Double getTheRateOfTodayKeep() {
        return theRateOfTodayKeep;
    }

    public void setTheRateOfTodayKeep(Double theRateOfTodayKeep) {
        this.theRateOfTodayKeep = theRateOfTodayKeep;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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
        return "PersonalNoData{" +
        "id=" + id +
        ", date=" + date +
        ", toPeopleNum=" + toPeopleNum +
        ", addFriendsNum=" + addFriendsNum +
        ", joinGroupNum=" + joinGroupNum +
        ", todayKeep=" + todayKeep +
        ", theRateOfAddFriends=" + theRateOfAddFriends +
        ", theRateOfJoinGroup=" + theRateOfJoinGroup +
        ", globalConversionRate=" + globalConversionRate +
        ", theRateOfTodayKeep=" + theRateOfTodayKeep +
        ", taskName=" + taskName +
        ", deleted=" + deleted +
        "}";
    }
}
