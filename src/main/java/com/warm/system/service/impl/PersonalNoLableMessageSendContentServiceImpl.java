package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warm.entity.DB;
import com.warm.system.entity.PersonalNoLableMessageSend;
import com.warm.system.entity.PersonalNoLableMessageSendContent;
import com.warm.system.mapper.PersonalNoLableMessageSendContentMapper;
import com.warm.system.service.db1.PersonalNoLableMessageSendContentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
public class PersonalNoLableMessageSendContentServiceImpl extends ServiceImpl<PersonalNoLableMessageSendContentMapper, PersonalNoLableMessageSendContent> implements PersonalNoLableMessageSendContentService {
    private static Log log = LogFactory.getLog(PersonalNoLableMessageSendContentServiceImpl.class);
    @Autowired
    private PersonalNoLableMessageSendContentMapper lableMessageSendContentMapper;

    private String DBLableMessageContent = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_lable_message_send_content);

    /**
     * 批量添加标签消息内容
     * @param personalNoLableMessageSend
     * @return
     */
    @Transactional
    @Override
    public boolean batchSave(PersonalNoLableMessageSend personalNoLableMessageSend) {
        String sql = DaoGetSql.getSql("delete from " + DBLableMessageContent + " where personal_no_lable_message_send_id = ?", personalNoLableMessageSend.getId());
        lableMessageSendContentMapper.delete(sql);
        List<PersonalNoLableMessageSendContent> personalNoLableMessageSendContentList = personalNoLableMessageSend.getPersonalNoLableMessageSendContentList();
        if(!VerifyUtils.collectionIsEmpty(personalNoLableMessageSendContentList)) {
            log.info("开始添加标签消息内容到数据库");
            for (PersonalNoLableMessageSendContent personalNoLableMessageSendContent : personalNoLableMessageSendContentList) {
                personalNoLableMessageSendContent.setId(null);
                personalNoLableMessageSendContent.setPersonalNoLableMessageSendId(personalNoLableMessageSend.getId());
                personalNoLableMessageSendContent.setDb(DBLableMessageContent);
                personalNoLableMessageSendContent.setDeleted(0);
                int insert = lableMessageSendContentMapper.add(personalNoLableMessageSendContent);
                if(insert<0){
                    return false;
                }
            }
            log.info("添加标签消息内容到数据库成功");
        }
        return true;
    }

    @Override
    public Integer add(PersonalNoLableMessageSendContent entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return lableMessageSendContentMapper.add(entity);
        return lableMessageSendContentMapper.updateOne(entity);
    }

    @Override
    public Integer delete(String sql) {
        return lableMessageSendContentMapper.delete(sql);
    }

    @Override
    public List<PersonalNoLableMessageSendContent> list(String sql) {
        return lableMessageSendContentMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return lableMessageSendContentMapper.listString(sql);
    }

    @Override
    public PersonalNoLableMessageSendContent getOne(String sql) {
        return lableMessageSendContentMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return lableMessageSendContentMapper.getCount(sql);
    }
}
