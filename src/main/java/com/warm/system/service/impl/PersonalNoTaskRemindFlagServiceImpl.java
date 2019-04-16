package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoTaskRemindFlag;
import com.warm.system.mapper.PersonalNoTaskRemindFlagMapper;
import com.warm.system.service.db1.PersonalNoTaskRemindFlagService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-04-10
 */
@Service
public class PersonalNoTaskRemindFlagServiceImpl extends ServiceImpl<PersonalNoTaskRemindFlagMapper, PersonalNoTaskRemindFlag> implements PersonalNoTaskRemindFlagService {
    @Autowired
    private PersonalNoTaskRemindFlagMapper remindFlagMapper;
    /**
     * 根据个人号微信id和用户微信id和任务id查询
     * @param personalNoWxId
     * @param personalFriendWxId
     * @param personalTaskId
     * @return
     */
    @Override
    public PersonalNoTaskRemindFlag getByPersonalWxIdAndUserWxIdAndTaskId(String personalNoWxId, String personalFriendWxId, Integer personalTaskId) {
        return remindFlagMapper.getByPersonalWxIdAndUserWxIdAndTaskId(personalNoWxId,personalFriendWxId,personalTaskId);
    }
}
