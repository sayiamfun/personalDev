package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.Sql;
import com.warm.entity.query.QueryFriendsCircle;
import com.warm.system.entity.PersonalNoFriendsCircle;
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
public interface PersonalNoFriendsCircleService extends IService<PersonalNoFriendsCircle> {

    Page<PersonalNoFriendsCircle> pageQuery(Page<PersonalNoFriendsCircle> page, QueryFriendsCircle searchObj);

    Integer add(PersonalNoFriendsCircle entity);

    Integer deleteBySql(Sql sql);

    List<PersonalNoFriendsCircle> list(String sql);

    List<String> listString(String sql);

    PersonalNoFriendsCircle getOne(String sql);

    Long getCount(String sql);

    PersonalNoFriendsCircle getCircleById(Integer id);
}
