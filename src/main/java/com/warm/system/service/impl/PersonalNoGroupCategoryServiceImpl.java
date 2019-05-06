package com.warm.system.service.impl;

import com.warm.entity.DB;
import com.warm.system.entity.PersonalNoGroupCategory;
import com.warm.system.mapper.PersonalNoGroupCategoryMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db3.PersonalNoGroupCategoryService;
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
public class PersonalNoGroupCategoryServiceImpl extends ServiceImpl<PersonalNoGroupCategoryMapper, PersonalNoGroupCategory> implements PersonalNoGroupCategoryService {

    private static Log log = LogFactory.getLog(PersonalNoGroupCategoryServiceImpl.class);
    @Autowired
    private PersonalNoGroupCategoryMapper groupCategoryMapper;
    private String ZCDB = DB.DBAndTable(DB.PERSONAL_ZC_WX_GROUP,DB.group_category);
    private String QUNLIEBIAN = DB.DBAndTable(DB.QUNLIEBINA_01,DB.group_category);

    @Override
    public Integer add(PersonalNoGroupCategory entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return groupCategoryMapper.add(entity);
        return groupCategoryMapper.updateOne(entity);
    }


    @Override
    public List<PersonalNoGroupCategory> list(String sql) {
        return groupCategoryMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return groupCategoryMapper.listString(sql);
    }

    @Override
    public PersonalNoGroupCategory getOne(String sql) {
        return groupCategoryMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return groupCategoryMapper.getCount(sql);
    }

    //获取群类别信息
    @Override
    public PersonalNoGroupCategory getPersonalNoGroupCategory(String[] split) {
        String database = ZCDB;
        if(split.length<2){
            return null;
        }
        switch (Integer.parseInt(split[0])){
            case 0:
                break;
            case 1:
                database = QUNLIEBIAN;
                break;
        }
        String sql = "select * from "+database+" where `id` = "+split[1];
        return getOne(sql);
    }
}
