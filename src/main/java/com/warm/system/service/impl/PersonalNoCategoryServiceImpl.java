package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoCategory;
import com.warm.system.mapper.PersonalNoCategoryMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoCategoryService;
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
    public List<PersonalNoCategory> listAll() {
        return noCategoryMapper.listAll();
    }
}
