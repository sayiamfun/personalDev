package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.entity.requre.QueryRoad;
import com.warm.system.entity.PersonalNoRoad;
import com.warm.system.mapper.PersonalNoRoadMapper;
import com.warm.system.service.db1.PersonalNoRoadService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import com.warm.utils.WebConst;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Service
public class PersonalNoRoadServiceImpl extends ServiceImpl<PersonalNoRoadMapper, PersonalNoRoad> implements PersonalNoRoadService {
    private static Log log = LogFactory.getLog(PersonalNoRoadServiceImpl.class);
    @Autowired
    private PersonalNoRoadMapper roadMapper;
    private String DBRoad = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_road);


    /**
     * 根据通道id查找任务列表
     * @param page
     * @param queryRoad
     */
    @Override
    public Page<PersonalNoRoad> pageQuery(Page<PersonalNoRoad> page, QueryRoad queryRoad) {
        log.info("开始处理查询条件");
        String roadName = queryRoad.getRoadName();
        Date beginTime = queryRoad.getBeginTime();
        Date endTime = queryRoad.getEndTime();
        StringBuffer temp = new StringBuffer();
        boolean F = true;
        if(!VerifyUtils.isEmpty(roadName)){
            temp = DaoGetSql.getTempSql(temp, F);
            temp.append(" road_name like '%"+roadName+"%' ");
            F = true;
        }
        if(!VerifyUtils.isEmpty(beginTime)){
            temp = DaoGetSql.getTempSql(temp, F);
            temp.append(" road_create_time > '"+ WebConst.getNowDate(beginTime) +"' ");
            F = true;
        }
        if(!VerifyUtils.isEmpty(endTime)){
            temp = DaoGetSql.getTempSql(temp, F);
            temp.append(" road_create_time < '"+ WebConst.getNowDate(endTime) +"' ");
        }
        Sql sql = new Sql("SELECT count(*) FROM " + DBRoad + " where deleted = 0 " + temp.toString());
        Long count = roadMapper.countBySql(sql);
        page.setTotal(count.intValue());
        temp.append(" order by id desc");
        sql.setSql("SELECT * FROM "+DBRoad+" where deleted = 0 "+temp.toString());
        List<PersonalNoRoad> personalNoRoads = roadMapper.listBySql(sql);
        page.setRecords(personalNoRoads);
        return page;
    }

    @Override
    public PersonalNoRoad getBySql(Sql sql) {
        return roadMapper.getBySql(sql);
    }

    /**
     * 添加或修改通道
     * @param road
     * @return
     */
    @Override
    public boolean add(PersonalNoRoad road) {
        log.info("判断是添加操作还是更新操作");
        boolean b = false;
        if(VerifyUtils.isEmpty(road.getId())){
            log.info("添加操作");
            road.setRoadCreateTime(new Date());
            b = roadMapper.add(road)>0;
        }else {
            log.info("更新操作");
            b = roadMapper.updateOne(road)>0;
        }
        return b;
    }
}
