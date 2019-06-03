package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskLable;
import com.warm.system.mapper.PersonalNoTaskLableMapper;
import com.warm.system.service.db1.PersonalNoTaskLableService;
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
public class PersonalNoTaskLableServiceImpl extends ServiceImpl<PersonalNoTaskLableMapper, PersonalNoTaskLable> implements PersonalNoTaskLableService {
    private static Log log = LogFactory.getLog(PersonalNoTaskLableServiceImpl.class);
    @Autowired
    private PersonalNoTaskLableMapper taskLableMapper;

    private String DBTaskLable = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_lable);
    /*
     * 根据任务id查找个人号任务标签列表
     */
    @Override
    public List<PersonalNoTaskLable> listBySql(Sql sql) {
        return taskLableMapper.listBySql(sql);
    }


    @Override
    public Integer add(PersonalNoTaskLable personalNoTaskLable) {
        if(VerifyUtils.isEmpty(personalNoTaskLable.getId())){
            return taskLableMapper.add(personalNoTaskLable);
        }
        return taskLableMapper.updateOne(personalNoTaskLable);
    }

    /**
     * 批量添加任务标签
     * @param noTask
     * @return
     */
    @Transactional
    @Override
    public boolean batchSave(PersonalNoTask noTask) {
        log.info("根据任务id删除任务标签");
        String delSql = DaoGetSql.getSql("update "+DBTaskLable+" set deleted = 1 where personal_no_task_id = ?",noTask.getId());
        taskLableMapper.deleteBySql(new Sql(delSql));
        List<PersonalNoTaskLable> noLableList = noTask.getNoLableList();
        if(!VerifyUtils.collectionIsEmpty(noLableList)){
            for (PersonalNoTaskLable personalNoTaskLable : noLableList) {
                //插入任务id
                personalNoTaskLable.setId(null);
                personalNoTaskLable.setPersonalNoTaskId(noTask.getId());
                personalNoTaskLable.setDb(DBTaskLable);
                personalNoTaskLable.setDeleted(0);
                int save = taskLableMapper.add(personalNoTaskLable);
                if(save < 0){
                    log.info("将标签列表保存到数据库失败");
                    return false;
                }
            }
        }
        log.info("批量添加任务标签成功");
        return true;
    }

    /**
     * 根据标签名称返回任务id集合
     * @param lableNameList
     * @return
     */
    @Override
    public List<Integer> listTaskIdsByLableNameList(List<String> lableNameList) {
        log.info("根据标签名称查询所有有此标签的任务id");
        String lableNames = DaoGetSql.getIds(lableNameList);
        String getSql = DaoGetSql.getSql("SELECT DISTINCT personal_no_task_id from " + DBTaskLable + " where lable_name in " + lableNames + " and deleted = 0");
        List<Integer> taskIds = taskLableMapper.listTaskIdsBySql(new Sql(getSql));
        log.info("根据标签名称查询所有有此标签的任务id结束");
        return taskIds;
    }

    @Override
    public boolean deleteBySql(Sql  sql){
        taskLableMapper.deleteBySql(sql);
        return true;
    }

    @Override
    public List<String> listStringBySql(Sql sql) {
        return taskLableMapper.listStringBySql(sql);
    }

    @Override
    public void updateBySql(Sql sql) {
        taskLableMapper.updateBySql(sql);
    }


}
