package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoBlacklist;
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
public interface PersonalNoBlacklistService extends IService<PersonalNoBlacklist> {

    Integer add(PersonalNoBlacklist entity);

    Integer delete(String sql);

    List<PersonalNoBlacklist> list(String sql);

    List<String> listString(String sql);

    PersonalNoBlacklist getOne(String sql);

    Long getCount(String sql);
}
