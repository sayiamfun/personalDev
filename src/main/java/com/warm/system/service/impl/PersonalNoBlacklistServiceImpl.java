package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoBlacklist;
import com.warm.system.mapper.PersonalNoBlacklistMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoBlacklistService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Service
public class PersonalNoBlacklistServiceImpl extends ServiceImpl<PersonalNoBlacklistMapper, PersonalNoBlacklist> implements PersonalNoBlacklistService {
    private static Log log = LogFactory.getLog(PersonalNoBlacklistServiceImpl.class);
    @Autowired
    private PersonalNoBlacklistMapper blacklistMapper;
    /**
     * 根据微信id查询黑名单成员
     * @param wxId
     * @return
     */
    @Override
    public PersonalNoBlacklist getByWxId(String wxId) {
        log.info("根据微信id查询黑名单信息");
        return blacklistMapper.getByWxId(wxId);
    }
}
