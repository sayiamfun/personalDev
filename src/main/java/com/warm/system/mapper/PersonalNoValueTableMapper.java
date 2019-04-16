package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoValueTable;
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
public interface PersonalNoValueTableMapper extends BaseMapper<PersonalNoValueTable> {
    @Select("SELECT wx_id FROM personal_no_value_table WHERE type = #{type} and deleted=0")
    List<String> listWxIdByType(Integer type);

    @Select("SELECT id,nick_name,wx_id,type,deleted,name,value FROM personal_no_value_table WHERE type = #{type} and deleted = 0")
    List<PersonalNoValueTable> listSuperUser(Integer type);
}
