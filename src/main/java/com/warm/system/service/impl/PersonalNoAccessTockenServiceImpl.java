package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoAccessTocken;
import com.warm.system.mapper.PersonalNoAccessTockenMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoAccessTockenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 微信登录验证 服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Service
public class PersonalNoAccessTockenServiceImpl extends ServiceImpl<PersonalNoAccessTockenMapper, PersonalNoAccessTocken> implements PersonalNoAccessTockenService {
    @Autowired
    private PersonalNoAccessTockenMapper accessTockenMapper;
    /**
     * 取得最后一条数据
     * @return
     */
    @Override
    public PersonalNoAccessTocken getLast() {
        return accessTockenMapper.getLast();
    }

    @Override
    public void deleteByOpenIdId(String openid) {
        accessTockenMapper.deleteByOpenIdId(openid);
    }

    @Override
    public PersonalNoAccessTocken getByOpenId(String openid) {
        return accessTockenMapper.getByOpenId(openid);
    }
}
