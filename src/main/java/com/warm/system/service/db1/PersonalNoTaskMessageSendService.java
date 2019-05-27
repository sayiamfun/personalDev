package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.system.entity.PersonalNoPeople;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskMessageSend;
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
public interface PersonalNoTaskMessageSendService extends IService<PersonalNoTaskMessageSend> {

    boolean insertPersonalNoTaskMessage(PersonalNoTaskMessageSend personalNoTaskMessageSend, PersonalNoTask noTask, List<PersonalNoPeople> peopleList);

    Page<PersonalNoTaskMessageSend> pageQuery(Page<PersonalNoTaskMessageSend> page, Object o);

    PersonalNoTaskMessageSend getTaskMessageById(Long id);

}
