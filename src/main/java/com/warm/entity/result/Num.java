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
@ApiModel(value = "返回个人号首页的三个总数数据")
public class Num implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "个人号总数")
    private  Integer total = 0;
    @ApiModelProperty(value = "合计好友人数")
    private  Integer friendsNum = 0;
    @ApiModelProperty(value = "承担活动总数")
    private  Integer activityNum = 0;
}
