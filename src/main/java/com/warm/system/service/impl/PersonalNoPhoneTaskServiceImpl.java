package com.warm.system.service.impl;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoPhoneTask;
import com.warm.system.mapper.PersonalNoPhoneTaskMapper;
import com.warm.system.service.db1.PersonalNoPhoneTaskService;
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
public class PersonalNoPhoneTaskServiceImpl extends ServiceImpl<PersonalNoPhoneTaskMapper, PersonalNoPhoneTask> implements PersonalNoPhoneTaskService {

    @Autowired
    private PersonalNoPhoneTaskMapper taskMapper;

     @Override
     public Integer add(PersonalNoPhoneTask entity) {
         if(VerifyUtils.isEmpty(entity.getId()))
             return taskMapper.add(entity);
         return taskMapper.updateOne(entity);
     }

     @Override
     public Integer delete(String sql) {
         return taskMapper.delete(sql);
     }

     @Override
     public List<PersonalNoPhoneTask> list(String sql) {
         return taskMapper.list(sql);
     }

     @Override
     public List<String> listString(String sql) {
         return taskMapper.listString(sql);
     }

     @Override
     public PersonalNoPhoneTask getOne(String sql) {
         return taskMapper.getOne(sql);
     }

     @Override
     public Long getCount(String sql) {
         return taskMapper.getCount(sql);
     }

    @Override
    public Integer updateBySql(Sql sql) {
        return taskMapper.updateBySql(sql);
    }
}
