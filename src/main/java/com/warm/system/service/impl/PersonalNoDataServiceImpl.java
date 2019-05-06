package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warm.entity.DB;
import com.warm.entity.query.QueryPersonalData;
import com.warm.entity.result.ResultPersonalData;
import com.warm.entity.result.ResultPersonalDataWithTask;
import com.warm.entity.result.ResultPersonalDataWithTime;
import com.warm.system.entity.PersonalNoData;
import com.warm.system.mapper.PersonalNoDataMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoDataService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import com.warm.utils.WebConst;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Service
public class PersonalNoDataServiceImpl extends ServiceImpl<PersonalNoDataMapper, PersonalNoData> implements PersonalNoDataService {
    private static Log log = LogFactory.getLog(PersonalNoDataServiceImpl.class);
    @Autowired
    private PersonalNoDataMapper dataMapper;

    @Override
    public Integer add(PersonalNoData entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return dataMapper.add(entity);
        return dataMapper.updateOne(entity);
    }

    @Override
    public Integer delete(String sql) {
        return dataMapper.delete(sql);
    }

    @Override
    public List<PersonalNoData> list(String sql) {
        return dataMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return dataMapper.listString(sql);
    }

    @Override
    public PersonalNoData getOne(String sql) {
        return dataMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return dataMapper.getCount(sql);
    }

    @Override
    public ResultPersonalData getInfoByDateList(List<PersonalNoData> records) {
        ResultPersonalData resultPersonalData = null;
        Map<String, ResultPersonalDataWithTime> resultPersonalDataWithTimeMap = new LinkedHashMap<>();
        Map<String, ResultPersonalDataWithTask> resultPersonalDataWithTaskMap = new HashMap<>();
        if(!VerifyUtils.collectionIsEmpty(records)){
            log.info("查询到数据，开始处理");
            for (PersonalNoData record : records) {
                ResultPersonalDataWithTime dataWithTime = new ResultPersonalDataWithTime();
                ResultPersonalDataWithTask dataWithTask = new ResultPersonalDataWithTask();
                log.info("处理时间数据");
                if(resultPersonalDataWithTimeMap.containsKey(WebConst.getNowDateNotHour(record.getDate()))){
                    log.info("已经存在此时间，修改数量");
                    dataWithTime = resultPersonalDataWithTimeMap.get(WebConst.getNowDateNotHour(record.getDate()));
                    dataWithTime.setToPeopleNum(dataWithTime.getToPeopleNum() + record.getToPeopleNum());
                    dataWithTime.setAddFriendsNum(dataWithTime.getAddFriendsNum() + record.getAddFriendsNum());
                    dataWithTime.setJoinGroupNum(dataWithTime.getJoinGroupNum() + record.getJoinGroupNum());
                    dataWithTime.setTodayKeep(dataWithTime.getTodayKeep() + record.getTodayKeep());
                }else {
                    log.info("不存在此时间，加入到map");
                    dataWithTime.setDate(WebConst.getNowDateNotHour(record.getDate()));
                    dataWithTime.setAddFriendsNum(record.getAddFriendsNum());
                    dataWithTime.setJoinGroupNum(record.getJoinGroupNum());
                    dataWithTime.setTodayKeep(record.getTodayKeep());
                    dataWithTime.setToPeopleNum(record.getToPeopleNum());
                }
                dataWithTime.setTheRateOfAddFriends(WebConst.div(dataWithTime.getToPeopleNum(), dataWithTime.getAddFriendsNum(), 2));
                log.info("处理任务数据");
                if(resultPersonalDataWithTaskMap.containsKey(record.getTaskName())){
                    log.info("已经存在此任务，修改数量");
                    dataWithTask = resultPersonalDataWithTaskMap.get(record.getTaskName());
                    dataWithTask.setToPeopleNum(dataWithTask.getToPeopleNum() + record.getToPeopleNum());
                    dataWithTask.setAddFriendsNum(dataWithTask.getAddFriendsNum() + record.getAddFriendsNum());
                    dataWithTask.setJoinGroupNum(dataWithTask.getJoinGroupNum() + dataWithTask.getJoinGroupNum());
                    dataWithTask.setTodayKeep(dataWithTask.getTodayKeep() + record.getTodayKeep());
                }else {
                    dataWithTask.setToPeopleNum(record.getToPeopleNum());
                    dataWithTask.setAddFriendsNum(record.getAddFriendsNum());
                    dataWithTask.setJoinGroupNum(record.getJoinGroupNum());
                    dataWithTask.setTodayKeep(record.getTodayKeep());
                    dataWithTask.setTaskName(record.getTaskName());
                }
                dataWithTask.setTheRateOfAddFriends(WebConst.div(dataWithTask.getToPeopleNum(), dataWithTask.getAddFriendsNum(), 2));
                resultPersonalDataWithTimeMap.put(WebConst.getNowDateNotHour(record.getDate()), dataWithTime);
                resultPersonalDataWithTaskMap.put(record.getTaskName(), dataWithTask);
                log.info("处理最外层数据");
                if(resultPersonalData == null) {
                    resultPersonalData = new ResultPersonalData();
                    resultPersonalData.setToPeopleNum(record.getToPeopleNum());
                    resultPersonalData.setAddFriendsNum(record.getAddFriendsNum());
                    resultPersonalData.setJoinGroupNum(record.getJoinGroupNum());
                    resultPersonalData.setTodayKeep(record.getTodayKeep());
                }else {
                    resultPersonalData.setToPeopleNum(resultPersonalData.getToPeopleNum() + record.getToPeopleNum());
                    resultPersonalData.setAddFriendsNum(resultPersonalData.getAddFriendsNum() + record.getAddFriendsNum());
                    resultPersonalData.setJoinGroupNum(resultPersonalData.getJoinGroupNum() + record.getJoinGroupNum());
                    resultPersonalData.setTodayKeep(resultPersonalData.getTodayKeep() + record.getTodayKeep());
                }
                resultPersonalData.setTheRateOfAddFriends(WebConst.div(resultPersonalData.getToPeopleNum(), resultPersonalData.getAddFriendsNum(), 2));
            }
            log.info("将map转换为list");
            Collection<ResultPersonalDataWithTask> values = resultPersonalDataWithTaskMap.values();
            Collection<ResultPersonalDataWithTime> values1 = resultPersonalDataWithTimeMap.values();
            resultPersonalData.setResultPersonalDataWithTaskList(new ArrayList<ResultPersonalDataWithTask>(values));
            resultPersonalData.setResultPersonalDataWithTimeList(new ArrayList<ResultPersonalDataWithTime>(values1));
        }
        return resultPersonalData;
    }

    /**
     * 根据条件分页查询个人号相关数据
     * @param queryPersonalData
     */
    @Override
    public List<PersonalNoData> listAll(QueryPersonalData queryPersonalData) {
        return getPersonalNoData(queryPersonalData,1);
    }
    @Override
    public List<PersonalNoData> listAllAsc(QueryPersonalData queryPersonalData) {
        return getPersonalNoData(queryPersonalData,0);
    }
    private List<PersonalNoData> getPersonalNoData(QueryPersonalData queryPersonalData, Integer flag) {
        log.info("开始处理数据查询条件");
        List<String> noTaskName = queryPersonalData.getNoTaskName();
        //渠道暂时未启用
        String personalnoChannelName = queryPersonalData.getPersonalnoChannelName();
        Date startTime = queryPersonalData.getStartTime();
        Date endTime = queryPersonalData.getEndTime();
        StringBuffer temp = new StringBuffer("select * from "+ DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_data));
        boolean F = false;
        if(!VerifyUtils.collectionIsEmpty(noTaskName)){
            String names = DaoGetSql.getIds(noTaskName);
            names.replaceAll(" ","");
            log.info("根据任务名称查询");
            temp.append(" where task_name in "+names);
            F = true;
        }
        if(!VerifyUtils.isEmpty(startTime) && !VerifyUtils.isEmpty(endTime)){
            log.info("根据时间查询");
            if(true){
                temp.append(" and date between '"+WebConst.getNowDate(startTime) + "' and '"+WebConst.getNowDate(endTime)+"'");
            }else {
                temp.append(" where date between '"+WebConst.getNowDate(startTime) + "' and '"+WebConst.getNowDate(endTime)+"'");
            }
        }
        if(flag ==1) {
            temp.append(" order by date desc");
        }else {
            temp.append(" order by date asc");
        }
        List<PersonalNoData> personalNoData = dataMapper.list(temp.toString());
        return personalNoData;
    }

    /**
     * 查询数据列表
     * @param flag
     * @param list
     * @return
     */
    @Override
    public Map<String,List<String>> getDateByDateList(String flag, List<PersonalNoData> list) {
        Map<String,List<String>> map = new HashMap<>();
        Map<String, ResultPersonalDataWithTime> resultPersonalDataWithTimeMap = new LinkedHashMap<>();
        for (PersonalNoData record : list) {
            ResultPersonalDataWithTime dataWithTime = new ResultPersonalDataWithTime();
            log.info("处理时间数据");
            if(resultPersonalDataWithTimeMap.containsKey(WebConst.getNowDateNotHour(record.getDate()))){
                log.info("已经存在此时间，修改数量");
                dataWithTime = resultPersonalDataWithTimeMap.get(WebConst.getNowDateNotHour(record.getDate()));
                dataWithTime.setToPeopleNum(dataWithTime.getToPeopleNum() + record.getToPeopleNum());
                dataWithTime.setAddFriendsNum(dataWithTime.getAddFriendsNum() + record.getAddFriendsNum());
                dataWithTime.setJoinGroupNum(dataWithTime.getJoinGroupNum() + record.getJoinGroupNum());
                dataWithTime.setTodayKeep(dataWithTime.getTodayKeep() + record.getTodayKeep());
            }else {
                log.info("不存在此时间，加入到map");
                dataWithTime.setDate(WebConst.getNowDateNotHour(record.getDate()));
                dataWithTime.setAddFriendsNum(record.getAddFriendsNum());
                dataWithTime.setJoinGroupNum(record.getJoinGroupNum());
                dataWithTime.setTodayKeep(record.getTodayKeep());
                dataWithTime.setToPeopleNum(record.getToPeopleNum());
            }
            dataWithTime.setTheRateOfAddFriends(WebConst.div(dataWithTime.getToPeopleNum(), dataWithTime.getAddFriendsNum(), 2));
            resultPersonalDataWithTimeMap.put(WebConst.getNowDateNotHour(record.getDate()), dataWithTime);
        }
        List<String> dataList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        if("0".equals(flag)){
            for (Map.Entry<String, ResultPersonalDataWithTime> stringResultPersonalDataWithTimeEntry : resultPersonalDataWithTimeMap.entrySet()) {
                dataList.add(stringResultPersonalDataWithTimeEntry.getValue().getToPeopleNum().toString());
                dateList.add(stringResultPersonalDataWithTimeEntry.getValue().getDate());
            }
        }else if("1".equals(flag)){
            for (Map.Entry<String, ResultPersonalDataWithTime> stringResultPersonalDataWithTimeEntry : resultPersonalDataWithTimeMap.entrySet()) {
                dataList.add(stringResultPersonalDataWithTimeEntry.getValue().getAddFriendsNum().toString());
                dateList.add(stringResultPersonalDataWithTimeEntry.getValue().getDate());
            }
        }else if("2".equals(flag)){
            for (Map.Entry<String, ResultPersonalDataWithTime> stringResultPersonalDataWithTimeEntry : resultPersonalDataWithTimeMap.entrySet()) {
                dataList.add(stringResultPersonalDataWithTimeEntry.getValue().getJoinGroupNum().toString());
                dateList.add(stringResultPersonalDataWithTimeEntry.getValue().getDate());
            }
        }else {
            for (Map.Entry<String, ResultPersonalDataWithTime> stringResultPersonalDataWithTimeEntry : resultPersonalDataWithTimeMap.entrySet()) {
                dataList.add(stringResultPersonalDataWithTimeEntry.getValue().getTodayKeep().toString());
                dateList.add(stringResultPersonalDataWithTimeEntry.getValue().getDate());
            }
        }
        map.put("dataList", dataList);
        map.put("dateList", dateList);
        return map;
    }

}
