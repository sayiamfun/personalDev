package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.result.LableManager;
import com.warm.system.entity.PersonalNoLable;
import com.warm.system.entity.PersonalNoLableCategory;
import com.warm.system.mapper.PersonalNoLableCategoryMapper;
import com.warm.system.service.db1.PersonalNoLableCategoryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoLableService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import net.bytebuddy.asm.Advice;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class PersonalNoLableCategoryServiceImpl extends ServiceImpl<PersonalNoLableCategoryMapper, PersonalNoLableCategory> implements PersonalNoLableCategoryService {
    private static Log log = LogFactory.getLog(PersonalNoLableCategoryServiceImpl.class);
    @Autowired
    private PersonalNoLableService noLableService;
    @Autowired
    private PersonalNoLableCategoryMapper lableCategoryMapper;
    private String DBLableCategory = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_lable_category);
    private String DBLable = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_lable);
    /**
     * 查询个人号类别的标签数量，个人号数量，粉丝数量
     * 根据类别查询标签，根据标签查询个人号数量
     * 根据标签查询粉丝数量
     * @param noLableCategorieList
     * @return
     */
    @Override
    public List<PersonalNoLableCategory> getInfo(List<PersonalNoLableCategory> noLableCategorieList) {
        if(!VerifyUtils.collectionIsEmpty(noLableCategorieList)){
            log.info("数据库根据标签类别查询标签集合");
            String sql = null;
            for (PersonalNoLableCategory noLableCategory : noLableCategorieList) {
                //标签数量
                sql = "select * from "+DBLable+" where lable_category = '"+noLableCategory.getCategoryName()+"'";
                List<PersonalNoLable> noLables = noLableService.list(sql);
                //得到所有标签的数据
                List<LableManager> numData = noLableService.getNumData(noLables);
                //便利标签集合统计类别数据
                int peopleNum = 0;
                int personalNum = 0;
                if(!VerifyUtils.collectionIsEmpty(numData)){
                    for (LableManager numDatum : numData) {
                        peopleNum+=numDatum.getPeopleNum();
                        personalNum+=numDatum.getPersonalNoNum();
                    }
                }
                //设置标签数量
                noLableCategory.setLableNum(noLables.size());
                //设置粉丝数量
                noLableCategory.setUserNum(peopleNum);
                //设置个人号数量
                noLableCategory.setPersonalNum(personalNum);
            }
        }
        log.info("数据库查询个人号类别的标签数量，个人号数量，粉丝数量结束");
        return noLableCategorieList;
    }

    /**
     * 分页查询标签类别列表
     * @param page
     * @param name
     */
    @Override
    public Page<PersonalNoLableCategory> pageList(Page<PersonalNoLableCategory> page, String name) {
        log.info("数据库分页查询标签类别列表开始");
        StringBuffer temp = new StringBuffer();
        if(!"-1".equals(name)) {
            temp.append(" where category_name like '%"+name+"%'");
        }
        String getSql = DaoGetSql.getSql("select count(*) from " + DBLableCategory+temp.toString());
        Long count = lableCategoryMapper.getCount(getSql);
        page.setTotal(count.intValue());
        temp.append(" order by id desc limit "+page.getOffset()+","+page.getLimit());
        log.info("分页查询标签类别列表");
        getSql = DaoGetSql.getSql("select * from " + DBLableCategory+temp.toString());
        List<PersonalNoLableCategory> personalNoLableCategories = lableCategoryMapper.list(getSql);
        log.info("开始查询标签类别相关信息");
        List<PersonalNoLableCategory> info = getInfo(personalNoLableCategories);
        page.setRecords(info);
        log.info("数据库分页查询标签类别列表结束");
        return page;
    }

    @Override
    public Integer add(PersonalNoLableCategory entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return lableCategoryMapper.add(entity);
        return lableCategoryMapper.updateOne(entity);
    }

    @Override
    public Integer delete(String sql) {
        return lableCategoryMapper.delete(sql);
    }

    @Override
    public List<PersonalNoLableCategory> list(String sql) {
        return lableCategoryMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return lableCategoryMapper.listString(sql);
    }

    @Override
    public PersonalNoLableCategory getOne(String sql) {
        return lableCategoryMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return lableCategoryMapper.getCount(sql);
    }

}
