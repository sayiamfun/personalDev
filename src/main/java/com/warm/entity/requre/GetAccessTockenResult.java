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
@ApiModel(value = "tocken参数")
public class GetAccessTockenResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "tocken")
    private String access_token;

    @ApiModelProperty(value = "错误码")
    private String expires_in;
}
