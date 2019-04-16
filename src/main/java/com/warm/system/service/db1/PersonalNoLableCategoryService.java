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

    List<PersonalNoLableCategory> listByName(String name);

    Page<PersonalNoLableCategory> pageList(Page<PersonalNoLableCategory> page, String name);

    List<PersonalNoLableCategory> getInfo(List<PersonalNoLableCategory> personalList);
}
