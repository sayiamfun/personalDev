package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoGroupCategorySet;
import com.warm.system.mapper.PersonalNoGroupCategorySetMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db3.PersonalNoGroupCategorySetService;
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
public class PersonalNoGroupCategorySetServiceImpl extends ServiceImpl<PersonalNoGroupCategorySetMapper, PersonalNoGroupCategorySet> implements PersonalNoGroupCategorySetService {
    private static Log log = LogFactory.getLog(PersonalNoGroupCategorySetServiceImpl.class);
    @Autowired
    private PersonalNoGroupCategorySetMapper groupCategorySetMapper;

    @Override
    public List<PersonalNoGroupCategorySet> list(String sql) {
        return groupCategorySetMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return groupCategorySetMapper.listString(sql);
    }

    @Override
    public PersonalNoGroupCategorySet getOne(String sql) {
        return groupCategorySetMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return groupCategorySetMapper.getCount(sql);
    }
}
