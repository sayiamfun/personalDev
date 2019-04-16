package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskBeginRemind;
import com.warm.system.mapper.PersonalNoTaskBeginRemindMapper;
import com.warm.system.service.db1.PersonalNoTaskBeginRemindService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
public class PersonalNoTaskBeginRemindServiceImpl extends ServiceImpl<PersonalNoTaskBeginRemindMapper, PersonalNoTaskBeginRemind> implements PersonalNoTaskBeginRemindService {
    private static Log log = LogFactory.getLog(PersonalNoTaskBeginRemindServiceImpl.class);
    @Autowired
    private PersonalNoTaskBeginRemindMapper personalNoTaskBeginRemindMapper;
    /*
     * 根据任务id查询开课提醒列表数据
     */
    @Override
    public List<PersonalNoTaskBeginRemind> getListByTaskId(Integer id) { ;
        List<PersonalNoTaskBeginRemind> beginReminds = personalNoTaskBeginRemindMapper.listByTaskId(id);
        log.info("根据任务id查找开课提醒列表结束");
        return beginReminds;
    }

    /**
     * 批量修改开课提醒数据
     * @param noTask
     * @return
     */
    @Transactional
    @Override
    public boolean batchSave(PersonalNoTask noTask) {
        log.info("根据任务id删除任务开课提醒数据");
        deleteByTaskId(noTask.getId());
        log.info("将开课提醒消息保存到数据库");
        List<PersonalNoTaskBeginRemind> noTaskBeginRemindList = noTask.getNoTaskBeginRemindList();
        for (PersonalNoTaskBeginRemind noTaskBeginRemind : noTaskBeginRemindList) {
            noTaskBeginRemind.setPersonalNoTaskId(noTask.getId());
            int save = baseMapper.insert(noTaskBeginRemind);
            if(save != 1){
                log.info("将开课提醒消息保存到数据库失败");
                return false;
            }
        }
        log.info("将开课提醒消息保存到数据库成功");
        return true;
    }

    /**
     * 根据任务id删除相关开课提醒
     * @param taskId
     * @return
     */
    @Override
    public boolean deleteByTaskId(Integer taskId) {
        personalNoTaskBeginRemindMapper.deleteByTaskId(taskId);
        return true;
    }

}
