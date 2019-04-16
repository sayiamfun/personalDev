package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoFriendsCircle;
import com.warm.system.entity.PersonalNoFriendsCirclePhoto;
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
public interface PersonalNoFriendsCirclePhotoService extends IService<PersonalNoFriendsCirclePhoto> {

    List<PersonalNoFriendsCirclePhoto> listByCircleId(Integer id);

    boolean batchSave(PersonalNoFriendsCircle noFriendsCircle);
}
