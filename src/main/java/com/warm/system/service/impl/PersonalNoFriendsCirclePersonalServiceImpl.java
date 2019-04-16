package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warm.system.entity.PersonalNoFriendsCircle;
import com.warm.system.entity.PersonalNoFriendsCirclePersonal;
import com.warm.system.mapper.PersonalNoFriendsCirclePersonalMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoFriendsCirclePersonalService;
import com.warm.utils.VerifyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
public class PersonalNoFriendsCirclePersonalServiceImpl extends ServiceImpl<PersonalNoFriendsCirclePersonalMapper, PersonalNoFriendsCirclePersonal> implements PersonalNoFriendsCirclePersonalService {
    private static Log log = LogFactory.getLog(PersonalNoFriendsCirclePersonalServiceImpl.class);
    /*
     * 根据朋友圈id获得相关朋友圈个人号数量
     */
    @Override
    public List<PersonalNoFriendsCirclePersonal> listByCircleId(Integer id) {
        log.info("根据朋友圈id获得相关朋友圈个人号数量开始");
        EntityWrapper<PersonalNoFriendsCirclePersonal> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        if(VerifyUtils.isEmpty(id)){
            log.info("朋友圈id为空");
            entityWrapper.eq("friends_circle_id" , -1);
        }else {
            entityWrapper.eq("friends_circle_id" , id);
        }
        List<PersonalNoFriendsCirclePersonal> noFriendsCirclePersonals = baseMapper.selectList(entityWrapper);
        log.info("根据朋友圈id查询相关个人号数量成功，返回数据");
        return noFriendsCirclePersonals;
    }

    /**
     * 批量添加朋友圈个人号
     * @param noFriendsCircle
     * @return
     */
    @Transactional
    @Override
    public boolean batchSave(PersonalNoFriendsCircle noFriendsCircle) {
        log.info("数据库添加朋友圈个人号开始");
        log.info("根据朋友圈id删除个人号");
        EntityWrapper<PersonalNoFriendsCirclePersonal> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("friends_circle_id" , noFriendsCircle.getId());
        baseMapper.delete(entityWrapper);
        List<PersonalNoFriendsCirclePersonal> personalList = noFriendsCircle.getPersonalList();
        if(!VerifyUtils.collectionIsEmpty(personalList)){
            log.info("开始添加朋友圈个人号");
            for (PersonalNoFriendsCirclePersonal noFriendsCirclePersonal : personalList) {
                noFriendsCirclePersonal.setFriendsCircleId(noFriendsCircle.getId());
                int insert = baseMapper.insert(noFriendsCirclePersonal);
                if(insert != 1){
                    log.info("添加朋友圈个人号失败");
                    return false;
                }
            }
        }
        log.info("数据库添加朋友圈个人号成功");
        return true;
    }
}
