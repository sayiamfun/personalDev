package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("personal_no_road")
public class PersonalNoRoad extends Model<PersonalNoRoad> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 通道名称
     */
    @TableField("road_name")
    private String roadName;
    /**
     * 通道背景图
     */
    @TableField("road_background")
    private String roadBackground;
    /**
     * 通道好友数
     */
    @TableField("road_num")
    private Integer roadNum;
    /**
     * 通道创建时间
     */
    @TableField("road_create_time")
    private Date roadCreateTime;
    /**
     * 点击通道人数
     */
    @TableField("click_road_num")
    private Integer clickRoadNum;
    /**
     * 通过ip去重后的人数
     */
    @TableField("less_click_road_num")
    private Integer lessClickRoadNum;
    /**
     * 逻辑删除标识
     */
    private Integer deleted;
    /**
     * 通道链接
     */
    @TableField("road_url")
    private String roadUrl;

    @ApiModelProperty(value = "任务数量")
    @TableField(exist = false)
    private Integer taskNum;

    @TableField(exist = false)
    private String db;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoRoad{" +
        "id=" + id +
        ", roadName=" + roadName +
        ", roadBackground=" + roadBackground +
        ", roadNum=" + roadNum +
        ", roadCreateTime=" + roadCreateTime +
        ", clickRoadNum=" + clickRoadNum +
        ", lessClickRoadNum=" + lessClickRoadNum +
        ", deleted=" + deleted +
        ", roadUrl=" + roadUrl +
        "}";
    }
}
