package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.requre.BatchUpdateObject;
import com.warm.entity.result.LableManager;
import com.warm.system.entity.PersonalNo;
import com.warm.system.entity.PersonalNoLable;
import com.baomidou.mybatisplus.service.IService;

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
public interface PersonalNoLableService extends IService<PersonalNoLable> {

    List<PersonalNoLable> listByCategory(String categoryName);

    Page<PersonalNoLable> pageQuery(Page<PersonalNoLable> page, String lableName);

    List<LableManager> getNumData(List<PersonalNoLable> rows);

    boolean batchUpdateCategory(BatchUpdateObject batchUpdateObject);

    List<PersonalNoLable> listByLableName(String lableName);

    Set<String> listByPersonal(List<PersonalNo> list);

    PersonalNoLable getByName(String s);
}
