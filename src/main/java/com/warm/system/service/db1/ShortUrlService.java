package com.warm.system.service.db1;

import com.warm.entity.Sql;
import com.warm.entity.result.ResultPersonalData;
import com.warm.system.entity.ShortUrl;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-05-09
 */
public interface ShortUrlService extends IService<ShortUrl> {

    Integer add(ShortUrl shortUrl);

    ShortUrl getBySql(Sql sql);
}
