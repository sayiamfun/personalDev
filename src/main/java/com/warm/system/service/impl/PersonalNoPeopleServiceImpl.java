package com.warm.system.service.impl;

import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.entity.requre.PeopleNumReq;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoOperationStockWechatAccount;
import com.warm.system.entity.PersonalNoPeople;
import com.warm.system.entity.PersonalNoTaskLable;
import com.warm.system.mapper.PersonalNoPeopleMapper;
import com.warm.system.service.db1.PersonalNoPeopleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoTaskLableService;
import com.warm.system.service.db2.PersonalNoOperationStockWechatAccountService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import com.warm.utils.WebConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Stream;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Service
public class PersonalNoPeopleServiceImpl extends ServiceImpl<PersonalNoPeopleMapper, PersonalNoPeople> implements PersonalNoPeopleService {

    @Autowired
    private PersonalNoPeopleMapper taskPeopleMapper;
    @Autowired
    private PersonalNoTaskLableService taskLableService;
    @Autowired
    private PersonalNoPeopleService peopleService;
    @Autowired
    private PersonalNoOperationStockWechatAccountService wechatAccountService;

    private String DBTaskLable = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_lable);
    private String DBNoPeople = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_people);
    private String DBWeChat = DB.DBAndTable(DB.OA,DB.operation_stock_wechat_account);

    @Override
    public Integer add(PersonalNoPeople entity) {
        if (VerifyUtils.isEmpty(entity.getId()))
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

    @Override
    public List<PersonalNoPeople> listByLableAndPersonal(PeopleNumReq peopleNumReq) {
        String lableIds = DaoGetSql.getIds(peopleNumReq.getLableIdList());
        String getSql = "SELECT DISTINCT personal_no_task_id FROM "+DBTaskLable+" where lable_id in "+lableIds;
        Sql sql = new Sql(getSql);
        List<String> taskIdList = taskLableService.listStringBySql(sql);
        String taskIds = DaoGetSql.getIds(taskIdList);
        List<PersonalNoPeople> peopleList = new ArrayList<>();
        for (String personalNoWxId : peopleNumReq.getNoWxIdList()) {
            getSql = DaoGetSql.getSql("SELECT * from "+DBWeChat+" where wx_id = ? and  operation_project_instance_id = ? limit 0,1",personalNoWxId, G.ms_OPERATION_PROJECT_INSTANCE_ID);
            sql.setSql(getSql);
            PersonalNoOperationStockWechatAccount wechatAccount = wechatAccountService.getBySql(sql);
            if(VerifyUtils.isEmpty(wechatAccount) || WebConst.WECHATSTATUS.equals(wechatAccount.getStatus())){
                continue;
            }
            if(VerifyUtils.isEmpty(peopleNumReq.getStartTime())){
                getSql = "SELECT * FROM "+DBNoPeople+" where personal_no_wx_id = '"+personalNoWxId+"' and personal_task_id in "+taskIds+" and deleted = 0 and flag = 2 GROUP BY personal_friend_wx_id";
            }else {
                getSql = "SELECT * FROM "+DBNoPeople+" where personal_no_wx_id = '"+personalNoWxId+"' and personal_task_id in "+taskIds+" and be_friend_time  BETWEEN '"+WebConst.getNowDate(peopleNumReq.getStartTime())+"' and '"+WebConst.getNowDate(peopleNumReq.getEndTime())+"'  and deleted = 0 and flag = 2 GROUP BY personal_friend_wx_id";
            }

            peopleList = peopleService.list(getSql);
        }
        return peopleList;
    }

    @Override
    public Map<String, List<String>> MapByPeopleList(List<PersonalNoPeople> peopleList) {
        Map<String,List<String>> map = new HashMap<>();
        for (PersonalNoPeople people : peopleList) {
            if(map.containsKey(people.getPersonalNoWxId())){
                map.get(people.getPersonalNoWxId()).add(people.getPersonalFriendWxId());
            }else {
                List<String> list = new ArrayList<>();
                list.add(people.getPersonalFriendWxId());
                map.put(people.getPersonalNoWxId(),list);
            }
        }
        return map;
    }

}
