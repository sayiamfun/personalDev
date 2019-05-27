package com.warm.system.mapper;

import com.warm.entity.Sql;
import com.warm.system.entity.DetailData12i;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-05-09
 */
public interface DetailData12iMapper extends BaseMapper<DetailData12i> {

    @Select("${sql}")
    Long countBySql(Sql sql);
}
