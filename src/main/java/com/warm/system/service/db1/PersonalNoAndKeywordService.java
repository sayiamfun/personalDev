package com.warm.system.service.db1;

import com.baomidou.mybatisplus.service.IService;
import com.warm.system.entity.PersonalNoAndKeyword;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-04-17
 */
public interface PersonalNoAndKeywordService extends IService<PersonalNoAndKeyword> {

    int add(PersonalNoAndKeyword entity);

    int delete(String sql);

    List<PersonalNoAndKeyword> list(String sql);

    List<String> listString(String sql);

    PersonalNoAndKeyword getOne(String sql);
}
