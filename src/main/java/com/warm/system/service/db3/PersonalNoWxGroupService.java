package com.warm.system.service.db3;

import com.warm.system.entity.PersonalNoWxGroup;
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
public interface PersonalNoWxGroupService extends IService<PersonalNoWxGroup> {

    List<PersonalNoWxGroup> list(String sql);

    List<String> listString(String sql);

    PersonalNoWxGroup getOne(String sql);

    Long getCount(String sql);
}
