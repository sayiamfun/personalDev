package com.warm.system.service.db3;

import com.warm.system.entity.PersonalNoGroupCategory;
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
public interface PersonalNoGroupCategoryService extends IService<PersonalNoGroupCategory> {

    List<PersonalNoGroupCategory> list(String sql);

    List<String> listString(String sql);

    PersonalNoGroupCategory getOne(String sql);

    Long getCount(String sql);

    //获取群类别信息
    PersonalNoGroupCategory getPersonalNoGroupCategory(String content);

    Integer add(PersonalNoGroupCategory entity);

}
