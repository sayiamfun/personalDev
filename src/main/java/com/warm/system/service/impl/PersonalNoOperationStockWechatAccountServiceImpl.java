package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoOperationStockWechatAccount;
import com.warm.system.mapper.PersonalNoOperationStockWechatAccountMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db2.PersonalNoOperationStockWechatAccountService;
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
public class PersonalNoOperationStockWechatAccountServiceImpl extends ServiceImpl<PersonalNoOperationStockWechatAccountMapper, PersonalNoOperationStockWechatAccount> implements PersonalNoOperationStockWechatAccountService {

    @Autowired
    private PersonalNoOperationStockWechatAccountMapper wechatAccountMapper;

    @Override
    public Integer add(PersonalNoOperationStockWechatAccount entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return wechatAccountMapper.add(entity);
        return wechatAccountMapper.updateOne(entity);
    }

    @Override
    public Integer delete(String sql) {
        return wechatAccountMapper.delete(sql);
    }

    @Override
    public List<PersonalNoOperationStockWechatAccount> list(String sql) {
        return wechatAccountMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return wechatAccountMapper.listString(sql);
    }

    @Override
    public PersonalNoOperationStockWechatAccount getOne(String sql) {
        return wechatAccountMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return wechatAccountMapper.getCount(sql);
    }
}
