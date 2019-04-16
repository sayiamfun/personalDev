package com.warm.entity.requre;

import com.warm.system.entity.PersonalNo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "去重粉丝人数请求参数")
public class PeopleNumReq implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "个人号集合")
    private List<PersonalNo> noList;
    @ApiModelProperty(value = "标签名称集合")
    private List<String> lableNameList;
    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

}
