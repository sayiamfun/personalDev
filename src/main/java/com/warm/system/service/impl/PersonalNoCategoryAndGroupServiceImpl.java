package com.warm.system.service.impl;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoCategoryAndGroup;
import com.warm.system.mapper.PersonalNoCategoryAndGroupMapper;
import com.warm.system.service.db1.PersonalNoCategoryAndGroupService;
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
 * @since 2019-05-16
 */
@Service
public class PersonalNoCategoryAndGroupServiceImpl extends ServiceImpl<PersonalNoCategoryAndGroupMapper, PersonalNoCategoryAndGroup> implements PersonalNoCategoryAndGroupService {

    @Autowired
    private PersonalNoCategoryAndGroupMapper categoryAndGroupMapper;

    @Override
    public Integer add(PersonalNoCategoryAndGroup personalNoCategoryAndGroup) {
        if(VerifyUtils.isEmpty(personalNoCategoryAndGroup.getId())){
            return categoryAndGroupMapper.add(personalNoCategoryAndGroup);
        }
        return categoryAndGroupMapper.updateOne(personalNoCategoryAndGroup);
    }

    @Override
    public PersonalNoCategoryAndGroup getBySql(Sql sql) {
        return categoryAndGroupMapper.getBySql(sql);
    }

    @Override
    public List<String> listStringBySql(Sql sql) {
        return categoryAndGroupMapper.listStringBySql(sql);
    }

    @Override
    public void updateBySql(Sql sql) {
        categoryAndGroupMapper.updateBySql(sql);
    }

    @Override
    public Long countBySql(Sql sql) {
        return categoryAndGroupMapper.countBySql(sql);
    }
}
