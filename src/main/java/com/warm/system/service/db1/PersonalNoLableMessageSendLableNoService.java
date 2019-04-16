package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoLableMessageSend;
import com.warm.system.entity.PersonalNoLableMessageSendLableNo;
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
public interface PersonalNoLableMessageSendLableNoService extends IService<PersonalNoLableMessageSendLableNo> {

    boolean batchSave(PersonalNoLableMessageSend personalNoLableMessageSend);

    List<PersonalNoLableMessageSendLableNo> listByMessageSendId(Integer id);

    List<PersonalNoLableMessageSendLableNo> listByPersonalWxId(String username);
}
