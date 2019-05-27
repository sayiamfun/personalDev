package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoLogInfo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-04-25
 */
public interface PersonalNoLogInfoService extends IService<PersonalNoLogInfo> {

    Integer add(PersonalNoLogInfo logInfo);
}
