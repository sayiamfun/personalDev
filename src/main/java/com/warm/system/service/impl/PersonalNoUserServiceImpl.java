package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warm.system.entity.PersonalNoUser;
import com.warm.system.mapper.PersonalNoUserMapper;
import com.warm.system.service.db1.PersonalNoUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.VerifyUtils;
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
public class PersonalNoUserServiceImpl extends ServiceImpl<PersonalNoUserMapper, PersonalNoUser> implements PersonalNoUserService {
    private static Log log = LogFactory.getLog(PersonalNoUserServiceImpl.class);
    @Autowired
    private PersonalNoUserMapper userMapper;
    /**
     * 根据用户wxId查找该用户信息
     * @param username
     * @return
     */
    @Override
    public PersonalNoUser getByWxId(String username) {
        return userMapper.getByWxId(username);
    }
    @Override
    public boolean insert(PersonalNoUser user) {
        if(VerifyUtils.isEmpty(user.getId())){
            log.info("该用户不存在，插入表");
            return baseMapper.insert(user)>0;
        }
        log.info("该用户存在，修改信息");
        return baseMapper.updateById(user)>0;
    }

    /**
     * 根据用户昵称查询用户
     * @param nickname
     * @return
     */
    @Override
    public List<PersonalNoUser> getByNickName(String nickname) {
        EntityWrapper<PersonalNoUser> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        entityWrapper.eq("nick_name", nickname);
        long time = new Date().getTime();
        long startTime =  time - 360000L;
        long endTime = time + 10000L;
        entityWrapper.between("create_time", new Date(startTime), new Date(endTime));
        return baseMapper.selectList(entityWrapper);
    }

    /**
     * 根据openid取得唯一用户
     * @param openid
     * @return
     */
    @Override
    public PersonalNoUser getByOpenid(String openid) {
        return userMapper.getByOpenid(openid);
    }

    /**
     * 根据微信id获取用户
     * @param wxId
     * @return
     */
    @Override
    public List<PersonalNoUser> listByWxId(String wxId) {
        return userMapper.listByWxId(wxId);
    }

    /**
     * 根据unionid获取唯一用户信息
     * @param unionid
     * @return
     */
    @Override
    public PersonalNoUser getByUnionId(String unionid) {
        return userMapper.getByUnionId(unionid);
    }
}
