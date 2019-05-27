package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoCategory;
import com.warm.system.mapper.PersonalNoCategoryMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoCategoryService;
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
public class PersonalNoCategoryServiceImpl extends ServiceImpl<PersonalNoCategoryMapper, PersonalNoCategory> implements PersonalNoCategoryService {

    private static Log log = LogFactory.getLog(PersonalNoCategoryServiceImpl.class);
    @Autowired
    private PersonalNoCategoryMapper noCategoryMapper;

    @Override
    public Integer add(PersonalNoCategory entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return noCategoryMapper.add(entity);
        return noCategoryMapper.updateOne(entity);
    }

    @Override
    public Integer delete(String sql) {
        return noCategoryMapper.delete(sql);
    }

    @Override
    public List<PersonalNoCategory> list(String sql) {
        return noCategoryMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return noCategoryMapper.listString(sql);
    }

    @Override
    public PersonalNoCategory getOne(String sql) {
        return noCategoryMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return noCategoryMapper.getCount(sql);
    }
}
