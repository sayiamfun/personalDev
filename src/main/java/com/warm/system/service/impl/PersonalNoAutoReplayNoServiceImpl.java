package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.system.entity.PersonalNo;
import com.warm.system.entity.PersonalNoAutoReplayNo;
import com.warm.system.mapper.PersonalNoAutoReplayNoMapper;
import com.warm.system.service.db1.PersonalNoAutoReplayNoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
    /**
     * 分页查询
     * @param page
     * @param nickName
     * @return
     */
    @Override
    public Page<PersonalNoAutoReplayNo> pageQuery(Page<PersonalNoAutoReplayNo> page, String nickName) {
        EntityWrapper<PersonalNoAutoReplayNo> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        if(!"-1".equals(nickName)){
            entityWrapper.like("nick_name", nickName);
        }
        List<PersonalNoAutoReplayNo> personalNoAutoReplayNos = baseMapper.selectPage(page, entityWrapper);
        page.setRecords(personalNoAutoReplayNos);
        return page;
    }

    /**
     * 添加
     * @param no
     * @return
     */
    @Override
    public boolean insertInfo(PersonalNo no) {
        PersonalNoAutoReplayNo autoReplayNo = new PersonalNoAutoReplayNo();
        autoReplayNo.setWxId(no.getWxId());
        autoReplayNo.setNickName(no.getNickname());
        autoReplayNo.setDeleted(0);
        return baseMapper.insert(autoReplayNo)>0;
    }
}
