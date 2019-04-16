package com.warm.entity.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "标签管理页的统计数据")
public class LableManager implements Serializable {
    private static final long serialVersionUID = 1L;

    //标签管理字段
    @ApiModelProperty(value = "标签id")
    private Integer lableId;
    @ApiModelProperty(value = "标签名称")
    private String lableName;
    @ApiModelProperty(value = "标签类别")
    private String lableCategory;
    @ApiModelProperty(value = "标签下粉丝数量")
    private Integer peopleNum = 0;
    @ApiModelProperty(value = "标签下个人号数量")
    private Integer personalNoNum = 0;
    @ApiModelProperty(value = "个人号数据集合（个人号名称和粉丝数量）")
    private Set<LableShow> lableShowList;
}
