package com.warm.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoSendMessage;
import com.warm.system.mapper.PersonalNoSendMessageMapper;
import com.warm.system.service.db1.PersonalNoSendMessageService;
import com.warm.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class PersonalNoSendMessageServiceImpl extends ServiceImpl<PersonalNoSendMessageMapper, PersonalNoSendMessage> implements PersonalNoSendMessageService {

    @Autowired
    private PersonalNoSendMessageMapper sendMessageMapper;

    private String DBSendMessage = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_send_message);

    @Override
    public Page<PersonalNoSendMessage> pageQuery(Page<PersonalNoSendMessage> page, String nickName) {
        StringBuffer temp = new StringBuffer();
        if(!"-1".equals(nickName)){
            temp.append(" where nick_name like '%"+nickName+"%'");
        }
        Sql sql = new Sql("SELECT count(*) FROM "+DBSendMessage+temp.toString());
        Long count = sendMessageMapper.getCount(sql);
        page.setTotal(count.intValue());
        temp.append(" order by id desc limit "+page.getOffset()+","+page.getLimit());
        sql.setSql("SELECT * FROM "+DBSendMessage+temp.toString());
        List<PersonalNoSendMessage> personalNoSendMessages = sendMessageMapper.pageByNickName(sql);
        page.setRecords(personalNoSendMessages);
        return page;
    }

    @Override
    public void deleteOne(Sql sql) {
        sendMessageMapper.deleteBySql(sql);
    }

    @Override
    public Integer add(PersonalNoSendMessage sendMessage) {
        if(VerifyUtils.isEmpty(sendMessage.getId())){
            return sendMessageMapper.add(sendMessage);
        }
        return sendMessageMapper.updateOne(sendMessage);
    }

}
