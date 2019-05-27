package com.warm.system.service.db1;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskLable;
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
public interface PersonalNoTaskLableService extends IService<PersonalNoTaskLable> {

    List<PersonalNoTaskLable> listBySql(Sql sql);

    Integer add(PersonalNoTaskLable personalNoTaskLable);

    boolean batchSave(PersonalNoTask noTask);

    List<Integer> listTaskIdsByLableNameList(List<String> lableNameList);

    boolean deleteBySql(Sql sql);

    List<String> listStringBySql(Sql sql);
}
