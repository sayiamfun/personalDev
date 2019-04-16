package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warm.system.entity.PersonalNoSuperuesr;
import com.warm.system.mapper.PersonalNoSuperuesrMapper;
import com.warm.system.service.db1.PersonalNoSuperuesrService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.VerifyUtils;
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
public class PersonalNoSuperuesrServiceImpl extends ServiceImpl<PersonalNoSuperuesrMapper, PersonalNoSuperuesr> implements PersonalNoSuperuesrService {
    private static Log log = LogFactory.getLog(PersonalNoSuperuesrServiceImpl.class);
    @Autowired
    private PersonalNoSuperuesrMapper superuesrMapper;
    /*
     * 根据用户名查找用户
     */
    @Override
    public PersonalNoSuperuesr login(String superName) {
        log.info("开始从数据库查找用户");
        PersonalNoSuperuesr personalNoSuperuesr = superuesrMapper.getBySuperName(superName);
        if(VerifyUtils.isEmpty(personalNoSuperuesr)){
            log.info("根据用户名未找到用户");
            return null;
        }
        log.info("查找成功，返回");
        return personalNoSuperuesr;
    }

    /**
     * 根据openid获取用户信息
     * @param openid
     * @return
     */
    @Override
    public PersonalNoSuperuesr getByOpenIdId(String openid) {
        return superuesrMapper.getByOpenId(openid);
    }
}
