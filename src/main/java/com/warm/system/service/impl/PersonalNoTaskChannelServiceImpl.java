package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.warm.entity.DB;
import com.warm.system.entity.PersonalNoChannel;
import com.warm.system.entity.PersonalNoTask;
import com.warm.system.entity.PersonalNoTaskChannel;
import com.warm.system.mapper.PersonalNoTaskChannelMapper;
import com.warm.system.service.db1.PersonalNoChannelService;
import com.warm.system.service.db1.PersonalNoTaskChannelService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
public class PersonalNoTaskChannelServiceImpl extends ServiceImpl<PersonalNoTaskChannelMapper, PersonalNoTaskChannel> implements PersonalNoTaskChannelService {
    private static Log log = LogFactory.getLog(PersonalNoTaskChannelServiceImpl.class);
    @Autowired
    private PersonalNoChannelService channelService;
    @Autowired
    private PersonalNoTaskChannelMapper taskChannelMapper;

    private String ZCDBChannel = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_channel);
    /*
     * 根据任务id查询相对的渠道信息列表
     */
    @Override
    public List<PersonalNoTaskChannel> getListByTaskId(Integer id) {
        log.info("根据任务id查询对应的渠道开始");
        EntityWrapper<PersonalNoTaskChannel> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        if(VerifyUtils.isEmpty(id)){
            log.info("任务id为空");
            entityWrapper.eq("personal_no_task_id" , -1);
        }else {
            entityWrapper.eq("personal_no_task_id" , id);
        }
        List<PersonalNoTaskChannel> channelList = baseMapper.selectList(entityWrapper);
        log.info("根据任务id查询对应的渠道结束");
        return channelList;
    }

    /**
     * 批量保存任务渠道信息
     * @param noTask
     * @return
     */
    @Transactional
    @Override
    public boolean batchSave(PersonalNoTask noTask) {
        deleteByTaskId(noTask.getId());
        List<String> channelNameList = noTask.getChannelNameList();
        if(!VerifyUtils.collectionIsEmpty(channelNameList)){
            log.info("将任务渠道列表保存到数据库开始");
            for (String s : channelNameList) {
                String sql = DaoGetSql.getSql("select * from " + ZCDBChannel + " where channel_name = ? limit 0,1", s);
                PersonalNoChannel channel = channelService.getOne(sql);
                PersonalNoTaskChannel taskChannel = new PersonalNoTaskChannel();
                taskChannel.setPersonalNoTaskId(noTask.getId());
                taskChannel.setChannelId(channel.getId());
                taskChannel.setChannelName(channel.getChannelName());
                int insert = baseMapper.insert(taskChannel);
                if(insert != 1){
                    log.info("将任务渠道列表保存到数据库失败");
                    return false;
                }
            }
        }
        log.info("将任务渠道列表保存到数据库成功");
        return true;
    }

    /**
     * 根据任务id删除任务相关渠道
     * @param taskId
     * @return
     */
    @Override
    public boolean deleteByTaskId(Integer taskId) {
        EntityWrapper<PersonalNoTaskChannel> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("personal_no_task_id", taskId);
        return baseMapper.delete(entityWrapper)>0;
    }

    @Override
    public PersonalNoTaskChannel selectByTaskIdAndChannelId(String roadId, Integer channelId, Integer roadOrTask) {
        return taskChannelMapper.selectByTaskIdAndChannelId(roadId, channelId,roadOrTask);
    }
}
