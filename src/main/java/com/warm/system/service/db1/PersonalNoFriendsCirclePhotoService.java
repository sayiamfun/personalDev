package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoFriendsCircle;
import com.warm.system.entity.PersonalNoFriendsCirclePhoto;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoFriendsCirclePhotoService extends IService<PersonalNoFriendsCirclePhoto> {

    @Transactional
    boolean batchSave(PersonalNoFriendsCircle noFriendsCircle);

    Integer add(PersonalNoFriendsCirclePhoto entity);

    Integer delete(String sql);

    List<PersonalNoFriendsCirclePhoto> list(String sql);

    List<String> listString(String sql);

    PersonalNoFriendsCirclePhoto getOne(String sql);

    Long getCount(String sql);
}
