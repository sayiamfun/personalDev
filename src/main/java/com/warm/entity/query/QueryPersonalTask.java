package com.warm.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "personal_task查询对象", description = "个人号任务对象封装")
public class QueryPersonalTask implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "个人号任务状态")
    private Integer activityType;

    @ApiModelProperty(value = "个人号任务名称")
    private String taskName;

}
