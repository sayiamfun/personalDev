package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.Sql;
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


    /*
     *  添加任务到数据库
     */
    boolean addPersonalTask(PersonalNoTask task);

    /*
         * 条件分页查询个人号任务
                task.setRecommendedReasons(temp.toString());
         */
    Page<PersonalNoTask> pageQuery(Page<PersonalNoTask> page, QueryPersonalTask queryPersonalTask);

    /*
     * 查询所有正在进行的并且相关个人号数量不为0的任务数量
     */
    int getOnGoingTaskNum();

    /*
     * 根据任务id查询任务及其相关信息
     */
    PersonalNoTask getTaskById(Integer taskId);

    PersonalNoTask getTaskInfoById(PersonalNoTask byId);

    Map<String, Object> getPersonalDataByTaskId(Integer taskId);

    PersonalNoTask getTaskByIdLess(Integer taskId);

    @Transactional
    boolean deleteById(Integer taskId);

    List<PersonalNoTask> listByQueryPersonalData(Sql sql);

    List<PersonalNoTask> listBySql(Sql sql);

    List<String> listtaskNamesBySql(Sql sql);

    boolean updateBySql(Sql sql);

    Long countBySql(Sql sql);

    PersonalNoTask getBySql(Sql sql);

    List<String> listStringBySql(Sql sql);
}
