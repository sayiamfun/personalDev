package com.warm.system.service.impl;

import com.warm.entity.DB;
import com.warm.entity.requre.PeopleNumReq;
import com.warm.entity.result.LableShow;
import com.warm.system.entity.PersonalNo;
import com.warm.system.entity.PersonalNoPeople;
import com.warm.system.entity.PersonalNoTaskLable;
import com.warm.system.mapper.PersonalNoPeopleMapper;
import com.warm.system.service.db1.PersonalNoPeopleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoTaskLableService;
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
public class PersonalNoPeopleServiceImpl extends ServiceImpl<PersonalNoPeopleMapper, PersonalNoPeople> implements PersonalNoPeopleService {

    private static Log log = LogFactory.getLog(PersonalNoPeopleServiceImpl.class);
    @Autowired
    private PersonalNoTaskLableService noTaskLableService;
    @Autowired
    private PersonalNoPeopleMapper taskPeopleMapper;
    private String ZCDBNoPeople = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_people);

    @Override
    public Integer add(PersonalNoPeople entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return taskPeopleMapper.add(entity);
        return taskPeopleMapper.updateOne(entity);
    }

    @Override
    public Integer delete(String sql) {
        return taskPeopleMapper.delete(sql);
    }

    @Override
    public List<PersonalNoPeople> list(String sql) {
        return taskPeopleMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return taskPeopleMapper.listString(sql);
    }

    @Override
    public PersonalNoPeople getOne(String sql) {
        return taskPeopleMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return taskPeopleMapper.getCount(sql);
    }
    /**
     * 根据个人号wxid和任务id查询对应的粉丝数量
     * @param noSet
     * @param taskLableList
     * @return
     */
    @Override
    public Set<LableShow> listByPersonalIdAndTaskId(Set<LableShow> noSet, List<PersonalNoTaskLable> taskLableList) {
        String sql = "";
        String sql1 = "";
        if(!VerifyUtils.collectionIsEmpty(noSet)){
            for (LableShow lableShow : noSet) {
                sql = "select count(*) from "+ZCDBNoPeople+" where personal_no_wx_id = '"+lableShow.getPersonalWxId()+"'";
                if(!VerifyUtils.collectionIsEmpty(taskLableList)){
                    for (PersonalNoTaskLable personalNoTaskLable : taskLableList) {
                        sql1 = sql + " and personal_task_id = "+personalNoTaskLable.getPersonalNoTaskId();
                        Long count = taskPeopleMapper.getCount(sql1);
                        lableShow.setPeopleNum(lableShow.getPeopleNum() + count.intValue());
                    }
                }else {
                    Long count = taskPeopleMapper.getCount(sql);
                    lableShow.setPeopleNum(lableShow.getPeopleNum() + count.intValue());
                }
            }
        }
        return noSet;
    }
    /**
     * 根据个人号集合和标签名称集合查询粉丝id集合
     * 标签查询个人号任务id集合
     * 根据个人号wxid和个人号任务呀查询任务粉丝
     * @param peopleNumReq
     * @return
     */
    @Override
    public List<String> getByNoListAndLableNameList(PeopleNumReq peopleNumReq) {
        log.info("用来存放y好友微信id");
        List<String> peopleIdSet = null;
        log.info("取得所有的任务id");
        String sql = "";
        Set<Integer> taskIdSet = noTaskLableService.listByLableNameList(peopleNumReq.getLableNameList());
        if(!VerifyUtils.isEmpty(peopleNumReq.getNoList())){
            for (PersonalNo no : peopleNumReq.getNoList()) {
                if(!VerifyUtils.collectionIsEmpty(taskIdSet)){
                    for (Integer integer : taskIdSet) {
                        sql = "select DISTINCT personal_friend_wx_id from "+ZCDBNoPeople+" where personal_no_wx_id = '"+no.getWxId()+"' and personal_task_id = "+integer+" and flag = 2";
                        if(!VerifyUtils.isEmpty(peopleNumReq.getStartTime()) && !VerifyUtils.isEmpty(peopleNumReq.getEndTime())){
                            sql += " and be_friend_time between '"+ WebConst.getNowDate(peopleNumReq.getStartTime()) +"' and '"+WebConst.getNowDate(peopleNumReq.getEndTime())+"'";
                        }
                        peopleIdSet = taskPeopleMapper.listString(sql);
                    }
                }
            }
        }
        return peopleIdSet;
    }

}
