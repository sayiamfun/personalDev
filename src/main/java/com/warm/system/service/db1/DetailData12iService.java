package com.warm.system.service.db1;

import com.warm.entity.Sql;
import com.warm.system.entity.DetailData12i;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-05-09
 */
public interface DetailData12iService extends IService<DetailData12i> {

    Long countBySql(Sql sql);
}
