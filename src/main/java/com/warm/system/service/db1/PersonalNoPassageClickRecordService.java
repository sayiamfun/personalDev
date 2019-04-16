package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoPassageClickRecord;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;

/**
 * <p>
 * 点击通道链接的记录 服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoPassageClickRecordService extends IService<PersonalNoPassageClickRecord> {

    PersonalNoPassageClickRecord getByIpAndTime(String ipAddress, Date date);
}
