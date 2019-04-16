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

    List<PersonalNoGroupCategory> listBySetId(Integer setId);

    List<PersonalNoGroupCategory> listBySetIdFromQunLie01(Integer setId);

    PersonalNoGroupCategory getById(int parseInt);

    PersonalNoGroupCategory getByIdFromQunLie01(int parseInt);
}
