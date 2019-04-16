package com.warm.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "personal_no_friends_circle查询对象", description = "朋友圈查询对象封装")
public class QueryFriendsCircle implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "个人号类别")
    private String personal_no_category;

    @ApiModelProperty(value = "个人号id")
    private Integer personal_no_id;

    @ApiModelProperty(value = "发送状态")
    private String status;

    @ApiModelProperty(value = "朋友圈主题(模糊查询)")
    private String friendsCircleTheme;

    @ApiModelProperty(value = "发送时间")
    private Date sendTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

}