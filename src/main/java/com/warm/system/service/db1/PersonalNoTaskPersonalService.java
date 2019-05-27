package com.warm.system.service.db1;

import com.warm.entity.Sql;
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

    boolean batchSave(PersonalNoTask noTask);

    boolean deleteBySql(Sql sql);

    List<PersonalNoTaskPersonal> listBySql(Sql sql);

    Long countBySql(Sql sql);

    List<String> listStringBySql(Sql sql);
}
