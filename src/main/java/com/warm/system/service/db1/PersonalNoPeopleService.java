package com.warm.system.service.db1;

import com.warm.entity.Sql;
import com.warm.entity.requre.PeopleNumReq;
import com.warm.entity.result.LableShow;
import com.warm.system.entity.PersonalNoPeople;
import com.baomidou.mybatisplus.service.IService;
import com.warm.system.entity.PersonalNoTaskLable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoPeopleService extends IService<PersonalNoPeople> {

    Integer add(PersonalNoPeople entity);

    Integer delete(String sql);

    List<PersonalNoPeople> list(String sql);

    List<String> listString(String sql);

    PersonalNoPeople getOne(String sql);

    Long getCount(String sql);

    List<PersonalNoPeople> listByLableAndPersonal(PeopleNumReq peopleNumReq);

    Map<String, List<String>> MapByPeopleList(List<PersonalNoPeople> peopleList);
}
