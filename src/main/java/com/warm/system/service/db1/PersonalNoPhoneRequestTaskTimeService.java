package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoPhoneRequestTaskTime;
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
public interface PersonalNoPhoneRequestTaskTimeService extends IService<PersonalNoPhoneRequestTaskTime> {

    Integer add(PersonalNoPhoneRequestTaskTime entity);

    Integer delete(String sql);

    List<PersonalNoPhoneRequestTaskTime> list(String sql);

    List<String> listString(String sql);

    PersonalNoPhoneRequestTaskTime getOne(String sql);

    Long getCount(String sql);
}
