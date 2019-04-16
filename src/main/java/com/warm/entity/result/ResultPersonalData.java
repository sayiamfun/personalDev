package com.warm.entity.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "personalNoTaskData查询返回对象", description = "个人号数据查询返回对象封装")
public class ResultPersonalData implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "推送人数")
    private Integer toPeopleNum;
    @ApiModelProperty(value = "加好友人数")
    private Integer addFriendsNum;
    @ApiModelProperty(value = "入群人数")
    private Integer joinGroupNum;
    @ApiModelProperty(value = "当日留存数")
    private Integer todayKeep;
    @ApiModelProperty(value = "加好友率")
    private Double theRateOfAddFriends;
    @ApiModelProperty(value = "入群率")
    private Double theRateOfJoinGroup;
    @ApiModelProperty(value = "全局转化率")
    private Double globalConversionRate;
    @ApiModelProperty(value = "当日留存率")
    private Double theRateOfTodayKeep;
    @ApiModelProperty(value = "时间数据集合")
    private List<ResultPersonalDataWithTime> resultPersonalDataWithTimeList;
    @ApiModelProperty(value = "任务数据集合")
    private List<ResultPersonalDataWithTask> resultPersonalDataWithTaskList;



}
