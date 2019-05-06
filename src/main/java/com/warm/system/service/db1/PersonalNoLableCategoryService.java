package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.system.entity.PersonalNoLableCategory;
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
public interface PersonalNoLableCategoryService extends IService<PersonalNoLableCategory> {

    Page<PersonalNoLableCategory> pageList(Page<PersonalNoLableCategory> page, String name);

    List<PersonalNoLableCategory> getInfo(List<PersonalNoLableCategory> personalList);

    Integer add(PersonalNoLableCategory entity);

    Integer delete(String sql);

    List<PersonalNoLableCategory> list(String sql);

    List<String> listString(String sql);

    PersonalNoLableCategory getOne(String sql);

    Long getCount(String sql);
}
