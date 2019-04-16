package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.system.entity.PersonalNoChannel;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoChannelService extends IService<PersonalNoChannel> {

    Page<PersonalNoChannel> pageQuery(Page<PersonalNoChannel> page, Object o);

    PersonalNoChannel getByChannelName(String s);
}
