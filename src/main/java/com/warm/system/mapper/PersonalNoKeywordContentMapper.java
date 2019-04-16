package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoKeywordContent;
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
public interface PersonalNoKeywordContentMapper extends BaseMapper<PersonalNoKeywordContent> {

    @Select("SELECT id,personal_no_keyword_id,content_type,content,deleted FROM `personal_no_keyword_content` WHERE personal_no_keyword_id = #{id} and deleted = 0 ")
    List<PersonalNoKeywordContent> listByKeywordId(Integer id);
    @Update("UPDATE `personal_no_keyword_content` SET deleted = 1 WHERE personal_no_keyword_id = #{id} and deleted = 0")
    void deleteByKeyWordId(Integer id);
}
