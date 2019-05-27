package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoAccessTocken;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 微信登录验证 服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoAccessTockenService extends IService<PersonalNoAccessTocken> {

    PersonalNoAccessTocken getOne(String sql);

    void delete(String sql);

    int add(PersonalNoAccessTocken personalNoAccessTocken);
}
