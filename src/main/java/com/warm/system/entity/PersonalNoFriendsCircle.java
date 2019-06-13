package com.warm.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;

import java.util.ArrayList;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Data
@TableName("personal_no_friends_circle")
public class PersonalNoFriendsCircle extends Model<PersonalNoFriendsCircle> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 朋友圈主题
     */
    @NotBlank(message = "朋友圈主题不能为空")
    @TableField("friends_circle_theme")
    private String friendsCircleTheme;
    /**
     * 是否定时发送
     */
    @TableField("auto_send")
    private Date autoSend;
    /**
     * 朋友圈文案
     */
    @NotBlank(message = "朋友圈文案不能为空")
    @TableField("friends_circle_official")
    private String friendsCircleOfficial;
    /**
     * 卡片链接
     */
    @TableField("card_url")
    private String cardUrl;
    /**
     * 卡片链接封面
     */
    @TableField("card_url_cover")
    private String cardUrlCover;
    /**
     * 待发送,发送中,已发送已取消
     */
    private String status;
    /**
     * 超级用户id
     */
    private Integer superId;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 逻辑删除
     */
    private Integer deleted;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoFriendsCircle{" +
        "id=" + id +
        ", friendsCircleTheme=" + friendsCircleTheme +
        ", autoSend=" + autoSend +
        ", friendsCircleOfficial=" + friendsCircleOfficial +
        ", cardUrl=" + cardUrl +
        ", cardUrlCover=" + cardUrlCover +
        ", status=" + status +
        ", superId=" + superId +
        ", createTime=" + createTime +
        ", deleted=" + deleted +
        "}";
    }
    @ApiModelProperty(value = "朋友圈对应个人号id集合")
    @TableLogic
    @TableField(exist = false)
    private List<PersonalNoFriendsCirclePersonal> personalList = new ArrayList<>();

    @ApiModelProperty(value = "朋友圈对应照片集合")
    @TableField(exist = false)
    private List<PersonalNoFriendsCirclePhoto> photoList = new ArrayList<>();

    @ApiModelProperty(value = "参与个人号数量")
    @TableField(exist = false)
    private Integer personalNum;

    @ApiModelProperty(value = "朋友圈类型")
    @TableField(exist = false)
    private String circleType;

    @TableField(exist = false)
    private String db;
}
