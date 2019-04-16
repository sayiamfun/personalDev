package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.system.entity.PersonalNoChannel;
import com.warm.system.mapper.PersonalNoChannelMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoChannelService;
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
    public Page<PersonalNoChannel> pageQuery(Page<PersonalNoChannel> page, Object o) {
        EntityWrapper<PersonalNoChannel> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        List<PersonalNoChannel> personalNoChannels = baseMapper.selectPage(page, entityWrapper);
        page.setRecords(personalNoChannels);
        return page;
    }

    /**
     * 根据渠道名称查询渠道
     * @param s
     * @return
     */
    @Override
    public PersonalNoChannel getByChannelName(String s) {
        PersonalNoChannel channel = personalNoChannelMapper.getByName(s);
        return channel;
    }
}
