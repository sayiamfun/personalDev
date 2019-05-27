package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoSuperuesr;
import com.warm.system.mapper.PersonalNoSuperuesrMapper;
import com.warm.system.service.db1.PersonalNoSuperuesrService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class PersonalNoSuperuesrServiceImpl extends ServiceImpl<PersonalNoSuperuesrMapper, PersonalNoSuperuesr> implements PersonalNoSuperuesrService {
    private static Log log = LogFactory.getLog(PersonalNoSuperuesrServiceImpl.class);
    @Autowired
    private PersonalNoSuperuesrMapper superuesrMapper;

    private String DBSuper = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_superuesr);
    /*
     * 根据用户名查找用户
     */
    @Override
    public PersonalNoSuperuesr login(String superName) {
        log.info("开始从数据库查找用户");
        String getSql = DaoGetSql.getSql("SELECT * FROM " + DBSuper + " where super_name = ? limit 0,1", superName);
        PersonalNoSuperuesr personalNoSuperuesr = superuesrMapper.getBySql(new Sql(getSql));
        if(VerifyUtils.isEmpty(personalNoSuperuesr)){
            log.info("根据用户名未找到用户");
            return null;
        }
        log.info("查找成功，返回");
        return personalNoSuperuesr;
    }

    @Override
    public Integer add(PersonalNoSuperuesr personalNoSuperuesr) {
        if(VerifyUtils.isEmpty(personalNoSuperuesr.getId())){
            return superuesrMapper.add(personalNoSuperuesr);
        }
        return superuesrMapper.updateOne(personalNoSuperuesr);
    }

    @Override
    public List<PersonalNoSuperuesr> listBySql(Sql sql) {
        return superuesrMapper.listBySql(sql);
    }

    @Override
    public PersonalNoSuperuesr getBySql(Sql sql) {
        return superuesrMapper.getBySql(sql);
    }
}
