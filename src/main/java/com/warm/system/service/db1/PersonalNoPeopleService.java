package com.warm.system.service.db1;

import com.warm.entity.requre.PeopleNumReq;
import com.warm.entity.result.LableShow;
import com.warm.system.entity.PersonalNoPeople;
import com.baomidou.mybatisplus.service.IService;
import com.warm.system.entity.PersonalNoTaskLable;

import java.util.Date;
import java.util.List;
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

    Set<LableShow> listByPersonalIdAndTaskId(Set<LableShow> noSet, List<PersonalNoTaskLable> taskLableList);

    List<String> getByNoListAndLableNameList(PeopleNumReq peopleNumReq);

    Integer add(PersonalNoPeople entity);

    Integer delete(String sql);

    List<PersonalNoPeople> list(String sql);

    List<String> listString(String sql);

    PersonalNoPeople getOne(String sql);

    Long getCount(String sql);
}
