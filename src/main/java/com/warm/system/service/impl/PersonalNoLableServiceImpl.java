package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.requre.BatchUpdateObject;
import com.warm.entity.result.LableManager;
import com.warm.entity.result.LableShow;
import com.warm.system.entity.PersonalNo;
import com.warm.system.entity.PersonalNoLable;
import com.warm.system.entity.PersonalNoTaskLable;
import com.warm.system.entity.PersonalNoTaskPersonal;
import com.warm.system.mapper.PersonalNoLableMapper;
import com.warm.system.service.db1.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.VerifyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Service
public class PersonalNoLableServiceImpl extends ServiceImpl<PersonalNoLableMapper, PersonalNoLable> implements PersonalNoLableService {
    private static Log log = LogFactory.getLog(PersonalNoLableServiceImpl.class);
    @Autowired
    private PersonalNoService noService;
    @Autowired
    private PersonalNoUserService userService;
    @Autowired
    private PersonalNoTaskPersonalService taskPersonalService;
    @Autowired
    private PersonalNoTaskLableService taskLableService;
    @Autowired
    private PersonalNoPeopleService noPeopleService;

    /*
     * 根据类别查找标签列表
     */
    @Override
    public List<PersonalNoLable> listByCategory(String category) {
        List<PersonalNoLable> list = new ArrayList<>();
        log.info("判断要查找的类别参数是否为空");
        EntityWrapper<PersonalNoLable> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        if(VerifyUtils.isEmpty(category)){
            log.info("参数为空,查找全部");
            list = baseMapper.selectList(null);
        }else{
            log.info("参数不为空,根据类别查找");
            entityWrapper.eq("lable_category" , category);
            list = baseMapper.selectList(entityWrapper);
        }
        log.info("从数据库根据类别查找标签成功");
        return list;
    }
    /*
     * 分页查询标签
     */
    @Override
    public Page<PersonalNoLable> pageQuery(Page<PersonalNoLable> page, String lableName) {
        log.info("根据名称模糊查询标签列表");
        EntityWrapper<PersonalNoLable> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        if(!"-1".equals(lableName)){
            entityWrapper.like("lable_name", lableName);
        }
        log.info("根据名称模糊查询标签列表结束");
        List<PersonalNoLable> personalNoLables = baseMapper.selectPage(page, entityWrapper);
        page.setRecords(personalNoLables);
        return page;
    }
    /*
     * 根据标签集合开始统计数据
     */
    @Override
    public List<LableManager> getNumData(List<PersonalNoLable> rows) {
        log.info("数据库开始统计标签数据");
        List<LableManager> resultList = new ArrayList<>();
        if(!VerifyUtils.collectionIsEmpty(rows)){
            log.info("NoLable信息开始装换为LableManager");
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
                Set<LableShow> noSet = new HashSet<>();
                List<PersonalNoTaskLable> taskLableList =  taskLableService.listByLableId(lableManager.getLableId());
                if(!VerifyUtils.collectionIsEmpty(taskLableList)){
                    for (PersonalNoTaskLable personalNoTaskLable : taskLableList) {
                        //根据任务id查找个人号id集合
                        List<PersonalNoTaskPersonal> personals = taskPersonalService.listByTaskId(personalNoTaskLable.getPersonalNoTaskId());
                        if(!VerifyUtils.collectionIsEmpty(personals)){
                            for (PersonalNoTaskPersonal taskpersonal : personals) {
                                LableShow lableShow = new LableShow();
                                lableShow.setPersonalWxId(taskpersonal.getPersonalNoWxId());
                                lableShow.setPersonalName(taskpersonal.getPersonalNoName());
                                noSet.add(lableShow);
                            }
                        }
                    }
                }
                //根据任务id和个人号id查找粉丝id集合
                Set<LableShow> lableShowSet = noPeopleService.listByPersonalIdAndTaskId(noSet, taskLableList);
                lableManager.setLableShowList(lableShowSet);
                //粉丝数量
                int peopleNum = 0;
                if(!VerifyUtils.collectionIsEmpty(lableShowSet)){
                    for (LableShow lableShow : lableShowSet) {
                        peopleNum+=lableShow.getPeopleNum();
                    }
                }
                //设置粉丝数量
                lableManager.setPeopleNum(peopleNum);
                //个人号数量
                lableManager.setPersonalNoNum(lableShowSet.size());
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
        if(VerifyUtils.isEmpty(batchUpdateObject.getObject())){
            log.info("要修改的类别名称为空");
            return false;
        }
        List<PersonalNoLable> lableList = batchUpdateObject.getLableList();
        for (PersonalNoLable noLable : lableList) {
            noLable.setLableCategory(batchUpdateObject.getObject());
            int i = baseMapper.updateById(noLable);
            if(i!=1){
                log.info("数据库批量修改标签失败");
                return false;
            }
        }
        log.info("数据库批量修改标签成功");
        return true;
    }

    /**
     * 根据标签名称查询模糊查询类别名称
     * @param lableName
     * @return
     */
    @Override
    public List<PersonalNoLable> listByLableName(String lableName) {
        EntityWrapper<PersonalNoLable> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        entityWrapper.like("lable_name", lableName);
        return baseMapper.selectList(entityWrapper);
    }

    /**
     * 根据个人号集合查找标签
     * @param list
     * @return
     */
    @Override
    public Set<String> listByPersonal(List<PersonalNo> list) {
        Set<String> lableNameSet = new HashSet<>();
        List<PersonalNoTaskLable> taskLableList = null;
        /**
         * 开始的任务个人号集合是为了存储前端传过来的个人号id集合
         * 根据个人号id查询任务所有的任务个人号
         * 根据任务id查询任务标签
         * 将任务标签集合  转化为  任务标签名集合
         */
        log.info("数据库根据个人号wxid查询任务个人号集合");
        for (PersonalNo no : list) {
            List<PersonalNoTaskPersonal> taskPersonalSet =  taskPersonalService.listByPersonalId(no.getWxId());
            if(!VerifyUtils.collectionIsEmpty(taskPersonalSet)){
                for (PersonalNoTaskPersonal personalNoTaskPersonal : taskPersonalSet) {
                    taskLableList = taskLableService.listByTaskId(personalNoTaskPersonal.getPersonalNoTaskId());
                }
            }
        }
        if(!VerifyUtils.collectionIsEmpty(taskLableList)) {
            for (PersonalNoTaskLable personalNoTaskLable : taskLableList) {
                lableNameSet.add(personalNoTaskLable.getLableName());
            }
        }
        log.info("数据库根据个人号集合查询标签集合结束");
        return lableNameSet;
    }

    /**
     * 根据标签名查找标签
     * @param s
     * @return
     */
    @Override
    public PersonalNoLable getByName(String s) {
        EntityWrapper<PersonalNoLable> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        entityWrapper.eq("lable_name", s);
        PersonalNoLable noLable = null;
        List<PersonalNoLable> personalNoLables = baseMapper.selectList(entityWrapper);
        if(!VerifyUtils.collectionIsEmpty(personalNoLables)){
            noLable = personalNoLables.get(0);
        }
        return noLable;
    }
}
