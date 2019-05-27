package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoMessage;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwenjie123
 * @since 2019-05-06
 */
public interface PersonalNoMessageService extends IService<PersonalNoMessage> {

    PersonalNoMessage getById(Integer messageId);

    Page<PersonalNoMessage> pageQuery(Page<PersonalNoMessage> page, String message);

    Integer add(PersonalNoMessage entity);

    PersonalNoMessage getByKeyWord(String keyword);

    PersonalNoMessage getInfoById(PersonalNoMessage PersonalNoMessage);

    Integer delete(String sql);

    List<PersonalNoMessage> list(String sql);

    List<String> listString(String sql);

    PersonalNoMessage getOne(String sql);

    Long getCount(String sql);

    List<PersonalNoMessage> listByMessage(String message);

    void deleteBySql(Sql sql);
}
