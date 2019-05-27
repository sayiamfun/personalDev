package com.warm.system.service.db1;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskBeginRemind;
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
public interface PersonalNoTaskBeginRemindService extends IService<PersonalNoTaskBeginRemind> {

    boolean batchSave(PersonalNoTask noTask);

    Integer add(PersonalNoTaskBeginRemind personalNoTaskBeginRemind);

    List<PersonalNoTaskBeginRemind> listBySql(Sql sql);

    boolean deleteBySql(Sql sql);
}
