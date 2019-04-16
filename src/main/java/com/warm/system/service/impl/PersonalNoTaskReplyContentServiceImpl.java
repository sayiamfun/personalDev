package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskReplyContent;
import com.warm.system.mapper.PersonalNoTaskReplyContentMapper;
import com.warm.system.service.db1.PersonalNoTaskReplyContentService;
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
public class PersonalNoTaskReplyContentServiceImpl extends ServiceImpl<PersonalNoTaskReplyContentMapper, PersonalNoTaskReplyContent> implements PersonalNoTaskReplyContentService {
    private static Log log = LogFactory.getLog(PersonalNoTaskReplyContentServiceImpl.class);
    /*
     * 根据任务id查询回复列表
     */
    @Override
    public List<PersonalNoTaskReplyContent> getListByTaskId(Integer id) {
        log.info("根据任务id查询任务回复内容列表开始");
        EntityWrapper<PersonalNoTaskReplyContent> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        entityWrapper.eq("personal_no_task_id" , id);
        List<PersonalNoTaskReplyContent> replyContentList = baseMapper.selectList(entityWrapper);
        log.info("根据任务id查询任务回复内容列表结束");
        return replyContentList;
    }

    /**
     * 批量添加自动回复消息
     * @param noTask
     * @return
     */
    @Transactional
    @Override
    public boolean batchSave(PersonalNoTask noTask) {
        log.info("根据任务id删除回复消息");
        deleteByTaskId(noTask.getId());
        List<PersonalNoTaskReplyContent> noTaskReplyContentList = noTask.getNoTaskReplyContentList();
        if(!VerifyUtils.collectionIsEmpty(noTaskReplyContentList)){
            log.info("开始插入任务回复消息内容");
            for (PersonalNoTaskReplyContent noTaskReplyContent : noTaskReplyContentList) {
                noTaskReplyContent.setPersonalNoTaskId(noTask.getId());
                int save = baseMapper.insert(noTaskReplyContent);
                if(save != 1){
                    log.info("将回复消息插入到数据库失败");
                    return false;
                }
            }
        }
        log.info("批量添加任务消息内容成功");
        return true;
    }

    /**
     * 根据任务id删除任务消息
     * @param taskId
     * @return
     */
    @Override
    public boolean deleteByTaskId(Integer taskId) {
        EntityWrapper<PersonalNoTaskReplyContent> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("personal_no_task_id", taskId);
        return baseMapper.delete(entityWrapper)>0;
    }
}
