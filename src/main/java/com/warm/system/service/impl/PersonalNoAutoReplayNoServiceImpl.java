package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoAutoReplayNo;
import com.warm.system.mapper.PersonalNoAutoReplayNoMapper;
import com.warm.system.service.db1.PersonalNoAutoReplayNoService;
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
 * @since 2019-04-03
 */
@Service
public class PersonalNoAutoReplayNoServiceImpl extends ServiceImpl<PersonalNoAutoReplayNoMapper, PersonalNoAutoReplayNo> implements PersonalNoAutoReplayNoService {
    @Autowired
    private PersonalNoAutoReplayNoMapper noAutoReplayNoMapper;

    @Override
    public Integer add(PersonalNoAutoReplayNo entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return noAutoReplayNoMapper.add(entity);
        return noAutoReplayNoMapper.updateOne(entity);
    }

    @Override
    public Integer delete(String sql) {
        return noAutoReplayNoMapper.delete(sql);
    }

    @Override
    public List<PersonalNoAutoReplayNo> list(String sql) {
        return noAutoReplayNoMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return noAutoReplayNoMapper.listString(sql);
    }

    @Override
    public PersonalNoAutoReplayNo getOne(String sql) {
        return noAutoReplayNoMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return noAutoReplayNoMapper.getCount(sql);
    }
}
