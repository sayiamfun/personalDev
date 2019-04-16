package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoValueTable;
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
public interface PersonalNoValueTableService extends IService<PersonalNoValueTable> {

    List<String> listWxIdByType(Integer type);

    List<PersonalNoValueTable> listSuperUser(Integer type);

    boolean insert(PersonalNoValueTable valueTable);
}
