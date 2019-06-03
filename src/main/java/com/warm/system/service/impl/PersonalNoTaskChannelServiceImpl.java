package com.warm.system.service.impl;

import com.warm.entity.DB;
import com.warm.entity.Sql;
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

    private String DBChannel = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_channel);
    private String DBTaskChannel = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_task_channel);

    /**
     * 批量保存任务渠道信息
     * @param noTask
     * @return
     */
    @Transactional
    @Override
    public boolean batchSave(PersonalNoTask noTask) {
        String delSql = DaoGetSql.getSql("update " + DBTaskChannel + " set deleted = 1 where personal_no_task_id = ?", noTask.getId());
        Sql sql = new Sql(delSql);
        taskChannelMapper.deleteBySql(sql);
        if(VerifyUtils.collectionIsEmpty(noTask.getChannelNameList())){
            return true;
        }
        String channelNames = DaoGetSql.getIds(noTask.getChannelNameList());
        String getSql = DaoGetSql.getSql("select * from " + DBChannel + " where channel_name in "+channelNames + " and deleted = 0");
        List<PersonalNoChannel> channelList = channelService.list(getSql);
        PersonalNoTaskChannel taskChannel = null;
        for (PersonalNoChannel channel : channelList) {
            taskChannel = new PersonalNoTaskChannel();
            taskChannel.setPersonalNoTaskId(noTask.getId());
            taskChannel.setChannelId(channel.getId());
            taskChannel.setChannelName(channel.getChannelName());
            taskChannel.setDb(DBTaskChannel);
            taskChannel.setDeleted(0);
            int insert = taskChannelMapper.add(taskChannel);
            if(insert < 0){
                log.info("将任务渠道列表保存到数据库失败");
                return false;
            }
        }
        log.info("将任务渠道列表保存到数据库成功");
        return true;
    }


    @Override
    public List<Integer> listChannelIdsBySql(Sql sql) {
        return taskChannelMapper.listChannelIdsBySql(sql);
    }

    @Override
    public Integer getIdBySql(Sql sql) {
        return taskChannelMapper.getIdBySql(sql);
    }

    @Override
    public List<PersonalNoTaskChannel> listBySql(Sql sql) {
        return taskChannelMapper.listBySql(sql);
    }

    @Override
    public boolean deleteBySql(Sql sql) {
        return taskChannelMapper.deleteBySql(sql);
    }

    @Override
    public void updateBySql(Sql sql) {
        taskChannelMapper.updateBySql(sql);
    }
}
