package com.warm.system.service.impl;

import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskBeginRemind;
import com.warm.system.mapper.PersonalNoTaskBeginRemindMapper;
import com.warm.system.service.db1.PersonalNoTaskBeginRemindService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.DaoGetSql;
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
public class PersonalNoTaskBeginRemindServiceImpl extends ServiceImpl<PersonalNoTaskBeginRemindMapper, PersonalNoTaskBeginRemind> implements PersonalNoTaskBeginRemindService {
    private static Log log = LogFactory.getLog(PersonalNoTaskBeginRemindServiceImpl.class);
    @Autowired
    private PersonalNoTaskBeginRemindMapper personalNoTaskBeginRemindMapper;

    private String ZCDBBeginRemind = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_begin_remind);
    /**
     * 批量修改开课提醒数据
     * @param noTask
     * @return
     */
    @Transactional
    @Override
    public boolean batchSave(PersonalNoTask noTask) {
        log.info("根据任务id删除任务开课提醒数据");
        String delSql = DaoGetSql.getSql("update " + ZCDBBeginRemind + " set deleted = 1 where personal_no_task_id = ?", noTask.getId());
        personalNoTaskBeginRemindMapper.deleteBySql(new Sql(delSql));
        log.info("将开课提醒消息保存到数据库");
        List<PersonalNoTaskBeginRemind> noTaskBeginRemindList = noTask.getNoTaskBeginRemindList();
        for (PersonalNoTaskBeginRemind noTaskBeginRemind : noTaskBeginRemindList) {
            noTaskBeginRemind.setId(null);
            noTaskBeginRemind.setPersonalNoTaskId(noTask.getId());
            noTaskBeginRemind.setDb(ZCDBBeginRemind);
            noTaskBeginRemind.setDeleted(0);
            int save = personalNoTaskBeginRemindMapper.add(noTaskBeginRemind);
            if(save < 0){
                log.info("将开课提醒消息保存到数据库失败");
                return false;
            }
        }
        log.info("将开课提醒消息保存到数据库成功");
        return true;
    }


    @Override
    public Integer add(PersonalNoTaskBeginRemind personalNoTaskBeginRemind) {
        if(VerifyUtils.isEmpty(personalNoTaskBeginRemind.getId())){
            return personalNoTaskBeginRemindMapper.add(personalNoTaskBeginRemind);
        }
        return personalNoTaskBeginRemindMapper.updateOne(personalNoTaskBeginRemind);
    }

    @Override
    public List<PersonalNoTaskBeginRemind> listBySql(Sql sql) {
        return personalNoTaskBeginRemindMapper.listBySql(sql);
    }

    @Override
    public boolean deleteBySql(Sql sql) {
        personalNoTaskBeginRemindMapper.deleteBySql(sql);
        return true;
    }

}
