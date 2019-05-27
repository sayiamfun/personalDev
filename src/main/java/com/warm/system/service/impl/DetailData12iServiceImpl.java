package com.warm.system.service.impl;

import com.warm.entity.Sql;
import com.warm.system.entity.DetailData12i;
import com.warm.system.mapper.DetailData12iMapper;
import com.warm.system.service.db1.DetailData12iService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-05-09
 */
@Service
public class DetailData12iServiceImpl extends ServiceImpl<DetailData12iMapper, DetailData12i> implements DetailData12iService {

    @Autowired
    private DetailData12iMapper data12iMapper;

    @Override
    public Long countBySql(Sql sql) {
        return data12iMapper.countBySql(sql);
    }
}
