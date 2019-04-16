package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskPersonal;
import com.warm.system.mapper.PersonalNoTaskPersonalMapper;
import com.warm.system.service.db1.PersonalNoTaskPersonalService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
public class PersonalNoTaskPersonalServiceImpl extends ServiceImpl<PersonalNoTaskPersonalMapper, PersonalNoTaskPersonal> implements PersonalNoTaskPersonalService {

    private static Log log = LogFactory.getLog(PersonalNoTaskPersonalServiceImpl.class);
    @Autowired
    private PersonalNoTaskPersonalMapper taskPersonalMapper;
    /*
     * 根据任务id查询任务相关个人号信息列表
     */
    @Override
    public List<PersonalNoTaskPersonal> listByTaskId(Integer id) {
        log.info("根据任务id查询所有任务个人号列表");
        List<PersonalNoTaskPersonal> personalList = taskPersonalMapper.listByTaskId(id);
        return personalList;
    }

    /**
     * 批量添加任务个人号
     * @param noTask
     * @return
     */
    @Transactional
    @Override
    public boolean batchSave(PersonalNoTask noTask) {
        taskPersonalMapper.deleteByTaskId(noTask.getId());

        List<PersonalNoTaskPersonal> noList = noTask.getNoList();
        for (PersonalNoTaskPersonal personalNoTaskPersonal : noList) {
            //插入任务id
            personalNoTaskPersonal.setPersonalNoTaskId(noTask.getId());
            int save = baseMapper.insert(personalNoTaskPersonal);
            if(save!=1){
                log.info("将个人号任务个人号插入到数据库失败");
                return false;
            }
        }
        log.info("将个人号任务个人号插入到数据库成功");
        return true;
    }

    /**
     * 根据个人号id查询任务个人号列表
     * @param personalWxId
     * @return
     */
    @Override
    public List<PersonalNoTaskPersonal> listByPersonalId(String personalWxId) {
        log.info("数据库根据个人号id查询任务个人号列表");
        List<PersonalNoTaskPersonal> personalList = taskPersonalMapper.listByPersonalId(personalWxId);
        log.info("数据库根据个人号id查询任务个人号列表结束");
        return personalList;
    }

    /**
     * 根据任务id和个人号微信id查询任务个人号
     * @param integer
     * @param personalWxId
     * @return
     */
    @Override
    public PersonalNoTaskPersonal getByTaskIdAndPersonalWxId(Integer integer, String personalWxId) {
        return taskPersonalMapper.getByTaskIdAndPersonalWxId(integer, personalWxId);
    }

    /**
     * 根据任务id删除任务相关个人号
     * @param taskId
     * @return
     */
    @Override
    public boolean deleteByTaskId(Integer taskId) {
        taskPersonalMapper.deleteByTaskId(taskId);
        return true;
    }
}
