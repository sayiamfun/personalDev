package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoTaskMessageSend;
import com.warm.system.entity.PersonalNoTaskMessageSendContent;
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
public interface PersonalNoTaskMessageSendContentService extends IService<PersonalNoTaskMessageSendContent> {

    List<PersonalNoTaskMessageSendContent> listByTaskMessageContentId(Integer id);

    boolean batchSave(PersonalNoTaskMessageSend personalNoTaskMessageSend);
}
