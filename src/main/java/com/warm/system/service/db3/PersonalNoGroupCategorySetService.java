package com.warm.system.service.db3;

import com.warm.system.entity.PersonalNoGroupCategorySet;
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
public interface PersonalNoGroupCategorySetService extends IService<PersonalNoGroupCategorySet> {


    List<PersonalNoGroupCategorySet> list(String sql);

    List<String> listString(String sql);

    PersonalNoGroupCategorySet getOne(String sql);

    Long getCount(String sql);
}
