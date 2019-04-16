package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.system.entity.PersonalNo;
import com.warm.system.entity.PersonalNoAutoReplayNo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-04-03
 */
public interface PersonalNoAutoReplayNoService extends IService<PersonalNoAutoReplayNo> {

    Page<PersonalNoAutoReplayNo> pageQuery(Page<PersonalNoAutoReplayNo> page, String nickName);

    boolean insertInfo(PersonalNo no);
}
