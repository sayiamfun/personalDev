package com.warm.system.mapper;

import com.warm.system.entity.PersonalNoPassageClickRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 点击通道链接的记录 Mapper 接口
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoPassageClickRecordMapper extends BaseMapper<PersonalNoPassageClickRecord> {

    @Select("SELECT id,passage_id,ip,request_info,click_time FROM personal_no_passage_click_record where ip = #{param1} and click_time between #{param2} and #{param3} LIMIT 0,1")
    PersonalNoPassageClickRecord getByIpAndTime(String ipAddress, String nowDate, String nowDate1);
}
