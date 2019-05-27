package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoOperationStockWechatAccount;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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

    Integer add(@Param("entity") PersonalNoOperationStockWechatAccount entity);

    Integer updateOne(@Param("entity")PersonalNoOperationStockWechatAccount entity);

    @Select("${sql}")
    PersonalNoOperationStockWechatAccount getBySql(Sql sql);

    @Select("${sql}")
    List<PersonalNoOperationStockWechatAccount> listBySql(Sql sql);

    @Select("${sql}")
    Long countBySql(Sql sql);
}
