package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoTaskData;
import com.warm.system.mapper.PersonalNoTaskDataMapper;
import com.warm.system.service.db1.PersonalNoTaskDataService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Service
public class PersonalNoTaskDataServiceImpl extends ServiceImpl<PersonalNoTaskDataMapper, PersonalNoTaskData> implements PersonalNoTaskDataService {
    @Autowired
    private PersonalNoTaskDataMapper taskDataMapper;
    /**
     * 根据任务id查询任务数据
     * @param taskId
     * @return
     */
    @Override
    public PersonalNoTaskData getByTaskId(Integer taskId) {
        return taskDataMapper.getByTaskId(taskId);
    }
}
