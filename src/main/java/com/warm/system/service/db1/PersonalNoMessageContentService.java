package com.warm.system.service.db1;

import com.baomidou.mybatisplus.service.IService;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoMessageContent;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwenjie123
 * @since 2019-05-06
 */
public interface PersonalNoMessageContentService extends IService<PersonalNoMessageContent> {


    Integer add(PersonalNoMessageContent personalNoMessageContent);

    void deleteBySql(Sql sql);

    List<PersonalNoMessageContent> listBySql(Sql sql);
}
