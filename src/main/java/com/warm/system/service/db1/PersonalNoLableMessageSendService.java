package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.system.entity.PersonalNoLableMessageSend;
import com.baomidou.mybatisplus.service.IService;

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

    /*
     * 根据任务id查询任务消息
     */
    PersonalNoLableMessageSend getLableMessageById(Integer id);

    boolean insertLableMessage(PersonalNoLableMessageSend personalNoLableMessageSend);
}
