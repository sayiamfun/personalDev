package com.warm.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoKeyword;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskAndKeyword;
import com.warm.system.mapper.PersonalNoTaskAndKeywordMapper;
import com.warm.system.service.db1.PersonalNoKeywordService;
import com.warm.system.service.db1.PersonalNoTaskAndKeywordService;
import com.warm.system.service.db1.PersonalNoTaskService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liwenjie123
 * @since 2019-04-28
 */
@Service
public class PersonalNoTaskAndKeywordServiceImpl extends ServiceImpl<PersonalNoTaskAndKeywordMapper, PersonalNoTaskAndKeyword> implements PersonalNoTaskAndKeywordService {

    @Autowired
    private PersonalNoTaskAndKeywordMapper taskAndKeywordMapper;
    @Autowired
    private PersonalNoKeywordService keywordService;
    @Autowired
    private PersonalNoTaskService noTaskService;

    private String ZCDBTaskAndKeyword = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_and_keyword);
    private String ZCDBTask = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task);
    private String ZCDBKeyword = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_keyword);


    @Override
    public Page<PersonalNoTaskAndKeyword> pageQuery(Page<PersonalNoTaskAndKeyword> page, Map<String, String> map) {
        StringBuffer temp = new StringBuffer();
        boolean F = false;
        if(!"-1".equals(map.get("taskName"))){
            temp = DaoGetSql.getTempSql(temp,F);
            temp.append(" task_name like '%"+map.get("taskName")+"%' ");
            F = true;
        }
        if(!"-1".equals(map.get("keywordName"))){
            temp = DaoGetSql.getTempSql(temp,F);
            temp.append(" keyword_name like '%"+map.get("keywordName")+"%' ");
        }
        Sql sql = new Sql("SELECT count(*) from " + ZCDBTaskAndKeyword + temp.toString());
        Long count = taskAndKeywordMapper.countBySql(sql);
        page.setTotal(count.intValue());
        temp.append(" order by id desc limit "+page.getOffset()+","+page.getLimit());
        sql.setSql("SELECT * from "+ZCDBTaskAndKeyword+temp.toString());
        List<PersonalNoTaskAndKeyword> taskAndKeywordList = taskAndKeywordMapper.listBySql(sql);
        page.setRecords(taskAndKeywordList);
        return page;
    }

    @Override
    @Transactional
    public Integer add(PersonalNoKeyword keyword) {
        if(VerifyUtils.collectionIsEmpty(keyword.getTaskIds())){
            return -1;
        }
        keyword.setDb(ZCDBKeyword);
        Integer add = keywordService.add(keyword);
        String ids = DaoGetSql.getIds(keyword.getTaskIds());
        if(!(add < 0)){
            System.err.println(keyword.getId());
            String getSql = "SELECT * from "+ZCDBTask+" where id in "+ids;
            List<PersonalNoTask> taskList = noTaskService.listBySql(new Sql(getSql));
            for (PersonalNoTask personalNoTask : taskList) {
                PersonalNoTaskAndKeyword personalNoTaskAndKeyword = new PersonalNoTaskAndKeyword();
                personalNoTaskAndKeyword.setKeywordId(add);
                personalNoTaskAndKeyword.setKeywordName(keyword.getKeyword());
                personalNoTaskAndKeyword.setTaskId(personalNoTask.getId());
                personalNoTaskAndKeyword.setTaskName(personalNoTask.getTaskName());
                personalNoTaskAndKeyword.setDb(ZCDBTaskAndKeyword);
                taskAndKeywordMapper.add(personalNoTaskAndKeyword);
            }
        }
        return 1;
    }

    @Override
    public Integer deleteBySql(Sql sql) {
        return taskAndKeywordMapper.deleteBySql(sql);
    }
}
