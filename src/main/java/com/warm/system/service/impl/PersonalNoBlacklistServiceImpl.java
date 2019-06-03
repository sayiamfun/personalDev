package com.warm.system.service.impl;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoBlacklist;
import com.warm.system.mapper.PersonalNoBlacklistMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoBlacklistService;
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
public class PersonalNoBlacklistServiceImpl extends ServiceImpl<PersonalNoBlacklistMapper, PersonalNoBlacklist> implements PersonalNoBlacklistService {
    private static Log log = LogFactory.getLog(PersonalNoBlacklistServiceImpl.class);
    @Autowired
    private PersonalNoBlacklistMapper blacklistMapper;

    @Override
    public Integer add(PersonalNoBlacklist entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return blacklistMapper.add(entity);
        return blacklistMapper.updateOne(entity);
    }

    @Override
    public Integer deleteBySql(Sql sql) {
        return blacklistMapper.deleteBySql(sql);
    }


    @Override
    public List<PersonalNoBlacklist> list(String sql) {
        return blacklistMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return blacklistMapper.listString(sql);
    }

    @Override
    public PersonalNoBlacklist getOne(String sql) {
        return blacklistMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return blacklistMapper.getCount(sql);
    }
}
