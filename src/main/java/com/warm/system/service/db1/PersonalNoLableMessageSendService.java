package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.system.entity.PersonalNoLableMessageSend;
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
public interface PersonalNoLableMessageSendService extends IService<PersonalNoLableMessageSend> {

    Page<PersonalNoLableMessageSend> pageQuery(Page<PersonalNoLableMessageSend> page, Object o);

    PersonalNoLableMessageSend getLableMessageById(Integer id);

    boolean insertLableMessage(PersonalNoLableMessageSend personalNoLableMessageSend);

    Integer add(PersonalNoLableMessageSend entity);

    Integer delete(String sql);

    List<PersonalNoLableMessageSend> list(String sql);

    List<String> listString(String sql);

    PersonalNoLableMessageSend getOne(String sql);

    Long getCount(String sql);
}
