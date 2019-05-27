package com.warm.system.service.impl;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoTaskRemindFlag;
import com.warm.system.mapper.PersonalNoTaskRemindFlagMapper;
import com.warm.system.service.db1.PersonalNoTaskRemindFlagService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.VerifyUtils;
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

    @Override
    public PersonalNoTaskRemindFlag getBySql(Sql sql) {
        return remindFlagMapper.getBySql(sql);
    }

    @Override
    public Integer add(PersonalNoTaskRemindFlag remindFlag) {
        if(VerifyUtils.isEmpty(remindFlag.getId())){
            return remindFlagMapper.add(remindFlag);
        }
        return remindFlagMapper.updateOne(remindFlag);
    }


}
