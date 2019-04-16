package com.warm.entity.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "标签管理页的统计数据查看现实个人号集合")
public class LableShow {

    @ApiModelProperty(value = "个人号wxid")
    private String personalWxId;
    @ApiModelProperty(value = "个人号昵称")
    private String personalName;
    @ApiModelProperty(value = "粉丝数量")
    private Integer peopleNum = 0;
}
