package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoPassageClickRecord;
import com.warm.system.mapper.PersonalNoPassageClickRecordMapper;
import com.warm.system.service.db1.PersonalNoPassageClickRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 点击通道链接的记录 服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Service
public class PersonalNoPassageClickRecordServiceImpl extends ServiceImpl<PersonalNoPassageClickRecordMapper, PersonalNoPassageClickRecord> implements PersonalNoPassageClickRecordService {
    @Autowired
    private PersonalNoPassageClickRecordMapper passageClickRecordMapper;

    @Override
    public Integer add(PersonalNoPassageClickRecord entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return passageClickRecordMapper.add(entity);
        return passageClickRecordMapper.updateOne(entity);
    }

    @Override
    public Integer delete(String sql) {
        return passageClickRecordMapper.delete(sql);
    }

    @Override
    public List<PersonalNoPassageClickRecord> list(String sql) {
        return passageClickRecordMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return passageClickRecordMapper.listString(sql);
    }

    @Override
    public PersonalNoPassageClickRecord getOne(String sql) {
        return passageClickRecordMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return passageClickRecordMapper.getCount(sql);
    }
}
