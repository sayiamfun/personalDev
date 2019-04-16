package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.requre.QueryRoad;
import com.warm.system.entity.PersonalNoRoad;
import com.warm.system.mapper.PersonalNoRoadMapper;
import com.warm.system.service.db1.PersonalNoRoadService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.VerifyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
        EntityWrapper<PersonalNoRoad> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        if(!VerifyUtils.isEmpty(roadName)){
            entityWrapper.like("road_name", roadName);
        }
        if(!VerifyUtils.isEmpty(beginTime)){
            entityWrapper.ge("road_create_time", beginTime);
        }
        if(!VerifyUtils.isEmpty(endTime)){
            entityWrapper.le("road_create_time", endTime);
        }
        List<PersonalNoRoad> personalNoRoads = baseMapper.selectPage(page, entityWrapper);
        page.setRecords(personalNoRoads);
        return page;
    }

    /**
     * 添加或修改通道
     * @param road
     * @return
     */
    @Override
    public boolean insert(PersonalNoRoad road) {
        log.info("判断是添加操作还是更新操作");
        boolean b = false;
        if(VerifyUtils.isEmpty(road.getId())){
            log.info("添加操作");
            road.setRoadCreateTime(new Date());
            b = baseMapper.insert(road)>0;
        }else {
            log.info("更新操作");
            b = baseMapper.updateById(road)>0;
        }
        return b;
    }
}
