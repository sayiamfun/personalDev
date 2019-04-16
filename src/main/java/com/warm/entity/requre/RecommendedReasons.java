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
@ApiModel(value = "推荐理由请求数据")
public class RecommendedReasons implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "推荐理由")
    private String content;

}
