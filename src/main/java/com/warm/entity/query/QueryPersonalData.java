package com.warm.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "personalNoTaskData查询对象", description = "个人号数据查询对象封装")
public class QueryPersonalData implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "个人号任务")
    private List<String> noTaskName;
    @ApiModelProperty(value = "个人号任务渠道")
    private List<Integer> personalnoChannelName;
    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

}
