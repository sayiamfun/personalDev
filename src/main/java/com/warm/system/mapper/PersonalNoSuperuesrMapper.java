package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoSuperuesr;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoSuperuesrMapper extends BaseMapper<PersonalNoSuperuesr> {

    @Select("SELECT id,super_name,`password`,head_portrait,`code`,deleted,wx_id,openid FROM personal_no_superuesr WHERE openid = #{openid} AND deleted = 0 LIMIT 0,1;")
    PersonalNoSuperuesr getByOpenId(String openid);
    @Select("SELECT id,super_name,`password`,head_portrait,`code`,deleted,wx_id,openid FROM personal_no_superuesr WHERE super_name = #{superName} AND deleted = 0 LIMIT 0,1;")
    PersonalNoSuperuesr getBySuperName(String superName);
}
