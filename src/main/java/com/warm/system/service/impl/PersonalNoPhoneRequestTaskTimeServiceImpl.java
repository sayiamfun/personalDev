package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoPhoneRequestTaskTime;
import com.warm.system.mapper.PersonalNoPhoneRequestTaskTimeMapper;
import com.warm.system.service.db1.PersonalNoPhoneRequestTaskTimeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Service
public class PersonalNoPhoneRequestTaskTimeServiceImpl extends ServiceImpl<PersonalNoPhoneRequestTaskTimeMapper, PersonalNoPhoneRequestTaskTime> implements PersonalNoPhoneRequestTaskTimeService {

    @Autowired
    private PersonalNoPhoneRequestTaskTimeMapper phoneRequestTaskTimeMapper;

    @Override
    public Integer add(PersonalNoPhoneRequestTaskTime entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return phoneRequestTaskTimeMapper.add(entity);
        return phoneRequestTaskTimeMapper.updateOne(entity);
    }

    @Override
    public Integer delete(String sql) {
        return phoneRequestTaskTimeMapper.delete(sql);
    }

    @Override
    public List<PersonalNoPhoneRequestTaskTime> list(String sql) {
        return phoneRequestTaskTimeMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return phoneRequestTaskTimeMapper.listString(sql);
    }

    @Override
    public PersonalNoPhoneRequestTaskTime getOne(String sql) {
        return phoneRequestTaskTimeMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return phoneRequestTaskTimeMapper.getCount(sql);
    }
}
