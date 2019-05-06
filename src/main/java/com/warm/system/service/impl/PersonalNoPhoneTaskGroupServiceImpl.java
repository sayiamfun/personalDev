package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoPhoneTaskGroup;
import com.warm.system.mapper.PersonalNoPhoneTaskGroupMapper;
import com.warm.system.service.db1.PersonalNoPhoneTaskGroupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.VerifyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class PersonalNoPhoneTaskGroupServiceImpl extends ServiceImpl<PersonalNoPhoneTaskGroupMapper, PersonalNoPhoneTaskGroup> implements PersonalNoPhoneTaskGroupService {

    private static Log log = LogFactory.getLog(PersonalNoPhoneTaskGroupServiceImpl.class);
    @Autowired
    private PersonalNoPhoneTaskGroupMapper taskGroupMapper;


    @Override
    public Integer add(PersonalNoPhoneTaskGroup entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return taskGroupMapper.add(entity);
        return taskGroupMapper.updateOne(entity);
    }

    @Override
    public Integer delete(String sql) {
        return taskGroupMapper.delete(sql);
    }

    @Override
    public List<PersonalNoPhoneTaskGroup> list(String sql) {
        return taskGroupMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return taskGroupMapper.listString(sql);
    }

    @Override
    public PersonalNoPhoneTaskGroup getOne(String sql) {
        return taskGroupMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return taskGroupMapper.getCount(sql);
    }
}
