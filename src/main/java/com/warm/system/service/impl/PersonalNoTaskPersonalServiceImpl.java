package com.warm.system.service.impl;

import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskPersonal;
import com.warm.system.mapper.PersonalNoTaskPersonalMapper;
import com.warm.system.service.db1.PersonalNoTaskPersonalService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.DaoGetSql;
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

    private String DBTaskPersonal = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_personal);

    /**
     * 批量添加任务个人号
     * @param noTask
     * @return
     */
    @Transactional
    @Override
    public boolean batchSave(PersonalNoTask noTask) {
        String getSql = DaoGetSql.getSql("UPDATE "+DBTaskPersonal+" set deleted = 1 where personal_no_task_id = ?",noTask.getId());
        taskPersonalMapper.deleteBySql(new Sql(getSql));
        List<PersonalNoTaskPersonal> noList = noTask.getNoList();
        for (PersonalNoTaskPersonal personalNoTaskPersonal : noList) {
            //插入任务id
            personalNoTaskPersonal.setId(null);
            personalNoTaskPersonal.setPersonalNoTaskId(noTask.getId());
            personalNoTaskPersonal.setDeleted(0);
            personalNoTaskPersonal.setDb(DBTaskPersonal);
            int save = taskPersonalMapper.add(personalNoTaskPersonal);
            if(save<0){
                log.info("将个人号任务个人号插入到数据库失败");
                return false;
            }
        }
        log.info("将个人号任务个人号插入到数据库成功");
        return true;
    }

    @Override
    public boolean deleteBySql(Sql sql) {
        return taskPersonalMapper.deleteBySql(sql)>0;
    }

    @Override
    public List<PersonalNoTaskPersonal> listBySql(Sql sql) {
        return taskPersonalMapper.listBySql(sql);
    }

    @Override
    public Long countBySql(Sql sql) {
        return taskPersonalMapper.countBySql(sql);
    }

    @Override
    public List<String> listStringBySql(Sql sql) {
        return taskPersonalMapper.listStringBySql(sql);
    }


}
