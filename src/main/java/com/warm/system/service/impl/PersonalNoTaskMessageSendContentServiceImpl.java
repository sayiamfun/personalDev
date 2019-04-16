package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoTaskMessageSend;
import com.warm.system.entity.PersonalNoTaskMessageSendContent;
import com.warm.system.mapper.PersonalNoTaskMessageSendContentMapper;
import com.warm.system.service.db1.PersonalNoTaskMessageSendContentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
public class PersonalNoTaskMessageSendContentServiceImpl extends ServiceImpl<PersonalNoTaskMessageSendContentMapper, PersonalNoTaskMessageSendContent> implements PersonalNoTaskMessageSendContentService {
    private static Log log = LogFactory.getLog(PersonalNoTaskMessageSendContentServiceImpl.class);
    @Autowired
    private PersonalNoTaskMessageSendContentMapper taskMessageSendContentMapper;
    /*
     * 根据任务消息id查询任务消息内容列表
     */
    @Override
    public List<PersonalNoTaskMessageSendContent> listByTaskMessageContentId(Integer id) {
        log.info("数据库根据任务消息id查询任务消息内容开始");
        List<PersonalNoTaskMessageSendContent> taskMessageSendContentList = taskMessageSendContentMapper.listByTaskMessageContentId(id);
        log.info("数据库根据任务消息id查询任务消息内容结束");
        return taskMessageSendContentList;
    }

    /**
     * 批量插入任务消息内容
     * @param personalNoTaskMessageSend
     * @return
     */
    @Transactional
    @Override
    public boolean batchSave(PersonalNoTaskMessageSend personalNoTaskMessageSend) {
        taskMessageSendContentMapper.deleteByTaskMessageSend(personalNoTaskMessageSend.getId());
        List<PersonalNoTaskMessageSendContent> personalNoTaskMessageSendContentList = personalNoTaskMessageSend.getPersonalNoTaskMessageSendContentList();
        log.info("准备插入任务消息内容");
        if(!VerifyUtils.collectionIsEmpty(personalNoTaskMessageSendContentList)){
            log.info("内容不为空，开始插入数据库");
            for (PersonalNoTaskMessageSendContent personalNoTaskMessageSendContent : personalNoTaskMessageSendContentList) {
                personalNoTaskMessageSendContent.setPersonalNoTaskMessageSendId(personalNoTaskMessageSend.getId());
                int save = baseMapper.insert(personalNoTaskMessageSendContent);
                if(save!=1){
                    log.info("数据库添加任务消息内容到数据库失败");
                    return false;
                }
            }
        }
        log.info("插入任务消息内容成功");
        return true;
    }
}
