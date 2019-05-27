package com.warm.system.service.db1;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoSuperuesr;
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
public interface PersonalNoSuperuesrService extends IService<PersonalNoSuperuesr> {

    PersonalNoSuperuesr login(String superName);

    Integer add(PersonalNoSuperuesr personalNoSuperuesr);

    List<PersonalNoSuperuesr> listBySql(Sql sql);

    PersonalNoSuperuesr getBySql(Sql sql);
}
