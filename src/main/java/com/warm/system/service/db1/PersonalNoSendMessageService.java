package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoSendMessage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwenjie123
 * @since 2019-05-06
 */
public interface PersonalNoSendMessageService extends IService<PersonalNoSendMessage> {

    Page<PersonalNoSendMessage> pageQuery(Page<PersonalNoSendMessage> page, String nickName);

    void deleteOne(Sql sql);

    Integer add(PersonalNoSendMessage sendMessage);
}
