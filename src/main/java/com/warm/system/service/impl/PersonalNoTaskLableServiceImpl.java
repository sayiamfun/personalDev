package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskLable;
import com.warm.system.mapper.PersonalNoTaskLableMapper;
import com.warm.system.service.db1.PersonalNoTaskLableService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.VerifyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    /*
     * 根据任务id查找个人号任务标签列表
     */
    @Override
    public List<PersonalNoTaskLable> listByTaskId(Integer id) {
        log.info("数据库开始根据任务id查找标签列表");
        EntityWrapper<PersonalNoTaskLable> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        if(!VerifyUtils.isEmpty(id)){
            log.info("根据任务id查询");
            entityWrapper.eq("personal_no_task_id" , id);
        }
        List<PersonalNoTaskLable> lableList = baseMapper.selectList(entityWrapper);
        log.info("查找完成返回标签数据");
        return lableList;
    }
    /*
     * 根据标签id查找所有个人号任务标签列表
     */
    @Override
    public List<PersonalNoTaskLable> listByLableId(Integer lableId) {
        log.info("数据库根据标签id查询个人号标签列表");
        List<PersonalNoTaskLable> personalNoTaskLableList = null;
        if(VerifyUtils.isEmpty(lableId)){
            log.info("标签id为空");
            personalNoTaskLableList = baseMapper.selectList(null);
        }else {
            log.info("根据标签id查询");
            personalNoTaskLableList = baseMapper.listByLableId(lableId);
        }
        log.info("数据库根据标签id查询个人号标签列表完成");
        return personalNoTaskLableList;
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
        EntityWrapper<PersonalNoTaskLable> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("personal_no_task_id", noTask.getId());
        baseMapper.delete(entityWrapper);
        List<PersonalNoTaskLable> noLableList = noTask.getNoLableList();
        if(!VerifyUtils.collectionIsEmpty(noLableList)){
            for (PersonalNoTaskLable personalNoTaskLable : noLableList) {
                //插入任务id
                personalNoTaskLable.setPersonalNoTaskId(noTask.getId());
                int save = baseMapper.insert(personalNoTaskLable);
                if(save != 1){
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
    public Set<Integer> listByLableNameList(List<String> lableNameList) {
        log.info("根据标签名称查询所有有此标签的任务id");
        Set<Integer> taskIdSet = new HashSet<>();
        if(!VerifyUtils.collectionIsEmpty(lableNameList)){
            for (String s : lableNameList) {
                EntityWrapper<PersonalNoTaskLable> entityWrapper = new EntityWrapper<>();
                entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
                entityWrapper.eq("lable_name", s);
                List<PersonalNoTaskLable> personalNoTaskLableList = baseMapper.selectList(entityWrapper);
                if(!VerifyUtils.collectionIsEmpty(personalNoTaskLableList)){
                    for (PersonalNoTaskLable personalNoTaskLable : personalNoTaskLableList) {
                        taskIdSet.add(personalNoTaskLable.getPersonalNoTaskId());
                    }
                }
            }
        }
        log.info("根据标签名称查询所有有此标签的任务id结束");
        return taskIdSet;
    }

    @Override
    public boolean deleteByTaskId(Integer taskId) {
        EntityWrapper<PersonalNoTaskLable> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        entityWrapper.eq("personal_no_task_id", taskId);
        return baseMapper.delete(entityWrapper)>0;
    }

}
