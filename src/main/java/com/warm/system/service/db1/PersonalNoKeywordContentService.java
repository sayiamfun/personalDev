package com.warm.system.service.db1;

import com.warm.system.entity.PersonalNoKeywordContent;
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
public interface PersonalNoKeywordContentService extends IService<PersonalNoKeywordContent> {

    void deleteByKeyWordId(Integer id);

    List<PersonalNoKeywordContent> listByKeywordId(Integer id);
}
