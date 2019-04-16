package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoLableMessageSend;
import com.warm.system.entity.PersonalNoLableMessageSendContent;
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
public interface PersonalNoLableMessageSendContentService extends IService<PersonalNoLableMessageSendContent> {

    List<PersonalNoLableMessageSendContent> listByLableMessageId(Integer id);

    boolean batchSave(PersonalNoLableMessageSend personalNoLableMessageSend);
}
