package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoRequestException;
import com.warm.system.mapper.PersonalNoRequestExceptionMapper;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
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
public class PersonalNoRequestExceptionServiceImpl extends ServiceImpl<PersonalNoRequestExceptionMapper, PersonalNoRequestException> implements PersonalNoRequestExceptionService {

    @Autowired
    private PersonalNoRequestExceptionMapper requestExceptionMapper;
    @Override
    public Integer add(PersonalNoRequestException exception) {
        if(VerifyUtils.isEmpty(exception.getId())){
            return requestExceptionMapper.add(exception);
        }
        return requestExceptionMapper.updateOne(exception);

    }
}
