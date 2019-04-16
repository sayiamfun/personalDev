package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warm.system.entity.PersonalNoLableMessageSend;
import com.warm.system.entity.PersonalNoLableMessageSendContent;
import com.warm.system.mapper.PersonalNoLableMessageSendContentMapper;
import com.warm.system.service.db1.PersonalNoLableMessageSendContentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.VerifyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    /**
     * 根据标签消息id查找标签消息内容
     * @param id
     * @return
     */
    @Override
    public List<PersonalNoLableMessageSendContent> listByLableMessageId(Integer id) {
        log.info("数据库根据标签消息id查询标签消息内容开始");
        EntityWrapper<PersonalNoLableMessageSendContent> entityWrapper = new EntityWrapper<>();
        if(VerifyUtils.isEmpty(id)){
            log.info("标签消息id为空");
            entityWrapper.eq("personal_no_lable_message_send_id", -1);
        }else {
            entityWrapper.eq("personal_no_lable_message_send_id", id);
        }
        List<PersonalNoLableMessageSendContent> lableMessageSendContentList = baseMapper.selectList(entityWrapper);
        log.info("数据库根据标签消息id查询标签消息内容结束");
        return lableMessageSendContentList;
    }

    /**
     * 批量添加标签消息内容
     * @param personalNoLableMessageSend
     * @return
     */
    @Transactional
    @Override
    public boolean batchSave(PersonalNoLableMessageSend personalNoLableMessageSend) {
       EntityWrapper<PersonalNoLableMessageSendContent> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("personal_no_lable_message_send_id", personalNoLableMessageSend.getId());
        baseMapper.delete(entityWrapper);
        List<PersonalNoLableMessageSendContent> personalNoLableMessageSendContentList = personalNoLableMessageSend.getPersonalNoLableMessageSendContentList();
        if(!VerifyUtils.collectionIsEmpty(personalNoLableMessageSendContentList)) {
            log.info("开始添加标签消息内容到数据库");
            for (PersonalNoLableMessageSendContent personalNoLableMessageSendContent : personalNoLableMessageSendContentList) {
                personalNoLableMessageSendContent.setPersonalNoLableMessageSendId(personalNoLableMessageSend.getId());
                int insert = baseMapper.insert(personalNoLableMessageSendContent);
                if(insert!=1){
                    return false;
                }
            }
            log.info("添加标签消息内容到数据库成功");
        }
        return true;
    }
}
