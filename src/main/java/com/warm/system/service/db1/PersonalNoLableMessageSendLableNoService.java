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

    Integer add(PersonalNoLableMessageSendLableNo entity);

    Integer delete(String sql);

    List<PersonalNoLableMessageSendLableNo> list(String sql);

    List<String> listString(String sql);

    PersonalNoLableMessageSendLableNo getOne(String sql);

    Long getCount(String sql);
}
