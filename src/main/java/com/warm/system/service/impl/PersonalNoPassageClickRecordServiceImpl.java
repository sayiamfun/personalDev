package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoPassageClickRecord;
import com.warm.system.mapper.PersonalNoPassageClickRecordMapper;
import com.warm.system.service.db1.PersonalNoPassageClickRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.WebConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    /**
     * 查询最近十分钟之内是否存在此ip
     * @param ipAddress
     * @param date
     * @return
     */
    @Override
    public PersonalNoPassageClickRecord getByIpAndTime(String ipAddress, Date date) {
        return passageClickRecordMapper.getByIpAndTime(ipAddress, WebConst.getNowDate(new Date(date.getTime()-20*60*1000)), WebConst.getNowDate(new Date(date.getTime()+60*1000)));
    }
}
