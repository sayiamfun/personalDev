package com.warm.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.entity.PersonalNoAndKeyword;
import com.warm.system.mapper.PersonalNoAndKeywordMapper;
import com.warm.system.service.db1.PersonalNoAndKeywordService;
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
 * @since 2019-04-17
 */
@Service
public class PersonalNoAndKeywordServiceImpl extends ServiceImpl<PersonalNoAndKeywordMapper, PersonalNoAndKeyword> implements PersonalNoAndKeywordService {

    @Autowired
    private PersonalNoAndKeywordMapper personalNoAndKeywordMapper;

    @Override
    public List<PersonalNoAndKeyword> list(String sql) {
        return personalNoAndKeywordMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return personalNoAndKeywordMapper.listString(sql);
    }

    @Override
    public int add(PersonalNoAndKeyword sql) {
        if(VerifyUtils.isEmpty(sql.getId())){
            return personalNoAndKeywordMapper.add(sql);
        }
        return personalNoAndKeywordMapper.updateOneById(sql);
    }


    @Override
    public PersonalNoAndKeyword getOne(String sql) {
        return personalNoAndKeywordMapper.getOne(sql);
    }

    @Override
    public int delete(String sql) {
        return personalNoAndKeywordMapper.delete(sql);
    }
}
