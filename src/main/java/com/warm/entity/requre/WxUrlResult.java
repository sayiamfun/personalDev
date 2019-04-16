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
@ApiModel(value = "微信短链返回参数")
public class WxUrlResult implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "错误码")
    private Integer errcode;
    @ApiModelProperty(value = "错误信息")
    private String errmsg;
    @ApiModelProperty(value = "短链")
    private String short_url;



}
