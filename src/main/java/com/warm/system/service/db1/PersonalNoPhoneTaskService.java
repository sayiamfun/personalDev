package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoPhoneTask;
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
public interface PersonalNoPhoneTaskService extends IService<PersonalNoPhoneTask> {
    PersonalNoPhoneTask getOneBytask_group_idAndstep(Integer id, Integer nextStep);

    List<PersonalNoPhoneTask> listBytask_group_id(Integer id);

    void deleteByTaskGrouPId(Integer id);
}
