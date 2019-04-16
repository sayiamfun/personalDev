package com.warm.entity.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "personalNoTaskDataWithTask查询返回对象", description = "个人号数据查询返回对象封装")
public class ResultPersonalDataWithTask extends ResultPersonalData implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务名称")
    private String taskName;

}
