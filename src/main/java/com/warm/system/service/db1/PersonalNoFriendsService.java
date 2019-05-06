package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.system.entity.PersonalNo;
import com.warm.system.entity.PersonalNoFriends;
import com.baomidou.mybatisplus.service.IService;
import com.warm.system.entity.PersonalNoUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoFriendsService extends IService<PersonalNoFriends> {

    List<PersonalNoUser> pageQuery(Page<PersonalNoFriends> page, Map<String, String> map);

    @Transactional
    boolean deleteFriends(String personalWxId, List<PersonalNoUser> users);

    boolean blackFriends(String personalWxId, PersonalNoUser user);

    Integer add(PersonalNoFriends entity);

    Integer delete(String sql);

    List<PersonalNoFriends> list(String sql);

    List<String> listString(String sql);

    PersonalNoFriends getOne(String sql);

    Long getCount(String sql);
}
