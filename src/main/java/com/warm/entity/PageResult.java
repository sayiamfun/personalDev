package com.warm.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel(value = "分页结果")
@AllArgsConstructor//全参数构造器
@NoArgsConstructor//无参构造器
public class PageResult<T> {
    @ApiModelProperty(value = "总记录数")
    private long total;

    @ApiModelProperty(value = "当前页数据列表")
    private List<T> rows;

}
