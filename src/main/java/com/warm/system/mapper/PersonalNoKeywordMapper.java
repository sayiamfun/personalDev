package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoKeyword;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoKeywordMapper extends BaseMapper<PersonalNoKeyword> {
    @Update("UPDATE personal_no_keyword SET deleted = #{param2} where id = #{param1}")
    void onById(Integer keyWordId, Integer deleted);
    @Select("select * from personal_no_keyword where id in(1,2,3)")
    List<PersonalNoKeyword> getStart();
    @Select("select * from personal_no_keyword where id = #{keyWordId}")
    PersonalNoKeyword getById(Integer keyWordId);
}
