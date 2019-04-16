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

    List<PersonalNoCategory> listAll();

}
