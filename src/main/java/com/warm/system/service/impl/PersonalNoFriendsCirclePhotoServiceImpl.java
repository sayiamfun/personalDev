package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warm.system.entity.PersonalNoFriendsCircle;
import com.warm.system.entity.PersonalNoFriendsCirclePhoto;
import com.warm.system.mapper.PersonalNoFriendsCirclePhotoMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoFriendsCirclePhotoService;
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
public class PersonalNoFriendsCirclePhotoServiceImpl extends ServiceImpl<PersonalNoFriendsCirclePhotoMapper, PersonalNoFriendsCirclePhoto> implements PersonalNoFriendsCirclePhotoService {
    private static Log log = LogFactory.getLog(PersonalNoFriendsCirclePhotoServiceImpl.class);
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
        EntityWrapper<PersonalNoFriendsCirclePhoto> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("friends_circle_id", noFriendsCircle.getId());
        baseMapper.delete(entityWrapper);
        List<PersonalNoFriendsCirclePhoto> photoList = noFriendsCircle.getPhotoList();
        if(!VerifyUtils.collectionIsEmpty(photoList)){
            log.info("数据库开始插入朋友圈照片数据");
            for (PersonalNoFriendsCirclePhoto noFriendsCirclePhoto : photoList) {
                noFriendsCirclePhoto.setFriendsCircleId(noFriendsCircle.getId());
                int insert = baseMapper.insert(noFriendsCirclePhoto);
                if(insert != 1){
                    log.info("数据库插入朋友圈照片失败");
                    return false;
                }
            }
        }
        log.info("批量添加朋友圈照片成功");
        return true;
    }

    /**
     * 根据朋友圈id查询相应朋友圈照片
     * @param id
     * @return
     */
    @Override
    public List<PersonalNoFriendsCirclePhoto> listByCircleId(Integer id) {
        log.info("数据库根据朋友圈id查询朋友圈照片");
        EntityWrapper<PersonalNoFriendsCirclePhoto> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        entityWrapper.eq("friends_circle_id", id);
        List<PersonalNoFriendsCirclePhoto> photoList = baseMapper.selectList(entityWrapper);
        log.info("数据库根据朋友圈id查询朋友圈照片结束");
        return photoList;
    }
}
