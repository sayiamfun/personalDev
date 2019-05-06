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

    boolean batchSave(PersonalNoLableMessageSend personalNoLableMessageSend);

    Integer add(PersonalNoLableMessageSendContent entity);

    Integer delete(String sql);

    List<PersonalNoLableMessageSendContent> list(String sql);

    List<String> listString(String sql);

    PersonalNoLableMessageSendContent getOne(String sql);

    Long getCount(String sql);
}
