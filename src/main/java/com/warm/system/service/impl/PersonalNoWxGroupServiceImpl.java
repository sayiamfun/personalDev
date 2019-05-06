package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoWxGroup;
import com.warm.system.mapper.PersonalNoWxGroupMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db3.PersonalNoWxGroupService;
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
public class PersonalNoWxGroupServiceImpl extends ServiceImpl<PersonalNoWxGroupMapper, PersonalNoWxGroup> implements PersonalNoWxGroupService {

    @Autowired
    private PersonalNoWxGroupMapper wxGroupMapper;
    @Override
    public List<PersonalNoWxGroup> list(String sql) {
        return wxGroupMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return wxGroupMapper.listString(sql);
    }

    @Override
    public PersonalNoWxGroup getOne(String sql) {
        return wxGroupMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return wxGroupMapper.getCount(sql);
    }
}
