package com.warm.entity.requre;

import com.warm.system.entity.PersonalNoLable;
import com.warm.system.entity.PersonalNoOperationStockWechatAccount;
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
@ApiModel(value = "批量修改请求数据")
public class BatchUpdateObject implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "个人号集合")
    private List<PersonalNoOperationStockWechatAccount> personalList;
    @ApiModelProperty(value = "标签集合")
    private List<PersonalNoLable> lableList;
    @ApiModelProperty(value = "要修改的参数属性")
    private String object;
    @ApiModelProperty(value = "要修改的参数标志")
    private String flag;
}
