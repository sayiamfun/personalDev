package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.result.LableManager;
import com.warm.system.entity.PersonalNoLable;
import com.warm.system.entity.PersonalNoLableCategory;
import com.warm.system.mapper.PersonalNoLableCategoryMapper;
import com.warm.system.service.db1.PersonalNoLableCategoryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoLableService;
import com.warm.utils.VerifyUtils;
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
    /**
     * 根据标签类别名称查询类别列表
     * @param name
     * @return
     */
    @Override
    public List<PersonalNoLableCategory> listByName(String name) {
        log.info("数据库根据标签类别查询标签列表");
        EntityWrapper<PersonalNoLableCategory> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        if(!"1".equals(name)){
            entityWrapper.like("category_name", name);
        }
        List<PersonalNoLableCategory> noLableCategories = baseMapper.selectList(entityWrapper);
        log.info("数据库根据标签类别查询标签列表结束");
        return noLableCategories;
    }

    /**
     * 查询个人号类别的标签数量，个人号数量，粉丝数量
     * 根据类别查询标签，根据标签查询个人号数量
     * 根据标签查询粉丝数量
     * @param personalList
     * @return
     */
    @Override
    public List<PersonalNoLableCategory> getInfo(List<PersonalNoLableCategory> personalList) {
        if(!VerifyUtils.collectionIsEmpty(personalList)){
            log.info("数据库根据标签类别查询标签集合");
            for (PersonalNoLableCategory noLableCategory : personalList) {
                //标签数量
                List<PersonalNoLable> noLables = noLableService.listByCategory(noLableCategory.getCategoryName());
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
        return personalList;
    }

    /**
     * 分页查询标签类别列表
     * @param page
     * @param name
     */
    @Override
    public Page<PersonalNoLableCategory> pageList(Page<PersonalNoLableCategory> page, String name) {
        log.info("数据库分页查询标签类别列表开始");
        EntityWrapper<PersonalNoLableCategory> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        if(!"-1".equals(name)) {
            entityWrapper.like("category_name", name);
        }
        List<PersonalNoLableCategory> personalNoLableCategories = baseMapper.selectPage(page, entityWrapper);
        page.setRecords(personalNoLableCategories);
        log.info("数据库分页查询标签类别列表结束");
        return page;
    }

}
