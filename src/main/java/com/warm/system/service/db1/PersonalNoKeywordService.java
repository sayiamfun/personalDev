package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.system.entity.PersonalNoKeyword;
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
public interface PersonalNoKeywordService extends IService<PersonalNoKeyword> {

    Page<PersonalNoKeyword> pageQuery(Page<PersonalNoKeyword> page, String keyWord);

    boolean insertInfo(PersonalNoKeyword keyword);

    PersonalNoKeyword getInfoById(Integer i);

    void deleteById(Integer keyWordId);

    PersonalNoKeyword getByKeyWord(String keyword);

    void onById(Integer keyWordId);

    List<PersonalNoKeyword> getStart();
}
