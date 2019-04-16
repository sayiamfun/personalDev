package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.system.entity.PersonalNo;
import com.warm.system.entity.PersonalNoFriends;
import com.baomidou.mybatisplus.service.IService;
import com.warm.system.entity.PersonalNoUser;

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

    List<PersonalNoFriends> listByPersonalId(Integer personalNoId);

    Set<Integer> listByPersonalList(List<PersonalNo> list);

    boolean insert(PersonalNoFriends friends);

    PersonalNoFriends getByPersonalIdAndUserWxId(Integer logicId, String username);

    void removeByFriends(List<PersonalNoFriends> friends);

    List<PersonalNoFriends> listByPersonalWxId(String username);

    PersonalNoFriends getByPersonalWxIdAndUserWxId(String personalNoWxId, String wxId);

    List<Integer> listByPersonalWxIdAndUserWxId(String wxId, String fromUsername);

    List<PersonalNoUser> pageQuery(Page<PersonalNoFriends> page, Map<String, String> map);

    boolean deleteFriends(String personalWxId, List<PersonalNoUser> users);

    boolean blackFriends(String personalWxId, PersonalNoUser user);
}
