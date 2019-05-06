package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoWxUser;
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
public interface PersonalNoWxUserService extends IService<PersonalNoWxUser> {

    List<String> listByASS(int i);
}
