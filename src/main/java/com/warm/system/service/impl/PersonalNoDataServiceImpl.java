package com.warm.system.service.impl;

import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.entity.query.QueryPersonalData;
import com.warm.entity.result.ResultPersonalData;
import com.warm.entity.result.ResultPersonalDataWithTask;
import com.warm.entity.result.ResultPersonalDataWithTime;
import com.warm.entity.robot.PersonNoEnterGroupQueryRequestInfo;
import com.warm.entity.robot.ResponseInfo;
import com.warm.system.entity.*;
import com.warm.system.mapper.PersonalNoDataMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.*;
import com.warm.utils.*;
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
    @Autowired
    private ShortUrlService shortUrlService;
    @Autowired
    private DetailData12iService detailData12iService;
    @Autowired
    private PersonalNoPeopleService personalNoPeopleService;
    @Autowired
    private PersonalNoTaskService noTaskService;
    @Autowired
    private PersonalNoTaskChannelService taskChannelService;
    @Autowired
    private PersonalNoTaskReplyContentService taskReplyContentService;

    private String ZCDBTask = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task);
    private String ZCDBPersonalPeople = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_people);
    private String ZCDBDatail12 = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.detail_data_12i);
    private String ZCDBTaskChannel = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_channel);
    private String ZCDBTaskReplyContent = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_reply_content);
    private String DBShortUrl = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.short_url);
    private String DBPersonalData = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_data);

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
//        List<String> noTaskName = queryPersonalData.getNoTaskName();
//        //渠道暂时未启用
//        List<Integer> personalnoChannelName = queryPersonalData.getPersonalnoChannelName();
//        Date startTime = queryPersonalData.getStartTime();
//        Date endTime = queryPersonalData.getEndTime();
//        StringBuffer temp = new StringBuffer("select * from "+ DBPersonalData);
//        boolean F = false;
//        if(!VerifyUtils.collectionIsEmpty(noTaskName)){
//            String names = DaoGetSql.getIds(noTaskName);
//            names.replaceAll(" ","");
//            log.info("根据任务名称查询");
//            temp.append(" where task_name in "+names);
//            F = true;
//        }
//        if(!VerifyUtils.isEmpty(startTime) && !VerifyUtils.isEmpty(endTime)){
//            log.info("根据时间查询");
//            if(true){
//                temp.append(" and date between '"+WebConst.getNowDate(startTime) + "' and '"+WebConst.getNowDate(endTime)+"'");
//            }else {
//                temp.append(" where date between '"+WebConst.getNowDate(startTime) + "' and '"+WebConst.getNowDate(endTime)+"'");
//            }
//        }
//        if(flag ==1) {
//            temp.append(" order by date desc");
//        }else {
//            temp.append(" order by date asc");
//        }
//        List<PersonalNoData> personalNoData = dataMapper.list(temp.toString());
//        return personalNoData;
        return null;
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
//        for (PersonalNoData record : list) {
//            ResultPersonalDataWithTime dataWithTime = new ResultPersonalDataWithTime();
//            log.info("处理时间数据");
//            if(resultPersonalDataWithTimeMap.containsKey(WebConst.getNowDateNotHour(record.getDate()))){
//                log.info("已经存在此时间，修改数量");
//                dataWithTime = resultPersonalDataWithTimeMap.get(WebConst.getNowDateNotHour(record.getDate()));
//                dataWithTime.setToPeopleNum(dataWithTime.getToPeopleNum() + record.getToPeopleNum());
//                dataWithTime.setAddFriendsNum(dataWithTime.getAddFriendsNum() + record.getAddFriendsNum());
//                dataWithTime.setJoinGroupNum(dataWithTime.getJoinGroupNum() + record.getJoinGroupNum());
//                dataWithTime.setTodayKeep(dataWithTime.getTodayKeep() + record.getTodayKeep());
//            }else {
//                log.info("不存在此时间，加入到map");
//                dataWithTime.setDate(WebConst.getNowDateNotHour(record.getDate()));
//                dataWithTime.setAddFriendsNum(record.getAddFriendsNum());
//                dataWithTime.setJoinGroupNum(record.getJoinGroupNum());
//                dataWithTime.setTodayKeep(record.getTodayKeep());
//                dataWithTime.setToPeopleNum(record.getToPeopleNum());
//            }
//            dataWithTime.setTheRateOfAddFriends(WebConst.div(dataWithTime.getToPeopleNum(), dataWithTime.getAddFriendsNum(), 2));
//            resultPersonalDataWithTimeMap.put(WebConst.getNowDateNotHour(record.getDate()), dataWithTime);
//        }
//        List<String> dataList = new ArrayList<>();
//        List<String> dateList = new ArrayList<>();
//        if("0".equals(flag)){
//            for (Map.Entry<String, ResultPersonalDataWithTime> stringResultPersonalDataWithTimeEntry : resultPersonalDataWithTimeMap.entrySet()) {
//                dataList.add(stringResultPersonalDataWithTimeEntry.getValue().getToPeopleNum().toString());
//                dateList.add(stringResultPersonalDataWithTimeEntry.getValue().getDate());
//            }
//        }else if("1".equals(flag)){
//            for (Map.Entry<String, ResultPersonalDataWithTime> stringResultPersonalDataWithTimeEntry : resultPersonalDataWithTimeMap.entrySet()) {
//                dataList.add(stringResultPersonalDataWithTimeEntry.getValue().getAddFriendsNum().toString());
//                dateList.add(stringResultPersonalDataWithTimeEntry.getValue().getDate());
//            }
//        }else if("2".equals(flag)){
//            for (Map.Entry<String, ResultPersonalDataWithTime> stringResultPersonalDataWithTimeEntry : resultPersonalDataWithTimeMap.entrySet()) {
//                dataList.add(stringResultPersonalDataWithTimeEntry.getValue().getJoinGroupNum().toString());
//                dateList.add(stringResultPersonalDataWithTimeEntry.getValue().getDate());
//            }
//        }else {
//            for (Map.Entry<String, ResultPersonalDataWithTime> stringResultPersonalDataWithTimeEntry : resultPersonalDataWithTimeMap.entrySet()) {
//                dataList.add(stringResultPersonalDataWithTimeEntry.getValue().getTodayKeep().toString());
//                dateList.add(stringResultPersonalDataWithTimeEntry.getValue().getDate());
//            }
//        }
//        map.put("dataList", dataList);
//        map.put("dateList", dateList);
        map.put("dataList", new ArrayList<>());
        map.put("dateList", new ArrayList<>());
        return map;
    }

    @Override
    public ResultPersonalData getAllData(QueryPersonalData queryPersonalData) {
        ResultPersonalData resultPersonalData = new ResultPersonalData();
        log.info("总推送人");
        Integer toPeopleCount = 0;
        log.info("总添加好友数");
        Integer addFriendsCount = 0;
        Integer delFriendsCount = 0;
        resultPersonalData.setJoinGroupNum(0);
        resultPersonalData.setTodayKeep(0);
        StringBuffer temp = new StringBuffer("select * from "+ZCDBTask+" where deleted = 0");
        if(!VerifyUtils.isEmpty(queryPersonalData)){
            boolean F = true;
            if(!VerifyUtils.collectionIsEmpty(queryPersonalData.getNoTaskName())){
                String names = DaoGetSql.getIds(queryPersonalData.getNoTaskName());
                temp = DaoGetSql.getTempSql(temp, F);
                temp.append(" task_name in "+names);
            }
        }
        temp.append(" order by id desc");
        Sql sql = new Sql(temp.toString());
        List<PersonalNoTask> noTaskList = noTaskService.listByQueryPersonalData(sql);
        Set<Integer> shortUrlSet = new HashSet<>();
        Iterator<PersonalNoTask> iterator = noTaskList.iterator();
        List<Integer> shortUrlIdList = new ArrayList<>();
        Integer shortUrlid = null;
        String getSql = null;
        while (iterator.hasNext()) {
            log.info("查询任务对应的渠道的短链");
            PersonalNoTask personalNoTask = iterator.next();
            if(!VerifyUtils.collectionIsEmpty(queryPersonalData.getPersonalnoChannelName())){
                List<Integer> personalnoChannelId = queryPersonalData.getPersonalnoChannelName();
                for (Integer integer : personalnoChannelId) {
                    getSql = DaoGetSql.getSql("SELECT id from "+ZCDBTaskChannel+" where personal_no_task_id = ? and channel_id = ? and deleted = 0",personalNoTask.getId(), integer);
                    sql.setSql(getSql);
                    Integer bySql = taskChannelService.getIdBySql(sql);
                    getSql = DaoGetSql.getSql("SELECT id from "+DBShortUrl+" where task_id = ? and channel_id = ? limit 0,1",personalNoTask.getId(),bySql);
                    sql.setSql(getSql);
                    shortUrlid = VerifyUtils.isEmpty(shortUrlService.getBySql(sql))?-1:shortUrlService.getBySql(sql).getId();
                    shortUrlSet.add(shortUrlid);
                    if(!shortUrlIdList.contains(shortUrlid) && !VerifyUtils.isEmpty(shortUrlid)){
                        shortUrlIdList.add(shortUrlid);
                    }
                }
                resultPersonalData = getGroupNum(personalNoTask, shortUrlIdList, queryPersonalData, resultPersonalData);
            }else {
                log.info("查询所有渠道对应的短链");
                getSql = DaoGetSql.getSql("SELECT * FROM " + ZCDBTaskChannel + " where personal_no_task_id = ? and deleted = 0", personalNoTask.getId());
                sql.setSql(getSql);
                List<PersonalNoTaskChannel> taskChannelList = taskChannelService.listBySql(sql);
                getSql = DaoGetSql.getSql("SELECT id from "+DBShortUrl+" where task_id = ? and channel_id = ? limit 0,1",personalNoTask.getId(),0);
                sql.setSql(getSql);
                shortUrlid = VerifyUtils.isEmpty(shortUrlService.getBySql(sql))?-1:shortUrlService.getBySql(sql).getId();
                shortUrlIdList = new ArrayList<>();
                shortUrlSet.add(shortUrlid);
                if(!shortUrlIdList.contains(shortUrlid) && !VerifyUtils.isEmpty(shortUrlid)){
                    shortUrlIdList.add(shortUrlid);
                }
                for (PersonalNoTaskChannel personalNoTaskChannel : taskChannelList) {
                    getSql = DaoGetSql.getSql("SELECT id from "+DBShortUrl+" where task_id = ? and channel_id = ? limit 0,1",personalNoTask.getId(), personalNoTaskChannel.getId());
                    sql.setSql(getSql);
                    shortUrlid = VerifyUtils.isEmpty(shortUrlService.getBySql(sql))?-1:shortUrlService.getBySql(sql).getId();
                    shortUrlSet.add(shortUrlid);
                    if(!shortUrlIdList.contains(shortUrlid) && !VerifyUtils.isEmpty(shortUrlid)){
                        shortUrlIdList.add(shortUrlid);
                    }
                }
                resultPersonalData = getGroupNum(personalNoTask, shortUrlIdList, queryPersonalData, resultPersonalData);
            }
        }
        for (Integer shortUrlId : shortUrlSet) {
            if(VerifyUtils.isEmpty(shortUrlId)){
                continue;
            }
            StringBuffer getTCountsql = new StringBuffer("SELECT count(*) from "+ZCDBDatail12+" where short_url_id = "+shortUrlId+" and is_new = 1 ");
            if(!VerifyUtils.isEmpty(queryPersonalData.getStartTime())){
                getTCountsql = DaoGetSql.getTempSql(getTCountsql, true);
                getTCountsql.append(" create_time > '"+WebConst.getNowDate(queryPersonalData.getStartTime())+"'");
            }
            if(!VerifyUtils.isEmpty(queryPersonalData.getEndTime())){
                getTCountsql = DaoGetSql.getTempSql(getTCountsql, true);
                getTCountsql.append(" create_time < '"+WebConst.getNowDate(queryPersonalData.getEndTime())+"'");
            }
            sql.setSql(getTCountsql.toString());
            Long count = detailData12iService.countBySql(sql);
            toPeopleCount += count.intValue();
            StringBuffer getCountsql = new StringBuffer("SELECT count(*) from "+ZCDBPersonalPeople+" where channel_id = "+shortUrlId+" and deleted = 0 and personal_friend_wx_id is not null");
            StringBuffer getdeleteCountsql = new StringBuffer("SELECT count(*) from "+ZCDBPersonalPeople+" where channel_id = "+shortUrlId+" and personal_friend_wx_id is null and deleted = 1");
            if(!VerifyUtils.isEmpty(queryPersonalData.getStartTime())){
                getCountsql = DaoGetSql.getTempSql(getCountsql, true);
                getCountsql.append(" be_friend_time > '"+WebConst.getNowDate(queryPersonalData.getStartTime())+"'");
                getdeleteCountsql = DaoGetSql.getTempSql(getdeleteCountsql,true);
                getdeleteCountsql.append(" be_friend_time > '"+WebConst.getNowDate(queryPersonalData.getStartTime())+"'");
            }
            if(!VerifyUtils.isEmpty(queryPersonalData.getEndTime())){
                getCountsql = DaoGetSql.getTempSql(getCountsql, true);
                getCountsql.append(" be_friend_time < '"+WebConst.getNowDate(queryPersonalData.getEndTime())+"'");
                getdeleteCountsql = DaoGetSql.getTempSql(getdeleteCountsql,true);
                getdeleteCountsql.append(" be_friend_time < '"+WebConst.getNowDate(queryPersonalData.getEndTime())+"'");
            }
            count = personalNoPeopleService.getCount(getCountsql.toString());
            Long deleteCount = personalNoPeopleService.getCount(getdeleteCountsql.toString());
            addFriendsCount += count.intValue();
            delFriendsCount += deleteCount.intValue();

        }
        resultPersonalData.setToPeopleNum(toPeopleCount);
        resultPersonalData.setAddFriendsNum(addFriendsCount);
        resultPersonalData.setKeepFriendsNum(addFriendsCount - delFriendsCount);
        log.info("加好友率");
        if(resultPersonalData.getToPeopleNum() != 0){
            Double div = WebConst.div(addFriendsCount.doubleValue(), toPeopleCount.doubleValue(), 4);
            resultPersonalData.setTheRateOfAddFriends(div*100);
        }
        log.info("用户入群率");
        if(resultPersonalData.getAddFriendsNum() != 0){
            Double div = WebConst.div(resultPersonalData.getJoinGroupNum().doubleValue(), addFriendsCount.doubleValue(), 4);
            resultPersonalData.setTheRateOfJoinGroup(div*100);
        }
        log.info("当日留存率");
        if(resultPersonalData.getToPeopleNum() != 0){
            Double div = WebConst.div(resultPersonalData.getTodayKeep().doubleValue(), toPeopleCount.doubleValue(), 4);
            resultPersonalData.setTheRateOfTodayKeep(div*100);
        }
        log.info("全局转化率");
        if(resultPersonalData.getJoinGroupNum() != 0){
            Double div = WebConst.div(resultPersonalData.getTodayKeep().doubleValue(), resultPersonalData.getJoinGroupNum().doubleValue(), 4);
            resultPersonalData.setGlobalConversionRate(div*100);
        }
        return resultPersonalData;
    }

    private ResultPersonalData getGroupNum(PersonalNoTask personalNoTask,List<Integer> shortUrlIdList,QueryPersonalData queryPersonalData, ResultPersonalData resultPersonalData){
        log.info("总入群数");
        Integer joinGroupCount = 0;
        log.info("今日群留存数");
        Integer todayKeep = 0;
        String getsql = DaoGetSql.getSql("SELECT * from "+ZCDBTaskReplyContent+" where personal_no_task_id = ? and content_type = '邀请入群' limit 0,1",personalNoTask.getId());
        Sql sql = new Sql(getsql);
        PersonalNoTaskReplyContent personalNoTaskReplyContent = taskReplyContentService.getBySql(sql);
        if(!VerifyUtils.isEmpty(personalNoTaskReplyContent) && !VerifyUtils.isEmpty(personalNoTaskReplyContent.getContent())) {
            List<Integer> groupCategoryIdList = new ArrayList<>();
            String[] split = personalNoTaskReplyContent.getContent().split("/");
            if(split.length>1){
                if(!groupCategoryIdList.contains(Integer.parseInt(split[1]))) {
                    groupCategoryIdList.add(Integer.parseInt(split[1]));
                }
            }
            log.info("查询对应的入群人数和群留存人数");
            PersonNoEnterGroupQueryRequestInfo personNoEnterGroupQueryRequestInfo = new PersonNoEnterGroupQueryRequestInfo();
            personNoEnterGroupQueryRequestInfo.setTask_id(personalNoTask.getId());
            personNoEnterGroupQueryRequestInfo.setChannel_id_list(shortUrlIdList);
            personNoEnterGroupQueryRequestInfo.setGroup_category_id_list(groupCategoryIdList);
            if (!VerifyUtils.isEmpty(queryPersonalData.getStartTime())) {
                personNoEnterGroupQueryRequestInfo.setStart_time(queryPersonalData.getStartTime());
            }
            if (!VerifyUtils.isEmpty(queryPersonalData.getEndTime())) {
                personNoEnterGroupQueryRequestInfo.setStart_time(queryPersonalData.getEndTime());
            }
            List<PersonNoEnterGroupQueryRequestInfo>  personNoEnterGroupQueryRequestInfoList = new ArrayList<>();
            personNoEnterGroupQueryRequestInfoList.add(personNoEnterGroupQueryRequestInfo);
            String s = HttpClientUtil.sendPost("http://youyoudk.cn/SpringBootService/queryGroupCategoryMember", JsonObjectUtils.objectToJson(personNoEnterGroupQueryRequestInfoList));
            if(!VerifyUtils.isEmpty(s)) {
                ResponseInfo responseInfo = JsonObjectUtils.jsonToPojo(s, ResponseInfo.class);
                if(responseInfo.code == 0){
                    List<Map<String,Integer>> personNoEnterGroupQueryResponseInfoList = (List<Map<String,Integer>>)responseInfo.data;
                    if(!VerifyUtils.collectionIsEmpty(personNoEnterGroupQueryResponseInfoList) && !VerifyUtils.isEmpty(personNoEnterGroupQueryResponseInfoList.get(0))) {
                        System.err.println(personNoEnterGroupQueryResponseInfoList.get(0));
                        Map<String, Integer> stringStringMap = personNoEnterGroupQueryResponseInfoList.get(0);
                        Set<Map.Entry<String, Integer>> entries = stringStringMap.entrySet();
                        for (Map.Entry<String, Integer> entry : entries) {
                            if("enter_group_group_member_count".equals(entry.getKey())){
                                joinGroupCount += entry.getValue();
                            }else if("enter_group_group_member_count_resverd".equals(entry.getKey())){
                                todayKeep += entry.getValue();
                            }
                        }
                    }
                }
            }
        }
        resultPersonalData.setJoinGroupNum(resultPersonalData.getJoinGroupNum()+joinGroupCount);
        resultPersonalData.setTodayKeep(resultPersonalData.getTodayKeep()+todayKeep);
        return resultPersonalData;
    }

}
