package com.warm.system.service.impl;

import com.warm.entity.Sql;
import com.warm.system.entity.PassageVisitorRecord;
import com.warm.system.mapper.PassageVisitorRecordMapper;
import com.warm.system.service.db1.PassageVisitorRecordService;
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
 * @since 2019-05-09
 */
@Service
public class PassageVisitorRecordServiceImpl extends ServiceImpl<PassageVisitorRecordMapper, PassageVisitorRecord> implements PassageVisitorRecordService {

    @Autowired
    private PassageVisitorRecordMapper passageVisitorRecordMapper;

    @Override
    public PassageVisitorRecord getBySql(Sql sql) {
        return passageVisitorRecordMapper.getBySl(sql);
    }

    @Override
    public Integer add(PassageVisitorRecord passageVisitorRecord) {
        if(VerifyUtils.isEmpty(passageVisitorRecord.getId())){
            return passageVisitorRecordMapper.add(passageVisitorRecord);
        }
        return passageVisitorRecordMapper.updateOne(passageVisitorRecord);
    }

}
