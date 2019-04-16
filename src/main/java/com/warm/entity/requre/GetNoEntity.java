package com.warm.entity.requre;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "根据任务取得个人号")
public class GetNoEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "openid")
    private String openId;
    @ApiModelProperty(value = "unionid")
    private String unionId;
    @ApiModelProperty(value = "昵称")
    private String nickName;
    @ApiModelProperty(value = "任务id")
    private Integer taskId;


}
