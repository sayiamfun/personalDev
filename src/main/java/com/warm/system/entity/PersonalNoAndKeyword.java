package com.warm.system.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author dgd123
 * @since 2019-04-17
 */
@Data
@TableName("personal_no_and_keyword")
public class PersonalNoAndKeyword extends Model<PersonalNoAndKeyword> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 个人号id
     */
    @TableField("personal_no_id")
    private Integer personalNoId;
    /**
     * 个人号微信id
     */
    @TableField("personal_no_wx_id")
    private String personalNoWxId;
    /**
     * 个人号昵称
     */
    @TableField("personal_no_nick_name")
    private String personalNoNickName;
    /**
     * 关键词id
     */
    @TableField("keyword_id")
    private Integer keywordId;

    @TableField("keyword_name")
    private String keywordName;

    @TableField(exist = false)
    private String db;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "PersonalNoAndKeyword{" +
        "id=" + id +
        ", personalNoId=" + personalNoId +
        ", personalNoWxId=" + personalNoWxId +
        ", personalNoNickName=" + personalNoNickName +
        ", keywordId=" + keywordId +
        "}";
    }


}
