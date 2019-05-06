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

    Integer add(PersonalNoData entity);

    Integer delete(String sql);

    List<PersonalNoData> list(String sql);

    List<String> listString(String sql);

    PersonalNoData getOne(String sql);

    Long getCount(String sql);

    ResultPersonalData getInfoByDateList(List<PersonalNoData> records);

    List<PersonalNoData> listAll(QueryPersonalData queryPersonalData);

    List<PersonalNoData> listAllAsc(QueryPersonalData queryPersonalData);

    Map<String,List<String>> getDateByDateList(String flag, List<PersonalNoData> list);
}
