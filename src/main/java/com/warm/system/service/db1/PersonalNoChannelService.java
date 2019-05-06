package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.system.entity.PersonalNoChannel;
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
public interface PersonalNoChannelService extends IService<PersonalNoChannel> {

    Integer add(PersonalNoChannel entity);

    Integer delete(String sql);

    List<PersonalNoChannel> list(String sql);

    List<String> listString(String sql);

    PersonalNoChannel getOne(String sql);

    Long getCount(String sql);
}
