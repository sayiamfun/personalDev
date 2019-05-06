package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.query.QueryPersonal;
import com.warm.entity.requre.GetNoEntity;
import com.warm.system.entity.PersonalNo;
import com.baomidou.mybatisplus.service.IService;

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
public interface PersonalNoService extends IService<PersonalNo> {

    Set<PersonalNo> listByLableName(Integer lableId);

    Page<PersonalNo> pageQuery(Page<PersonalNo> page, QueryPersonal searchObj);

    boolean batchUpdateCategory(List<PersonalNo> personalList, String object);

    boolean batchUpdateGroup(List<PersonalNo> personalList, String object);

    boolean updateFriends(Integer personalId);

    Map<String, Object> getPersonalByTaskId(GetNoEntity getNoEntity);

    Integer add(PersonalNo entity);

    Integer delete(String sql);

    List<PersonalNo> list(String sql);

    List<String> listString(String sql);

    PersonalNo getOne(String sql);

    Long getCount(String sql);
}
