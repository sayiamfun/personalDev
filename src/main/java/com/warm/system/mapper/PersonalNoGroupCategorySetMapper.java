package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoGroupCategorySet;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoGroupCategorySetMapper extends BaseMapper<PersonalNoGroupCategorySet> {

    @Select("SELECT id,sname,sdescription,create_time FROM personal_no_group_category_set ORDER BY id DESC")
    List<PersonalNoGroupCategorySet> listAll();
    @Select("SELECT id,sname,sdescription,create_time FROM qunliebian_01.group_category_set ORDER BY id DESC")
    List<PersonalNoGroupCategorySet> listQunLie01All();
}
