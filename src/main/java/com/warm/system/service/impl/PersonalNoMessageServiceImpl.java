package com.warm.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoGroupCategory;
import com.warm.system.entity.PersonalNoMessage;
import com.warm.system.entity.PersonalNoMessageContent;
import com.warm.system.mapper.PersonalNoMessageMapper;
import com.warm.system.service.db1.PersonalNoMessageContentService;
import com.warm.system.service.db1.PersonalNoMessageService;
import com.warm.system.service.db3.PersonalNoGroupCategoryService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import com.warm.utils.WebConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liwenjie123
 * @since 2019-05-06
 */
@Service
public class PersonalNoMessageServiceImpl extends ServiceImpl<PersonalNoMessageMapper, PersonalNoMessage> implements PersonalNoMessageService {

    @Autowired
    private PersonalNoMessageMapper messageMapper;
    @Autowired
    private PersonalNoMessageContentService messageContentService;
    @Autowired
    private PersonalNoGroupCategoryService groupCategoryService;

    private String DBMessage = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_message);
    private String DBMessageContent = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_message_content);

    /**
     * 根据id获取消息和消息内容
     * @param messageId
     * @return
     */
    @Override
    public PersonalNoMessage getById(Integer messageId) {
        String getSql = DaoGetSql.getById(DBMessage,messageId);
        PersonalNoMessage message = messageMapper.getOne(getSql);
        if(!VerifyUtils.isEmpty(message)){
            getSql = DaoGetSql.getSql("SELECT * FROM " + DBMessageContent + " where message_id = ?", messageId);
            List<PersonalNoMessageContent> messageContentList = messageContentService.listBySql(new Sql(getSql));
            message.setMessageContentList(messageContentList);
            for (PersonalNoMessageContent messageContent : messageContentList) {
                if("邀请入群".equals(messageContent.getContentType())){
                    PersonalNoGroupCategory personalNoGroupCategory = groupCategoryService.getPersonalNoGroupCategory(messageContent.getContent());
                    message.setGroupName(personalNoGroupCategory==null?"":personalNoGroupCategory.getCname());
                }
            }
        }
        return message;
    }

    /**
     * 分页查询消息
     * @param page
     * @param message
     * @return
     */
    @Override
    public Page<PersonalNoMessage> pageQuery(Page<PersonalNoMessage> page, String message) {
        List<PersonalNoMessage> list = new ArrayList<>();
        StringBuffer temp = new StringBuffer();
        if(!"-1".equals(message)){
            temp.append(" where keyword like '%"+message+"%'");
        }
        Long count = messageMapper.getCount("select count(*) from " + DBMessage + temp.toString());
        page.setTotal(count.intValue());
        temp.append(" order by id desc limit "+page.getOffset()+","+page.getLimit());
        List<PersonalNoMessage> personalNoKeywords = messageMapper.list("select * from " + DBMessage + temp.toString());
        for (PersonalNoMessage record : personalNoKeywords) {
            PersonalNoMessage infoById = getInfoById(record);
            list.add(infoById);
        }
        page.setRecords(list);
        return page;
    }

     @Override
     @Transactional
     public Integer add(PersonalNoMessage entity) {
         PersonalNoMessage byKeyWord1 = getByKeyWord(entity.getKeyword());
         entity.setDeleted(0);
         entity.setDb(DBMessage);
         if(!VerifyUtils.isEmpty(byKeyWord1) || !VerifyUtils.isEmpty(entity.getId())) {
             messageMapper.updateOne(entity);
         }else {
             messageMapper.add(entity);
         }
         String getSql = DaoGetSql.getSql("DELETE FROM " + DBMessageContent + " where message_id = ?", entity.getId());
         messageContentService.deleteBySql(new Sql(getSql));
         for (PersonalNoMessageContent messageContent : entity.getMessageContentList()) {
             messageContent.setMessageId(entity.getId());
             messageContent.setDeleted(0);
             messageContent.setDb(DBMessageContent);
             boolean save = messageContentService.add(messageContent)>0;
             if(!save){
                 throw new RuntimeException("插入关键词内容出错了");
             }
         }
         return 1;
     }

    @Override
    public PersonalNoMessage getByKeyWord(String keyword) {
        String sql = DaoGetSql.getSql("select * from " + DBMessage + " where keyword = ? limit 0,1" , keyword);
        return messageMapper.getOne(sql);
    }


    @Override
    public PersonalNoMessage getInfoById(PersonalNoMessage PersonalNoMessage) {
        String getSql = DaoGetSql.getSql("SELECT * from " + DBMessageContent + " where message_id = ?", PersonalNoMessage.getId());
        List<PersonalNoMessageContent> messageContentList = messageContentService.listBySql(new Sql(getSql));
        for (PersonalNoMessageContent personalNoKeywordContent : messageContentList) {
            if("邀请入群".equals(personalNoKeywordContent.getContentType())){
                PersonalNoGroupCategory personalNoGroupCategory = groupCategoryService.getPersonalNoGroupCategory(personalNoKeywordContent.getContent());
                PersonalNoMessage.setGroupName(personalNoGroupCategory==null?"":personalNoGroupCategory.getCname());
            }
        }
        PersonalNoMessage.setMessageContentList(messageContentList);
        return PersonalNoMessage;
    }

     @Override
     public Integer delete(String sql) {
         return messageMapper.delete(sql);
     }

     @Override
     public List<PersonalNoMessage> list(String sql) {
         return messageMapper.list(sql);
     }

     @Override
     public List<String> listString(String sql) {
         return messageMapper.listString(sql);
     }

     @Override
     public PersonalNoMessage getOne(String sql) {
         return messageMapper.getOne(sql);
     }

     @Override
     public Long getCount(String sql) {
         return messageMapper.getCount(sql);
     }

    @Override
    public List<PersonalNoMessage> listByMessage(String message) {
        StringBuffer temp = new StringBuffer("select * from "+DBMessage);
        if(!"-1".equals(message)){
            temp.append(" where keyword like '%"+message+"%'");
        }
        temp.append(" order by id desc");
        return messageMapper.list(temp.toString());
    }

    @Override
    public void deleteBySql(Sql sql) {
        messageMapper.deleteBySql(sql);
    }
}
