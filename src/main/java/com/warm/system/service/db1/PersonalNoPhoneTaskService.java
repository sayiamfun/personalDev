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

    Integer add(PersonalNoPhoneTask entity);

    Integer delete(String sql);

    List<PersonalNoPhoneTask> list(String sql);

    List<String> listString(String sql);

    PersonalNoPhoneTask getOne(String sql);

    Long getCount(String sql);
}
