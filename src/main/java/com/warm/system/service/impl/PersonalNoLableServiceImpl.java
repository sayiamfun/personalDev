package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.entity.requre.BatchUpdateObject;
import com.warm.entity.result.LableManager;
import com.warm.entity.result.LableShow;
import com.warm.system.entity.PersonalNoLable;
import com.warm.system.entity.PersonalNoOperationStockWechatAccount;
import com.warm.system.entity.PersonalNoTaskLable;
import com.warm.system.entity.PersonalNoTaskPersonal;
import com.warm.system.mapper.PersonalNoLableMapper;
import com.warm.system.service.db1.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Service
public class PersonalNoLableServiceImpl extends ServiceImpl<PersonalNoLableMapper, PersonalNoLable> implements PersonalNoLableService {
    private static Log log = LogFactory.getLog(PersonalNoLableServiceImpl.class);
    @Autowired
    private PersonalNoLableMapper personalNoLableMapper;
    @Autowired
    private PersonalNoTaskPersonalService taskPersonalService;
    @Autowired
    private PersonalNoTaskLableService taskLableService;
    @Autowired
    private PersonalNoPeopleService noPeopleService;

    private String DBTaskLable = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_task_lable);
    private String DBLable = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_lable);
    private String DBTaskPersonal = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_task_personal);
    private String DBPeople = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_people);


    /*
     * 分页查询标签
     */
    @Override
    public Page<PersonalNoLable> pageQuery(Page<PersonalNoLable> page, String lableName) {
        log.info("根据名称模糊查询标签列表");
        StringBuffer temp = new StringBuffer();
        if (!"-1".equals(lableName)) {
            temp.append(" where lable_name like '%"+lableName+"%'");
        }
        String getSql = DaoGetSql.getSql("select count(*) from "+DBLable+temp.toString());
        Long count = personalNoLableMapper.getCount(getSql);
        temp.append(" order by id desc limit "+page.getOffset()+","+page.getLimit());
        getSql = DaoGetSql.getSql("select * from "+DBLable+temp.toString());
        log.info("根据名称模糊查询标签列表结束");
        List<PersonalNoLable> personalNoLables = baseMapper.list(getSql);
        page.setRecords(personalNoLables);
        page.setTotal(count.intValue());
        return page;
    }

    /*
     * 根据标签集合开始统计数据
     */
    @Override
    public List<LableManager> getNumData(List<PersonalNoLable> rows) {
        log.info("数据库开始统计标签数据");
        List<LableManager> resultList = new ArrayList<>();
        if (!VerifyUtils.collectionIsEmpty(rows)) {
            log.info("NoLable信息开始装换为LableManager");
            String getSql = "";
            Sql sql = new Sql();
            for (PersonalNoLable row : rows) {
                LableManager lableManager = new LableManager();
                lableManager.setLableId(row.getId());
                lableManager.setLableName(row.getLableName());
                lableManager.setLableCategory(row.getLableCategory());
                /**
                 * 根据标签查找个人号任务列表
                 * 根据任务id查找任务粉丝数量
                 * 根据任务id查找个人号数量
                 */
                //存放标签下的个人号列表（个人号id，个人号名称，粉丝数量）
                log.info("获取标签对应的所有任务id");
                getSql = DaoGetSql.getSql("select DISTINCT personal_no_task_id from " + DBTaskLable + " where lable_id = ?", lableManager.getLableId());
                sql.setSql(getSql);
                List<String> taskIdList = taskLableService.listStringBySql(sql);
                log.info("获取任务下的所有个人号");
                String ids = DaoGetSql.getIds(taskIdList);
                getSql = "SELECT *  FROM " + DBTaskPersonal + " where deleted = 0 and personal_no_task_id in "+ids;
                sql.setSql(getSql);
                List<PersonalNoTaskPersonal> personalNoTaskPersonalList = taskPersonalService.listBySql(sql);
                log.info("存放个人号微信id，去重用");
                Set<String> noWxIdSet = new HashSet<>();
                log.info("存放好友人数");
                Integer peopleNum = 0;
                log.info("获取该个人号下的任务好友");
                List<LableShow> lableShowList  = new ArrayList<>();
                Map<String,LableShow> lableShowMap = new HashMap<>();
                for (PersonalNoTaskPersonal personalNoTaskPersonal : personalNoTaskPersonalList) {
                    noWxIdSet.add(personalNoTaskPersonal.getPersonalNoWxId());
                    getSql = DaoGetSql.getSql("SELECT count(*) FROM " + DBPeople + " where personal_no_wx_id = ? and personal_task_id = ? and deleted = 0 and flag = 2", personalNoTaskPersonal.getPersonalNoWxId(), personalNoTaskPersonal.getPersonalNoTaskId());
                    Long count = noPeopleService.getCount(getSql);
                    peopleNum += count.intValue();
                    if(lableShowMap.containsKey(personalNoTaskPersonal.getPersonalNoWxId())){
                        lableShowMap.get(personalNoTaskPersonal.getPersonalNoWxId()).setPeopleNum(count.intValue()+lableShowMap.get(personalNoTaskPersonal.getPersonalNoWxId()).getPeopleNum());
                    }else {
                        LableShow lableShow = new LableShow();
                        lableShow.setPersonalWxId(personalNoTaskPersonal.getPersonalNoWxId());
                        lableShow.setPersonalName(personalNoTaskPersonal.getPersonalNoName());
                        lableShow.setPeopleNum(count.intValue());
                        lableShowMap.put(personalNoTaskPersonal.getPersonalNoWxId(),lableShow);
                    }
                }
                lableShowList.addAll(lableShowMap.values());
                lableManager.setLableShowList(lableShowList);
                lableManager.setPeopleNum(peopleNum);
                lableManager.setPersonalNoNum(noWxIdSet.size());
                resultList.add(lableManager);
            }
            log.info("标签列表不为空，开始统计数据 粉丝数量，个人号数量");
        }
        log.info("数据库统计标签数据成功");
        return resultList;
    }

    /*
     * 批量修改标签列表的类别
     */
    @Transactional
    @Override
    public boolean batchUpdateCategory(BatchUpdateObject batchUpdateObject) {
        log.info("数据库开始批量修改标签类别");
        if (VerifyUtils.isEmpty(batchUpdateObject.getObject())) {
            log.info("要修改的类别名称为空");
            return false;
        }
        List<PersonalNoLable> lableList = batchUpdateObject.getLableList();
        for (PersonalNoLable noLable : lableList) {
            noLable.setLableCategory(batchUpdateObject.getObject());
            int i = personalNoLableMapper.add(noLable);
            if (i < 0) {
                log.info("数据库批量修改标签失败");
                return false;
            }
        }
        log.info("数据库批量修改标签成功");
        return true;
    }


    /**
     * 根据个人号集合查找标签
     *
     * @param list
     * @return
     */
    @Override
    public List<String> listByPersonal(List<PersonalNoOperationStockWechatAccount> list) {
        List<PersonalNoTaskLable> taskLableList = null;
        /**
         * 开始的任务个人号集合是为了存储前端传过来的个人号id集合
         * 根据个人号id查询任务所有的任务个人号
         * 根据任务id查询任务标签
         * 将任务标签集合  转化为  任务标签名集合
         */
        log.info("数据库根据个人号wxid查询任务个人号集合");
        String getSql = "";
        Sql sql = new Sql();
        StringBuffer noWxIds = new StringBuffer("(''");
        for (PersonalNoOperationStockWechatAccount no : list) {
            noWxIds.append(",'" + no.getWxId() + "'");
        }
        noWxIds.append(")");
        getSql = "SELECT DISTINCT personal_no_task_id FROM "+DBTaskPersonal+" where personal_no_wx_id in "+noWxIds+" and deleted = 0";
        sql.setSql(getSql);
        List<String> taskIdList = taskPersonalService.listStringBySql(sql);
        String taskIds = DaoGetSql.getIds(taskIdList);
        getSql = "SELECT DISTINCT lable_name FROM "+DBTaskLable+" where personal_no_task_id in "+taskIds;
        sql.setSql(getSql);
        List<String> strings = taskLableService.listStringBySql(sql);
        log.info("数据库根据个人号集合查询标签集合结束");
        return strings;
    }

    @Override
    public Integer add(PersonalNoLable entity) {
        if (VerifyUtils.isEmpty(entity.getId()))
            return personalNoLableMapper.add(entity);
        return personalNoLableMapper.updateOne(entity);
    }

    @Override
    public Integer delete(String sql) {
        return personalNoLableMapper.delete(sql);
    }

    @Override
    public List<PersonalNoLable> list(String sql) {
        return personalNoLableMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return personalNoLableMapper.listString(sql);
    }

    @Override
    public PersonalNoLable getOne(String sql) {
        return personalNoLableMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return personalNoLableMapper.getCount(sql);
    }
}
