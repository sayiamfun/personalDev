package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoBlacklist;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoBlacklistService extends IService<PersonalNoBlacklist> {

    PersonalNoBlacklist getByWxId(String wxId);
}
