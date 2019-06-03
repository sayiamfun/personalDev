package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoFriendsCircle;
import com.warm.system.entity.PersonalNoFriendsCirclePersonal;
import com.warm.system.mapper.PersonalNoFriendsCirclePersonalMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoFriendsCirclePersonalService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private PersonalNoFriendsCirclePersonalMapper noFriendsCirclePersonalMapper;

    private String DBFriendsCirclePersonal = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_friends_circle_personal);

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
        String sql = DaoGetSql.getSql("update " + DBFriendsCirclePersonal + " set deleted = 1 where friends_circle_id = ?", noFriendsCircle.getId());
        noFriendsCirclePersonalMapper.deleteBySql(new Sql(sql));
        List<PersonalNoFriendsCirclePersonal> personalList = noFriendsCircle.getPersonalList();
        if(!VerifyUtils.collectionIsEmpty(personalList)){
            log.info("开始添加朋友圈个人号");
            for (PersonalNoFriendsCirclePersonal noFriendsCirclePersonal : personalList) {
                noFriendsCirclePersonal.setId(null);
                noFriendsCirclePersonal.setFriendsCircleId(noFriendsCircle.getId());
                noFriendsCirclePersonal.setDb(DBFriendsCirclePersonal);
                noFriendsCirclePersonal.setDeleted(0);
                int insert = noFriendsCirclePersonalMapper.add(noFriendsCirclePersonal);
                if(insert < 0){
                    log.info("添加朋友圈个人号失败");
                    return false;
                }
            }
        }
        log.info("数据库添加朋友圈个人号成功");
        return true;
    }

    @Override
    public Integer add(PersonalNoFriendsCirclePersonal entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return noFriendsCirclePersonalMapper.add(entity);
        return noFriendsCirclePersonalMapper.updateOne(entity);
    }

    @Override
    public Integer deleteBySql(Sql sql) {
        return noFriendsCirclePersonalMapper.deleteBySql(sql);
    }

    @Override
    public List<PersonalNoFriendsCirclePersonal> list(String sql) {
        return noFriendsCirclePersonalMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return noFriendsCirclePersonalMapper.listString(sql);
    }

    @Override
    public PersonalNoFriendsCirclePersonal getOne(String sql) {
        return noFriendsCirclePersonalMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return noFriendsCirclePersonalMapper.getCount(sql);
    }
}
