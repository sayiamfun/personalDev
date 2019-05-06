package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoOperationStockWechatAccount;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoOperationStockWechatAccountMapper extends BaseMapper<PersonalNoOperationStockWechatAccount> {

    List<PersonalNoOperationStockWechatAccount> list(@Param("sql") String sql);

    List<String> listString(@Param("sql") String sql);

    Integer add(@Param("entity") PersonalNoOperationStockWechatAccount entity);

    PersonalNoOperationStockWechatAccount getOne(@Param("sql") String sql);

    Integer updateOne(@Param("entity")PersonalNoOperationStockWechatAccount entity);

    Integer delete(@Param("sql") String sql);

    Long getCount(@Param("sql") String sql);
}
