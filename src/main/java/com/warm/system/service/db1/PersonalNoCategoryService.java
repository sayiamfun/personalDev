package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoCategory;
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
public interface PersonalNoCategoryService extends IService<PersonalNoCategory> {

    Integer add(PersonalNoCategory entity);

    Integer delete(String sql);

    List<PersonalNoCategory> list(String sql);

    List<String> listString(String sql);

    PersonalNoCategory getOne(String sql);

    Long getCount(String sql);

}
