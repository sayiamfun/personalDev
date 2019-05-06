package com.warm.system.service.impl;

import com.warm.entity.DB;
import com.warm.system.entity.PersonalNo;
import com.warm.system.entity.PersonalNoLable;
import com.warm.system.entity.PersonalNoLableMessageSend;
import com.warm.system.entity.PersonalNoLableMessageSendLableNo;
import com.warm.system.mapper.PersonalNoLableMessageSendLableNoMapper;
import com.warm.system.service.db1.PersonalNoLableMessageSendLableNoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoLableService;
import com.warm.utils.VerifyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class PersonalNoLableMessageSendLableNoServiceImpl extends ServiceImpl<PersonalNoLableMessageSendLableNoMapper, PersonalNoLableMessageSendLableNo> implements PersonalNoLableMessageSendLableNoService {
    private static Log log = LogFactory.getLog(PersonalNoLableMessageSendLableNoServiceImpl.class);
    @Autowired
    private PersonalNoLableService noLableService;
    @Autowired
    private PersonalNoLableMessageSendLableNoMapper lableMessageSendLableNoMapper;

    private String ZCDB = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_lable);
    /**
     * 批量添加标签消息发送的个人号和标签信息
     * @param personalNoLableMessageSend
     * @return
     */
    @Transactional
    @Override
    public boolean batchSave(PersonalNoLableMessageSend personalNoLableMessageSend) {
        log.info("数据库开始批量插入标签消息的个人号和标签信息");
        List<String> lableList = personalNoLableMessageSend.getLableList();
        List<PersonalNo> noList = personalNoLableMessageSend.getNoList();
        if(!VerifyUtils.collectionIsEmpty(noList)){
            String sql = null;
            for (PersonalNo no : noList) {
                PersonalNoLableMessageSendLableNo messageSendLableNo = new PersonalNoLableMessageSendLableNo();
                messageSendLableNo.setPersonalNoLableMessageSendId(personalNoLableMessageSend.getId());
                messageSendLableNo.setPersonalNoId(no.getId());
                messageSendLableNo.setWxId(no.getWxId());
                if(!VerifyUtils.collectionIsEmpty(lableList)){
                    for (String s : lableList) {
                        sql = " select * from "+ZCDB+" where lable_name = '"+s+"' limit 0,1";
                        PersonalNoLable noLable = noLableService.getOne(sql);
                        if(!VerifyUtils.isEmpty(noLable)) {
                            messageSendLableNo.setLableId(noLable.getId());
                            messageSendLableNo.setLableName(noLable.getLableName());
                        }
                        int insert = baseMapper.insert(messageSendLableNo);
                        if (insert != 1) {
                            log.info("数据库批量插入标签消息的个人号和标签信息失败");
                            return false;
                        }

                    }
                }
            }
        }
        log.info("数据库批量插入标签消息的个人号和标签信息成功");
        return true;
    }

    @Override
    public Integer add(PersonalNoLableMessageSendLableNo entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return lableMessageSendLableNoMapper.add(entity);
        return lableMessageSendLableNoMapper.updateOne(entity);
    }

    @Override
    public Integer delete(String sql) {
        return lableMessageSendLableNoMapper.delete(sql);
    }

    @Override
    public List<PersonalNoLableMessageSendLableNo> list(String sql) {
        return lableMessageSendLableNoMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return lableMessageSendLableNoMapper.listString(sql);
    }

    @Override
    public PersonalNoLableMessageSendLableNo getOne(String sql) {
        return lableMessageSendLableNoMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return lableMessageSendLableNoMapper.getCount(sql);
    }
}
