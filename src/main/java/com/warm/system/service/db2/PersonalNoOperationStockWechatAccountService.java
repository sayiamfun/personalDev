package com.warm.system.service.db2;

import com.warm.system.entity.PersonalNoOperationStockWechatAccount;
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
public interface PersonalNoOperationStockWechatAccountService extends IService<PersonalNoOperationStockWechatAccount> {

    Integer add(PersonalNoOperationStockWechatAccount entity);

    Integer delete(String sql);

    List<PersonalNoOperationStockWechatAccount> list(String sql);

    List<String> listString(String sql);

    PersonalNoOperationStockWechatAccount getOne(String sql);

    Long getCount(String sql);
}
