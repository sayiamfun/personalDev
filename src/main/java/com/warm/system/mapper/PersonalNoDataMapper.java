package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoData;
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
public interface PersonalNoDataMapper extends BaseMapper<PersonalNoData> {

    //根据时间查询数据
    @Select("SELECT id,date,to_people_num,add_friends_num,join_group_num,today_keep,the_rate_of_add_friends,the_rate_of_join_group,global_conversion_rate,the_rate_of_today_keep,task_name,deleted FROM personal_no_data WHERE date = #{tostring} and deleted = 0")
    List<PersonalNoData> listByDate(String toString);
    //根据时间和任务查找对应的数据
    @Select("SELECT id,date,to_people_num,add_friends_num,join_group_num,today_keep,the_rate_of_add_friends,the_rate_of_join_group,global_conversion_rate,the_rate_of_today_keep,task_name,deleted FROM personal_no_data WHERE date = #{param1} and task_name = #{param2} and deleted = 0 LIMIT 0,1")
    PersonalNoData getByTaskNameAndTime(String date, String taskName);
}
