package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskPersonal;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoTaskPersonalService extends IService<PersonalNoTaskPersonal> {

    List<PersonalNoTaskPersonal> listByTaskId(Integer taskId);

    List<PersonalNoTaskPersonal> listByPersonalId(String personalWxId);

    PersonalNoTaskPersonal getByTaskIdAndPersonalWxId(Integer integer, String personalWxId);

    boolean deleteByTaskId(Integer taskId);

    boolean batchSave(PersonalNoTask noTask);
}
