package com.warm.system.service.db1;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoFriendsCircle;
import com.warm.system.entity.PersonalNoFriendsCirclePersonal;
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
public interface PersonalNoFriendsCirclePersonalService extends IService<PersonalNoFriendsCirclePersonal> {

    boolean batchSave(PersonalNoFriendsCircle noFriendsCircle);

    Integer add(PersonalNoFriendsCirclePersonal entity);

    Integer deleteBySql(Sql sql);

    List<PersonalNoFriendsCirclePersonal> list(String sql);

    List<String> listString(String sql);

    PersonalNoFriendsCirclePersonal getOne(String sql);

    Long getCount(String sql);
}
