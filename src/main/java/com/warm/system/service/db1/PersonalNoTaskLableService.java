package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskLable;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoTaskLableService extends IService<PersonalNoTaskLable> {

    List<PersonalNoTaskLable> listByLableId(Integer lableId);

    List<PersonalNoTaskLable> listByTaskId(Integer personalNoTaskId);

    boolean deleteByTaskId(Integer taskId);

    Set<Integer> listByLableNameList(List<String> lableNameList);

    boolean batchSave(PersonalNoTask noTask);
}
