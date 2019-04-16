package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoUser;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoUserService extends IService<PersonalNoUser> {

    PersonalNoUser getByWxId(String username);

    List<PersonalNoUser> getByNickName(String nickname);

    PersonalNoUser getByOpenid(String personalFriendNickName);

    List<PersonalNoUser> listByWxId(String wxId);

    PersonalNoUser getByUnionId(String unionid);
}
