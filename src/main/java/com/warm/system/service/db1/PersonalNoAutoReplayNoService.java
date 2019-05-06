package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.system.entity.PersonalNo;
import com.warm.system.entity.PersonalNoAutoReplayNo;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-04-03
 */
public interface PersonalNoAutoReplayNoService extends IService<PersonalNoAutoReplayNo> {

    Integer add(PersonalNoAutoReplayNo entity);

    Integer delete(String sql);

    List<PersonalNoAutoReplayNo> list(String sql);

    List<String> listString(String sql);

    PersonalNoAutoReplayNo getOne(String sql);

    Long getCount(String sql);
}
