package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoCategory;
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
public interface PersonalNoCategoryMapper extends BaseMapper<PersonalNoCategory> {

    @Select("select id,personal_no_category,super_id,create_time,remarks,deleted from personal_no_category where deleted = 0 order by id desc")
    List<PersonalNoCategory> listAll();
}
