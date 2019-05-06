package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warm.entity.DB;
import com.warm.system.entity.PersonalNoFriendsCircle;
import com.warm.system.entity.PersonalNoFriendsCirclePhoto;
import com.warm.system.mapper.PersonalNoFriendsCirclePhotoMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoFriendsCirclePhotoService;
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
public class PersonalNoFriendsCirclePhotoServiceImpl extends ServiceImpl<PersonalNoFriendsCirclePhotoMapper, PersonalNoFriendsCirclePhoto> implements PersonalNoFriendsCirclePhotoService {
    private static Log log = LogFactory.getLog(PersonalNoFriendsCirclePhotoServiceImpl.class);
    @Autowired
    private PersonalNoFriendsCirclePhotoMapper friendsCirclePhotoMapper;
    /**
     * 批量添加朋友圈照片数据
     * @param noFriendsCircle
     * @return
     */
    @Transactional
    @Override
    public boolean batchSave(PersonalNoFriendsCircle noFriendsCircle) {
        log.info("数据库开始批量添加朋友圈相关照片数据");
        log.info("先根据朋友圈id删除照片数据");
        String sql = DaoGetSql.getSql("delete from " + DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_friends_circle_photo )+ " where `friends_circle_id` = ?", noFriendsCircle.getId());
        friendsCirclePhotoMapper.delete(sql);
        List<PersonalNoFriendsCirclePhoto> photoList = noFriendsCircle.getPhotoList();
        if(!VerifyUtils.collectionIsEmpty(photoList)){
            log.info("数据库开始插入朋友圈照片数据");
            for (PersonalNoFriendsCirclePhoto noFriendsCirclePhoto : photoList) {
                noFriendsCirclePhoto.setId(null);
                noFriendsCirclePhoto.setFriendsCircleId(noFriendsCircle.getId());
                noFriendsCirclePhoto.setDb(DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_friends_circle_photo ));
                int insert = add(noFriendsCirclePhoto);
                if(insert != 1){
                    log.info("数据库插入朋友圈照片失败");
                    return false;
                }
            }
        }
        log.info("批量添加朋友圈照片成功");
        return true;
    }

    @Override
    public Integer add(PersonalNoFriendsCirclePhoto entity) {
        if(VerifyUtils.isEmpty(entity.getId()))
            return friendsCirclePhotoMapper.add(entity);
        return friendsCirclePhotoMapper.updateOne(entity);
    }

    @Override
    public Integer delete(String sql) {
        return friendsCirclePhotoMapper.delete(sql);
    }

    @Override
    public List<PersonalNoFriendsCirclePhoto> list(String sql) {
        return friendsCirclePhotoMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return friendsCirclePhotoMapper.listString(sql);
    }

    @Override
    public PersonalNoFriendsCirclePhoto getOne(String sql) {
        return friendsCirclePhotoMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return friendsCirclePhotoMapper.getCount(sql);
    }
}
