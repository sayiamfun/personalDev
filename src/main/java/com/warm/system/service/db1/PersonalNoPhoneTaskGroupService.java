package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoPhoneTaskGroup;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoPhoneTaskGroupService extends IService<PersonalNoPhoneTaskGroup> {

    Integer add(PersonalNoPhoneTaskGroup entity);

    Integer delete(String sql);

    List<PersonalNoPhoneTaskGroup> list(String sql);

    List<String> listString(String sql);

    PersonalNoPhoneTaskGroup getOne(String sql);

    Long getCount(String sql);
}
