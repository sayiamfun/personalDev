package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoAccessTocken;
import com.warm.system.mapper.PersonalNoAccessTockenMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoAccessTockenService;
import com.warm.utils.VerifyUtils;
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
     * 取得一条数据
     * @return
     */
    @Override
    public PersonalNoAccessTocken getOne(String sql) {
        return accessTockenMapper.getOne(sql);
    }

    /**
     * 删除数据
     * @param sql
     */
    @Override
    public void delete(String sql) {
        accessTockenMapper.delete(sql);
    }

    @Override
    public int add(PersonalNoAccessTocken personalNoAccessTocken) {
        if(VerifyUtils.isEmpty(personalNoAccessTocken.getId())) {
            return accessTockenMapper.add(personalNoAccessTocken);
        }
        return accessTockenMapper.updateOneById(personalNoAccessTocken);
    }

}
