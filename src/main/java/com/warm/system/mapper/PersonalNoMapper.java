package com.warm.system.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.query.QueryPersonal;
import com.warm.system.entity.PersonalNo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoMapper extends BaseMapper<PersonalNo> {

    @Select("SELECT id,wx_id,wx_name,qr_code,nickname,equipment_id,waiting_pass_num,friends_num,equipment_status,sales_group,personal_no_category AS personalNoCategoryName,head_portrait_url,create_time,super_id,remarks,deleted FROM personal_no WHERE wx_id = #{personalNoWxId} and deleted=0 limit 0,1")
    PersonalNo selectOneByWxId(String personalNoWxId);
    @Select("SELECT id,wx_id,wx_name,qr_code,nickname,equipment_id,waiting_pass_num,friends_num,equipment_status,sales_group,personal_no_category AS personalNoCategoryName,head_portrait_url,create_time,super_id,remarks,deleted FROM personal_no WHERE personal_no_category = #{category} and deleted=0 order by id desc")
    List<PersonalNo> listByCategory(String category);
    @Select("SELECT id,wx_id,wx_name,qr_code,nickname,equipment_id,waiting_pass_num,friends_num,equipment_status,sales_group,personal_no_category AS personalNoCategoryName,head_portrait_url,create_time,super_id,remarks,deleted FROM personal_no WHERE nickname like #{category} and deleted=0 order by id desc")
    List<PersonalNo> listLikeNickName(String nickName);

}
