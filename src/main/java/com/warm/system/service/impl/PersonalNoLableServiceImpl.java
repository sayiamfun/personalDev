package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.entity.requre.BatchUpdateObject;
import com.warm.entity.result.LableManager;
import com.warm.entity.result.LableShow;
import com.warm.system.entity.*;
import com.warm.system.mapper.PersonalNoLableMapper;
import com.warm.system.service.db1.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db2.PersonalNoOperationStockWechatAccountService;
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
    @Autowired
    private PersonalNoOperationStockWechatAccountService wechatAccountService;
    @Autowired
    private PersonalNoLableCategoryService lableCategoryService;

    private String DBTaskLable = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_task_lable);
    private String DBLable = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_lable);
    private String DBTaskPersonal = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_task_personal);
    private String DBPeople = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_people);
    private String DBWeChat = DB.DBAndTable(DB.OA, DB.operation_stock_wechat_account);
    private String DBLableCategory = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_lable_category);

    /*
     * 分页查询标签
     */
    @Override
    public Page<PersonalNoLable> pageQuery(Page<PersonalNoLable> page, String lableName) {
        log.info("根据名称模糊查询标签列表");
        StringBuffer temp = new StringBuffer();
        boolean F = false;
        if (!"-1".equals(lableName)) {
            temp.append(" where lable_name like '%"+lableName+"%'");
            F = true;
        }
        temp = DaoGetSql.getTempSql(temp,F);
        temp.append(" deleted = 0");
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
                log.info("存放好友人数");
                Integer peopleNum = 0;
                log.info("获取该标签下的好友");
                getSql = DaoGetSql.getSql("SELECT `personal_no_wx_id` AS personal_wx_id ,COUNT(*) AS people_num FROM "+DBPeople+" WHERE deleted = 0 AND `personal_friend_wx_id` IS NOT NULL  AND lable LIKE ? GROUP BY `personal_no_wx_id` ","%"+lableManager.getLableName()+"%");
                sql.setSql(getSql);
                List<LableShow> lableShowList = noPeopleService.listLableShowBySql(sql);
                for (LableShow lableShow : lableShowList) {
                    peopleNum += lableShow.getPeopleNum();
                    getSql = DaoGetSql.getSql("SELECT * FROM "+DBWeChat+" WHERE wx_id = ?",lableShow.getPersonalWxId());
                    sql.setSql(getSql);
                    PersonalNoOperationStockWechatAccount wechatAccount = wechatAccountService.getBySql(sql);
                    lableShow.setPersonalName(wechatAccount.getNickName());
                }
                lableManager.setLableShowList(lableShowList);
                lableManager.setPeopleNum(peopleNum);
                lableManager.setPersonalNoNum(lableShowList.size());
                resultList.add(lableManager);
            }
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
            String getSql = DaoGetSql.getSql("SELECT * FROM "+DBLableCategory+" WHERE `category_name` = ? AND deleted = 0 LIMIT 0,1",noLable.getLableCategory());
            PersonalNoLableCategory one1 = lableCategoryService.getOne(getSql);
            noLable.setLableCategoryId(VerifyUtils.isEmpty(one1)?-1:one1.getId());
            noLable.setDb(DBLable);
            noLable.setDeleted(0);
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
        getSql = "SELECT DISTINCT lable_name FROM "+DBTaskLable+" where personal_no_task_id in "+taskIds+" and deleted = 0";
        sql.setSql(getSql);
        List<String> strings = taskLableService.listStringBySql(sql);
        log.info("数据库根据个人号集合查询标签集合结束");
        return strings;
    }

    @Override
    public void updateBySql(Sql sql) {
        personalNoLableMapper.updateBySql(sql);
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
