package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoChannel;
import com.warm.system.mapper.PersonalNoChannelMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoChannelService;
import com.warm.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
public class PersonalNoChannelServiceImpl extends ServiceImpl<PersonalNoChannelMapper, PersonalNoChannel> implements PersonalNoChannelService {
    @Autowired
    private PersonalNoChannelMapper personalNoChannelMapper;


    @Override
    public Integer add(PersonalNoChannel entity) {
        if(VerifyUtils.isEmpty(entity.getId())) {
            return personalNoChannelMapper.add(entity);
        }
        return personalNoChannelMapper.updateOne(entity);
    }

    @Override
    public Integer deleteBySql(Sql sql) {
        return personalNoChannelMapper.deleteBySql(sql);
    }

    @Override
    public List<PersonalNoChannel> list(String sql) {
        return personalNoChannelMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return personalNoChannelMapper.listString(sql);
    }

    @Override
    public PersonalNoChannel getOne(String sql) {
        return personalNoChannelMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return personalNoChannelMapper.getCount(sql);
    }
}
