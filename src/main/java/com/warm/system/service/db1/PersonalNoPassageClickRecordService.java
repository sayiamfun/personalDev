package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoPassageClickRecord;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 点击通道链接的记录 服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoPassageClickRecordService extends IService<PersonalNoPassageClickRecord> {

    Integer add(PersonalNoPassageClickRecord entity);

    Integer delete(String sql);

    List<PersonalNoPassageClickRecord> list(String sql);

    List<String> listString(String sql);

    PersonalNoPassageClickRecord getOne(String sql);

    Long getCount(String sql);
}
