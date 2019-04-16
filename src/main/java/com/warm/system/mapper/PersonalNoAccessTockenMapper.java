package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoAccessTocken;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * 微信登录验证 Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoAccessTockenMapper extends BaseMapper<PersonalNoAccessTocken> {
    //取得最后一条数据
    @Select("select id,flag,access_token,openid,refreshtoken,deleted from personal_no_access_tocken where deleted = 0 order by id desc limit 0,1 ")
    PersonalNoAccessTocken getLast();

    @Update("update personal_no_access_tocken set deleted = 1 where openid = #{openid} and deleted = 0")
    void deleteByOpenIdId(String openid);

    @Select("select id,flag,access_token,openid,refreshtoken,deleted from personal_no_access_tocken where openid = #{openid} and deleted = 0 order by id desc limit 0,1")
    PersonalNoAccessTocken getByOpenId(String openid);
}
