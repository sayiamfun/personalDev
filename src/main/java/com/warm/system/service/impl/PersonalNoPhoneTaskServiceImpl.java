package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoPhoneTask;
import com.warm.system.mapper.PersonalNoPhoneTaskMapper;
import com.warm.system.service.db1.PersonalNoPhoneTaskService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class PersonalNoPhoneTaskServiceImpl extends ServiceImpl<PersonalNoPhoneTaskMapper, PersonalNoPhoneTask> implements PersonalNoPhoneTaskService {
    @Autowired
    private PersonalNoPhoneTaskMapper taskMapper;
    /**
     * 根据task_group_id和step查找
     * @param id
     * @param nextStep
     * @return
     */
    @Override
    public PersonalNoPhoneTask getOneBytask_group_idAndstep(Integer id, Integer nextStep) {
        return taskMapper.getByTaskGroupIdAndStep(id,nextStep);
    }

    /**
     * 根据task_group_id查找列表
     * @param id
     * @return
     */
    @Override
    public List<PersonalNoPhoneTask> listBytask_group_id(Integer id) {
        return taskMapper.getByTaskGroupId(id);
    }
    /**
     * 根据任务组id删除所有的子任务
     * @param id
     */
    @Override
    public void deleteByTaskGrouPId(Integer id) {
        taskMapper.deleteByTaskGrouPId(id);
    }
}
