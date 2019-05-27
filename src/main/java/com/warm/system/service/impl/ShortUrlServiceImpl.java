package com.warm.system.service.impl;

import com.warm.entity.Sql;
import com.warm.entity.result.ResultPersonalData;
import com.warm.system.entity.ShortUrl;
import com.warm.system.mapper.ShortUrlMapper;
import com.warm.system.service.db1.ShortUrlService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-05-09
 */
@Service
public class ShortUrlServiceImpl extends ServiceImpl<ShortUrlMapper, ShortUrl> implements ShortUrlService {

    @Autowired
    private ShortUrlMapper shortUrlMapper;

    @Override
    public Integer add(ShortUrl shortUrl) {
        if(VerifyUtils.isEmpty(shortUrl.getId())){
            return shortUrlMapper.add(shortUrl);
        }
        return shortUrlMapper.updateOne(shortUrl);
    }

    @Override
    public ShortUrl getBySql(Sql sql) {
        return shortUrlMapper.getBySql(sql);
    }
}
