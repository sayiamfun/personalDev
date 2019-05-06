package com.warm.entity.robot;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class GroupCategory {
    private Integer id;
    private Integer groupCategorySetId;
    private String cname;
    private Integer upLimit;
    private String prefix;
    private String postfix;
    private Integer beginIndex;
    private Integer currentIndex;
    private String assistantIds;
    private String fullVerify;
    private String cdescription;
    private Date createTime;
    private String personalIds;
    private Integer isGroupFullLogicExecuting;
}
