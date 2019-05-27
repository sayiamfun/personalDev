package com.warm.system.service.db1;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoKeyword;
import com.warm.system.entity.PersonalNoTaskAndKeyword;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liwenjie123
 * @since 2019-04-28
 */
public interface PersonalNoTaskAndKeywordService extends IService<PersonalNoTaskAndKeyword> {

    Page<PersonalNoTaskAndKeyword> pageQuery(Page<PersonalNoTaskAndKeyword> page, Map<String, String> map);

    Integer add(PersonalNoKeyword keyword);

    Integer deleteBySql(Sql sql);
}
