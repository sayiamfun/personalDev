package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
    @Autowired PersonalNoLableMessageSendLableNoMapper lableMessageSendLableNoMapper;
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
            for (PersonalNo no : noList) {
                PersonalNoLableMessageSendLableNo messageSendLableNo = new PersonalNoLableMessageSendLableNo();
                messageSendLableNo.setPersonalNoLableMessageSendId(personalNoLableMessageSend.getId());
                messageSendLableNo.setPersonalNoId(no.getId());
                messageSendLableNo.setWxId(no.getWxId());
                if(!VerifyUtils.collectionIsEmpty(lableList)){
                    for (String s : lableList) {
                        PersonalNoLable noLable = noLableService.getByName(s);
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

    /**
     * 根据标签消息id查找所有消息标签个人号列表
     * @param id
     * @return
     */
    @Override
    public List<PersonalNoLableMessageSendLableNo> listByMessageSendId(Integer id) {
        log.info("数据库根据标签消息id查找标签消息标签和个人号");
        EntityWrapper<PersonalNoLableMessageSendLableNo> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("personal_no_lable_message_send_id", id);
        List<PersonalNoLableMessageSendLableNo> messageSendLableNoList = baseMapper.selectList(entityWrapper);
        log.info("数据库根据标签消息id查找标签消息标签和个人号结束");
        return messageSendLableNoList;
    }
    /**
     * 根据个人号id查询列表
     * @param username
     * @return
     */
    @Override
    public List<PersonalNoLableMessageSendLableNo> listByPersonalWxId(String username) {
        return lableMessageSendLableNoMapper.listLableMessageIdByPersonalWxId(username);
    }
}
