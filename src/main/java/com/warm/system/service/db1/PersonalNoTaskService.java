package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.query.QueryPersonalTask;
import com.warm.system.entity.PersonalNoTask;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoTaskService extends IService<PersonalNoTask> {

    int getOnGoingTaskNum();

    PersonalNoTask getTaskById(Integer taskId);

    Page<PersonalNoTask> pageQuery(Page<PersonalNoTask> page, QueryPersonalTask queryPersonalTask);

    boolean addPersonalTask(PersonalNoTask task);

    PersonalNoTask getTaskInfoById(PersonalNoTask id);

    List<Integer> listByPersonalWxId(String wxId);

    List<PersonalNoTask> listByRoadId(Integer id);

    Map<String, Object> getPersonalDataByTaskId(Integer taskId);

    List<PersonalNoTask> listByStartTimeAndEndTime(Date date, Date datTaskDate);

    PersonalNoTask getTaskByIdLess(Integer taskId);

    boolean deleteById(Integer taskId);

    boolean stopTaskById(Integer taskId);

    List<String> listAllTaskName(Object o);
}
