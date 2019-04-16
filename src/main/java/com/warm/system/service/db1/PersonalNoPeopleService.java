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

    Set<String> getByNoListAndLableNameList(PeopleNumReq peopleNumReq);

    List<String> listUserWxIdByTaskIdAndPersonalWxId(Integer taskId, String username, Date startTime, Date endTime);

    List<String> listUserWxIdByPersonalWxId(String wxId, Date startTime, Date endTime);

    PersonalNoPeople getByPersonalIdAndUserId(String wxId, String username, Integer flag);

    List<PersonalNoPeople> getByPersonalWxIdAndUserName(String wxId, String nickname);

    PersonalNoPeople getByTaskIdAndUserNickName(int parseInt, String nickname);

    Integer getPeopleCountByTaskId(Integer personaNoTaskId);

    List<Integer> listIdByPersonalWxIdAndUserWxId(String username, String userWxId);

    PersonalNoPeople getByPersonalWxIdAndUserWxId(String username, String userWxId);

    PersonalNoPeople getByTaskIdAndUserWxId(int parseInt, String wxId);

    List<PersonalNoPeople> ListByTaskIdAndPersonalWxId(Integer taskId, String personalNoWxId);

    List<PersonalNoPeople> listByTaskIdAndTime(Integer id, Date date, Date datTaskDate);
}
