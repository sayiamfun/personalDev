package com.warm.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "personal_no查询对象", description = "个人号查询对象封装")
public class QueryPersonal implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "个人号昵称")
    private String nickname;

    @ApiModelProperty(value = "设备id")
    private String equipmentId;

    @ApiModelProperty(value = "个人号类别")
    private String personalNoCategoryName;

    @ApiModelProperty(value = "设备状态")
    private String equipmentStatus;

    @ApiModelProperty(value = "标签列表")
    private List<Integer> lableList;

}
