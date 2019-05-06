package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoKeywordContent;
import com.warm.system.mapper.PersonalNoKeywordContentMapper;
import com.warm.system.service.db1.PersonalNoKeywordContentService;
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
public class PersonalNoKeywordContentServiceImpl extends ServiceImpl<PersonalNoKeywordContentMapper, PersonalNoKeywordContent> implements PersonalNoKeywordContentService {
    @Autowired
    private PersonalNoKeywordContentMapper keywordContentMapper;

    @Override
    public Integer add(PersonalNoKeywordContent entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return keywordContentMapper.add(entity);
        return keywordContentMapper.updateOne(entity);
    }

    @Override
    public Integer delete(String sql) {
        return keywordContentMapper.delete(sql);
    }

    @Override
    public List<PersonalNoKeywordContent> list(String sql) {
        return keywordContentMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return keywordContentMapper.listString(sql);
    }

    @Override
    public PersonalNoKeywordContent getOne(String sql) {
        return keywordContentMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return keywordContentMapper.getCount(sql);
    }
}
