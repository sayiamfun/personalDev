package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.query.QueryFriendsCircle;
import com.warm.system.entity.PersonalNoFriendsCircle;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoFriendsCircleService extends IService<PersonalNoFriendsCircle> {

    Page<PersonalNoFriendsCircle> pageQuery(Page<PersonalNoFriendsCircle> page, QueryFriendsCircle searchObj);

    PersonalNoFriendsCircle getCircleById(Integer id);

    boolean insertNoFriendCircle(PersonalNoFriendsCircle noFriendsCircle);
}
