package com.warm.system.service.db1;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskChannel;
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
public interface PersonalNoTaskChannelService extends IService<PersonalNoTaskChannel> {

    boolean batchSave(PersonalNoTask noTask);

    List<Integer> listChannelIdsBySql(Sql sql);

    Integer getIdBySql(Sql sql);

    List<PersonalNoTaskChannel> listBySql(Sql sql);

    boolean deleteBySql(Sql sql);
}
