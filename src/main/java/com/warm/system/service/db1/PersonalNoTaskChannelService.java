package com.warm.system.service.db1;

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

    List<PersonalNoTaskChannel> getListByTaskId(Integer id);

    boolean deleteByTaskId(Integer taskId);

    PersonalNoTaskChannel selectByTaskIdAndChannelId(String roadId, Integer channelId, Integer roadOrTask);
}
