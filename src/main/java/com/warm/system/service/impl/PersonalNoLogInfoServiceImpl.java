package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoLogInfo;
import com.warm.system.mapper.PersonalNoLogInfoMapper;
import com.warm.system.service.db1.PersonalNoLogInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-04-25
 */
@Service
public class PersonalNoLogInfoServiceImpl extends ServiceImpl<PersonalNoLogInfoMapper, PersonalNoLogInfo> implements PersonalNoLogInfoService {

    @Autowired
    private PersonalNoLogInfoMapper personalNoLogInfoMapper;

    @Override
    public Integer add(PersonalNoLogInfo logInfo) {
        if(VerifyUtils.isEmpty(logInfo.getId())){
            return personalNoLogInfoMapper.add(logInfo);
        }
        return personalNoLogInfoMapper.updateOne(logInfo);
    }
}
