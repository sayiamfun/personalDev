package com.warm.system.service.db2;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.Sql;
import com.warm.entity.query.QueryPersonal;
import com.warm.entity.requre.GetNoEntity;
import com.warm.system.entity.PersonalNoOperationStockWechatAccount;
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
public interface PersonalNoOperationStockWechatAccountService extends IService<PersonalNoOperationStockWechatAccount> {

    Integer add(PersonalNoOperationStockWechatAccount entity);

    Map<String, Object> getPersonalByTaskId(GetNoEntity getNoEntity);

    List<PersonalNoOperationStockWechatAccount> listBySql(Sql sql);

    Page<PersonalNoOperationStockWechatAccount> pageQuery(Page<PersonalNoOperationStockWechatAccount> page, QueryPersonal searchObj);

    PersonalNoOperationStockWechatAccount getBySql(Sql sql);
}
