package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.query.QueryPersonalData;
import com.warm.entity.result.ResultPersonalData;
import com.warm.system.entity.PersonalNoData;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoDataService extends IService<PersonalNoData> {

    ResultPersonalData getInfoByDateList(List<PersonalNoData> records);

    List<PersonalNoData> listByDate(String toString);

    boolean deleteByDate(List<PersonalNoData> list);

    PersonalNoData getByTaskNameAndTime(String taskName, String date);

    List<PersonalNoData> listAll(QueryPersonalData queryPersonalData);

    Map<String,List<String>> getDateByDateList(String flag, List<PersonalNoData> list);

    List<PersonalNoData> listAllAsc(QueryPersonalData queryPersonalData);
}
