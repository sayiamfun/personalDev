package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoTaskRemindFlag;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-04-10
 */
public interface PersonalNoTaskRemindFlagService extends IService<PersonalNoTaskRemindFlag> {

    PersonalNoTaskRemindFlag getByPersonalWxIdAndUserWxIdAndTaskId(String personalNoWxId, String personalFriendWxId, Integer personalTaskId);
}
