package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.requre.BatchUpdateObject;
import com.warm.entity.result.LableManager;
import com.warm.system.entity.PersonalNoLable;
import com.baomidou.mybatisplus.service.IService;
import com.warm.system.entity.PersonalNoOperationStockWechatAccount;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoLableService extends IService<PersonalNoLable> {

    Page<PersonalNoLable> pageQuery(Page<PersonalNoLable> page, String lableName);

    List<LableManager> getNumData(List<PersonalNoLable> rows);

    boolean batchUpdateCategory(BatchUpdateObject batchUpdateObject);
    //*******************************************************
    Integer add(PersonalNoLable entity);

    Integer delete(String sql);

    List<PersonalNoLable> list(String sql);

    List<String> listString(String sql);

    PersonalNoLable getOne(String sql);

    Long getCount(String sql);

    List<String> listByPersonal(List<PersonalNoOperationStockWechatAccount> list);
}
