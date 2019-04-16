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
@ApiModel(value = "返回个人号任务的链接信息")
public class TaskUrlResult implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "开始任务地址")
    private  String taskUrl;

    @ApiModelProperty(value = "结束任务地址")
    private  String taskEndUrl;

    @ApiModelProperty(value = "推广渠道")
    private  String qrCodeUrl;


}
