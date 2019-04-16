package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskReplyContent;
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
public interface PersonalNoTaskReplyContentService extends IService<PersonalNoTaskReplyContent> {

    boolean batchSave(PersonalNoTask noTask);

    List<PersonalNoTaskReplyContent> getListByTaskId(Integer id);

    boolean deleteByTaskId(Integer taskId);
}
