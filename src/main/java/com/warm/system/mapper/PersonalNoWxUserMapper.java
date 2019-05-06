package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoWxUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
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
public interface PersonalNoWxUserMapper extends BaseMapper<PersonalNoWxUser> {

    @Select("select nick_name from qunliebian_01.wx_user where is_assistant = #{i}")
    List<String> listByASS(int i);
}
