package com.warm.system.service.impl;

import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskReplyContent;
import com.warm.system.mapper.PersonalNoTaskReplyContentMapper;
import com.warm.system.service.db1.PersonalNoTaskReplyContentService;
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
public class PersonalNoTaskReplyContentServiceImpl extends ServiceImpl<PersonalNoTaskReplyContentMapper, PersonalNoTaskReplyContent> implements PersonalNoTaskReplyContentService {
    private static Log log = LogFactory.getLog(PersonalNoTaskReplyContentServiceImpl.class);
    @Autowired
    private PersonalNoTaskReplyContentMapper replyContentMapper;

    private String DBTaskReply = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_reply_content);

    /**
     * 批量添加自动回复消息
     * @param noTask
     * @return
     */
    @Transactional
    @Override
    public boolean batchSave(PersonalNoTask noTask) {
        log.info("根据任务id删除回复消息");
        String getSql = DaoGetSql.getSql("update "+DBTaskReply+" set deleted = 1 where personal_no_task_id = ?",noTask.getId());
        Sql sql = new Sql(getSql);
        replyContentMapper.deleteBySql(sql);
        List<PersonalNoTaskReplyContent> noTaskReplyContentList = noTask.getNoTaskReplyContentList();
        if(!VerifyUtils.collectionIsEmpty(noTaskReplyContentList)){
            log.info("开始插入任务回复消息内容");
            for (PersonalNoTaskReplyContent noTaskReplyContent : noTaskReplyContentList) {
                noTaskReplyContent.setId(null);
                noTaskReplyContent.setPersonalNoTaskId(noTask.getId());
                noTaskReplyContent.setDeleted(0);
                noTaskReplyContent.setDb(DBTaskReply);
                int save = replyContentMapper.add(noTaskReplyContent);
                if(save < 0){
                    log.info("将回复消息插入到数据库失败");
                    return false;
                }
            }
        }
        log.info("批量添加任务消息内容成功");
        return true;
    }

    @Override
    public PersonalNoTaskReplyContent getBySql(Sql sql) {
        return replyContentMapper.getBySql(sql);
    }

    @Override
    public List<PersonalNoTaskReplyContent> listBySql(Sql sql) {
        return replyContentMapper.listBySql(sql);
    }

    @Override
    public boolean delBySql(Sql sql) {
        return replyContentMapper.deleteBySql(sql)>=0;
    }
}
