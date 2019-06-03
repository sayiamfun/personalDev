package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.entity.query.QueryPersonalTask;
import com.warm.entity.requre.RecommendedReasons;
import com.warm.system.entity.*;
import com.warm.system.mapper.PersonalNoTaskMapper;
import com.warm.system.service.db1.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db2.PersonalNoOperationStockWechatAccountService;
import com.warm.system.service.db3.PersonalNoGroupCategoryService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Service
public class PersonalNoTaskServiceImpl extends ServiceImpl<PersonalNoTaskMapper, PersonalNoTask> implements PersonalNoTaskService {

    private static Log log = LogFactory.getLog(PersonalNoTaskServiceImpl.class);
    @Autowired
    private PersonalNoTaskPersonalService personalNoTaskPersonalService;
    @Autowired
    private PersonalNoTaskMapper noTaskMapper;
    @Autowired
    private PersonalNoTaskReplyContentService noTaskReplyContentService;
    @Autowired
    private PersonalNoTaskBeginRemindService noTaskBeginRemindService;
    @Autowired
    private PersonalNoTaskChannelService personalNoTaskChannelService;
    @Autowired
    private PersonalNoTaskLableService personalNoTaskLableService;
    @Autowired
    private PersonalNoChannelService noChannelService;
    @Autowired
    private PersonalNoPeopleService peopleService;
    @Autowired
    private PersonalNoGroupCategoryService groupCategoryService;

    private String DBNoPeople = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_people);
    private String DBBeginRemind = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_begin_remind);
    private String DBChannel = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_channel);
    private String DBTaskChannel = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_channel);
    private String DBTaskLable = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_lable);
    private String DBTask = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task);
    private String DBTaskPersonal = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_personal);
    private String DBTaskReplay = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_reply_content);

    /*
     *  添加任务到数据库
     */
    @Transactional
    @Override
    public boolean addPersonalTask(PersonalNoTask task) {
        log.info("添加任务到数据库");
        List<RecommendedReasons> recommendedReasonsList = task.getRecommendedReasonsList();
        log.info("将推荐理由集合转成字符串，中间以 & 拼接");
        if(!VerifyUtils.collectionIsEmpty(recommendedReasonsList)){
            StringBuffer temp = new StringBuffer();
            for (int i = 0; i < recommendedReasonsList.size(); i++) {
                if(i>0){
                    temp.append("&");
                }
                temp.append(recommendedReasonsList.get(i).getContent());
            }
        }
        PersonalNoTask noTask = getTask(task);
        int insert = 0;
        noTask.setDeleted(0);
        noTask.setCreateTime(new Date());
        noTask.setActivityType(0);
        noTask.setDb(DBTask);
        boolean b = false;
        log.info("只有添加才可以插入任务个人号，任务渠道，任务标签");
        if(VerifyUtils.isEmpty(noTask.getId())){
            log.info("添加个人号任务到数据库");
            noTask.setAddFriendIndex(0);
            insert = noTaskMapper.add(noTask);
            log.info("将个人号列表插入到数据库");
            b = personalNoTaskPersonalService.batchSave(noTask);
            if(!b){
                log.info("将个人号列表插入到数据库失败");
                return false;
            }
            log.info("将渠道列表插入到数据库");
            b = personalNoTaskChannelService.batchSave(noTask);
            if(!b){
                log.info("将渠道列表插入到数据库失败");
                return false;
            }
        }else {
            log.info("修改个人号任务到数据库");
            insert = noTaskMapper.updateOne(noTask);
        }
        if(insert < 0){
            log.info("数据库添加任务信息失败");
            return false;
        }
        log.info("将标签列表保存到数据库");
        b = personalNoTaskLableService.batchSave(noTask);
        if(!b){
            log.info("将标签列表保存到数据库失败");
            return false;
        }
        log.info("将回复消息插入到数据库");
        b = noTaskReplyContentService.batchSave(noTask);
        if(!b){
            log.info("将回复消息插入到数据库失败");
            return false;
        }
        log.info("将开课提醒插入到数据库");
        b = noTaskBeginRemindService.batchSave(noTask);
        if(!b){
            log.info("将开课提醒插入到数据库失败");
            return false;
        }
        log.info("将任务保存到数据库成功");
        return true;
    }
    /*
     * 条件分页查询个人号任务
            task.setRecommendedReasons(temp.toString());
     */
    @Override
    public Page<PersonalNoTask> pageQuery(Page<PersonalNoTask> page, QueryPersonalTask queryPersonalTask) {

        String getSql = null;
        Sql sql = new Sql();
        StringBuffer temp = new StringBuffer(" where deleted = 0 ");
        boolean F = true;
        if(!VerifyUtils.isEmpty(queryPersonalTask)){
            log.info("开始条件查询");
            if(!VerifyUtils.isEmpty(queryPersonalTask.getActivityType())){
                log.info("根据状态查询个人号任务");
                temp = DaoGetSql.getTempSql(temp,F);
                temp.append(" activity_type = '"+queryPersonalTask.getActivityType()+"'");
                F = true;
            }
            if(!VerifyUtils.isEmpty(queryPersonalTask.getTaskName())){
                log.info("根据个人号任务名称查询个人号任务");
                temp = DaoGetSql.getTempSql(temp,F);
                temp.append(" task_name like '%"+queryPersonalTask.getTaskName()+"%'");
                F = true;
            }
            log.info("查询完成，返回数据");
            getSql = DaoGetSql.getSql("select count(*) from "+DBTask+temp.toString());
            sql.setSql(getSql);
            Long count = noTaskMapper.countBySql(sql);
            page.setTotal(count.intValue());
            temp.append(" order by id desc limit "+page.getOffset()+","+page.getLimit());
            getSql = DaoGetSql.getSql("select * from "+DBTask+temp.toString());
            sql.setSql(getSql);
            List<PersonalNoTask> personalNoTasks = noTaskMapper.listBySql(sql);
            for (PersonalNoTask personalNoTask : personalNoTasks) {
                getSql = DaoGetSql.getSql("SELECT * FROM "+DBTaskReplay+" WHERE personal_no_task_id = ? and deleted = 0",personalNoTask.getId());
                sql.setSql(getSql);
                List<PersonalNoTaskReplyContent> replyContentList = noTaskReplyContentService.listBySql(sql);
                for (PersonalNoTaskReplyContent personalNoTaskReplyContent : replyContentList) {
                    if("邀请入群".equals(personalNoTaskReplyContent.getContentType())){
                        PersonalNoGroupCategory personalNoGroupCategory = groupCategoryService.getPersonalNoGroupCategory(personalNoTaskReplyContent.getContent());
                        personalNoTask.setCategoryName1(personalNoGroupCategory==null?"":personalNoGroupCategory.getCname());
                    }
                }
                getSql = DaoGetSql.getSql("SELECT * FROM "+DBBeginRemind+" where personal_no_task_id = ? and deleted = 0",personalNoTask.getId());
                sql.setSql(getSql);
                List<PersonalNoTaskBeginRemind> listByTaskId1 = noTaskBeginRemindService.listBySql(sql);
                for (PersonalNoTaskBeginRemind personalNoTaskBeginRemind : listByTaskId1) {
                    if("邀请入群".equals(personalNoTaskBeginRemind.getContentType())){
                        PersonalNoGroupCategory personalNoGroupCategory = groupCategoryService.getPersonalNoGroupCategory(personalNoTaskBeginRemind.getContent());
                        personalNoTask.setCategoryName2(personalNoGroupCategory==null?"":personalNoGroupCategory.getCname());
                    }
                }
            }
            page.setRecords(personalNoTasks);
        }
        return page;
    }
    /*
     * 查询所有正在进行的并且相关个人号数量不为0的任务数量
     */
    @Override
    public int getOnGoingTaskNum() {
        log.info("开始查询个人号承担的个人号任务数量");
        String getSql = DaoGetSql.getSql("SELECT * FROM "+DBTask+" where activity_type = 0 and deleted = 0");
        Sql sql = new Sql(getSql);
        List<PersonalNoTask> noTasks = noTaskMapper.listBySql(sql);
        log.info("开始判断个人号数量是否为0");
        Iterator<PersonalNoTask> iterator = noTasks.iterator();
        while (iterator.hasNext()){
            PersonalNoTask noTask = iterator.next();
            getSql = DaoGetSql.getSql("SELECT count(*) FROM "+DBTaskPersonal+" WHERE personal_no_task_id = ? and deleted = 0 ",noTask.getId());
            sql.setSql(getSql);
            Long count = personalNoTaskPersonalService.countBySql(sql);
            if(count==0){
                iterator.remove();
            }
        }
        log.info("查询个人号相关任务数成功");
        return noTasks.size();
    }

    /*
     * 根据任务id查询任务及其相关信息
     */
    @Override
    public PersonalNoTask getTaskById(Integer taskId) {
        log.info("数据库ID查询任务");
        String getSql = DaoGetSql.getSql("SELECT * FROM "+DBTask+" where id = ? and deleted = 0",taskId);
        Sql sql = new Sql(getSql);
        PersonalNoTask personalNoTask = noTaskMapper.getbySql(sql);
        PersonalNoTask noTaskById = getTaskInfoById(personalNoTask);
        if(!VerifyUtils.isEmpty(noTaskById)){
            log.info("根据推荐理由转为推荐理由列表");
            List<RecommendedReasons> recommendedReason = getRecommendedReason(noTaskById.getRecommendedReasons());
            noTaskById.setRecommendedReasonsList(recommendedReason);
            log.info("根据任务id号查询自动回复列表");
            getSql = DaoGetSql.getSql("SELECT * FROM "+DBTaskReplay+" WHERE personal_no_task_id = ? and deleted = 0",taskId);
            sql.setSql(getSql);
            List<PersonalNoTaskReplyContent> replyContentList = noTaskReplyContentService.listBySql(sql);
            noTaskById.setNoTaskReplyContentList(replyContentList);
            log.info("根据任务id号查询开课提醒列表");
            getSql = DaoGetSql.getSql("SELECT * FROM "+DBBeginRemind+" where personal_no_task_id = ?  and deleted = 0",taskId);
            sql.setSql(getSql);
            List<PersonalNoTaskBeginRemind> beginRemindList = noTaskBeginRemindService.listBySql(sql);
            noTaskById.setNoTaskBeginRemindList(beginRemindList);
            log.info("根据任务id查询任务标签列表");
            getSql = DaoGetSql.getSql("SELECT * FROM "+DBTaskLable+" where personal_no_task_id = ?  and deleted = 0",taskId);
            sql.setSql(getSql);
            List<PersonalNoTaskLable> personalNoTaskLables = personalNoTaskLableService.listBySql(sql);
            noTaskById.setNoLableList(personalNoTaskLables);
            //将任务渠道列表转换为渠道名称列表
            noTaskById.setChannelNameList(getChannelNameList(noTaskById.getPersonalnoChannelList()));
            //将渠道信息列表转换为渠道名称列表
            getSql = DaoGetSql.getSql("select channel_name from " + DBChannel+" where deleted = 0");
            List<String> list = noChannelService.listString(getSql);
            noTaskById.setAllChannelNameList(list);
            log.info("数据库ID查询任务结束");
        }
        return noTaskById;
    }
//将推荐理由字符转分割为列表
    private List<RecommendedReasons> getRecommendedReason(String recommendedReason){
        List<RecommendedReasons> reasonsList = new ArrayList<>();
        if(!VerifyUtils.isEmpty(recommendedReason)){
            String[] split = recommendedReason.split("&");
            for (String s : split) {
                RecommendedReasons recommendedReasons = new RecommendedReasons();
                recommendedReasons.setContent(s);
                reasonsList.add(recommendedReasons);
            }
        }
        return reasonsList;
    }

    /**
     * 根据任务查询任务显示界面的任务信息
     * 标签类别，个人号数量，渠道数量
     * @param byId
     * @return
     */
    @Override
    public PersonalNoTask getTaskInfoById(PersonalNoTask byId) {
        log.info("数据库根据id查询任务标签，个人号，渠道");
        if(!VerifyUtils.isEmpty(byId)) {
            log.info("根据任务id查询所有的标签列表");
            String getSql = DaoGetSql.getSql("SELECT * FROM "+DBTaskLable+" where personal_no_task_id = ? and deleted = 0",byId.getId());
            Sql sql = new Sql(getSql);
            List<PersonalNoTaskLable> lableList = personalNoTaskLableService.listBySql(sql);
            if (!VerifyUtils.collectionIsEmpty(lableList)) {
                log.info("开始拼接标签列表");
                StringBuffer temp = new StringBuffer();
                for (int i = 0; i < lableList.size(); i++) {
                    if (i > 0) {
                        temp.append("|");
                    }
                    temp.append(lableList.get(i).getLableName());
                }
                byId.setNoLable(temp.toString());
                byId.setNoLableList(lableList);
            }
            log.info("根据任务id号查询个人号信息列表");
            getSql = DaoGetSql.getSql("SELECT * FROM "+DBTaskPersonal+" where personal_no_task_id = ? and deleted = 0",byId.getId());
            sql.setSql(getSql);
            List<PersonalNoTaskPersonal> personalList = personalNoTaskPersonalService.listBySql(sql);
            //设置个人号数量
            byId.setPersonalNum(personalList.size());
            byId.setNoList(personalList);
            log.info("根据任务id号查询渠道列表");
            getSql = DaoGetSql.getSql("SELECT * FROM " + DBTaskChannel + " where personal_no_task_id = ? and deleted = 0", byId.getId());
            sql.setSql(getSql);
            List<PersonalNoTaskChannel> taskChannelList = personalNoTaskChannelService.listBySql(sql);
            //设置渠道数量和渠道列表信息
            byId.setChannelNum(taskChannelList.size());
            byId.setPersonalnoChannelList(taskChannelList);
        }
        log.info("数据库根据id查询任务标签，个人号，渠道结束");
        return byId;
    }

    /**
     * 根据任务id查询任务数据信息
     * @param taskId
     * @return
     */
    @Override
    public Map<String, Object> getPersonalDataByTaskId(Integer taskId) {
        Map<String, Object> map = new HashMap<>();
        log.info("根据任务id查询任务数据");
        PersonalNoTaskData taskData = new PersonalNoTaskData();
        log.info("开始查询任务对应的个人号的数据");
        String getSql = DaoGetSql.getSql("SELECT * FROM "+DBTaskPersonal+" where personal_no_task_id = ? and deleted = 0",taskId);
        Sql sql = new Sql(getSql);
        List<PersonalNoTaskPersonal> personalNoTaskPersonals = personalNoTaskPersonalService.listBySql(sql);
        log.info("根据个人号和任务id查询好友人数");
        Integer num = 0;
        Integer toNum = 0;
        for (PersonalNoTaskPersonal personalNoTaskPersonal : personalNoTaskPersonals) {
            getSql = DaoGetSql.getSql("SELECT count(*) FROM "+DBNoPeople+" WHERE personal_task_id = ? and personal_no_wx_id = ? and deleted = 0 and flag = 2",taskId, personalNoTaskPersonal.getPersonalNoWxId());
            Long count = peopleService.getCount(getSql);
            personalNoTaskPersonal.setPeopleNum(count.intValue());
            num += count.intValue();
            getSql = DaoGetSql.getSql("SELECT count(*) FROM "+DBNoPeople+" WHERE personal_task_id = ? and personal_no_wx_id = ? and deleted = 0",taskId, personalNoTaskPersonal.getPersonalNoWxId());
            count = peopleService.getCount(getSql);
            personalNoTaskPersonal.setToPeopleNum(count.intValue());
            toNum += count.intValue();
        }
        taskData.setPeopleNum(num);
        taskData.setToPeopleNum(toNum);
        map.put("taskData", taskData);
        map.put("personalList", personalNoTaskPersonals);
        return map;
    }

    @Override
    public PersonalNoTask getTaskByIdLess(Integer taskId) {
        String getSql = DaoGetSql.getSql("SELECT * from "+DBTask+" where id = ? and deleted = 0",taskId);
        Sql sql = new Sql(getSql);
        PersonalNoTask noTask = baseMapper.getbySql(sql);
        if (!VerifyUtils.isEmpty(noTask)) {
            log.info("根据推荐理由转为推荐理由列表");
            List<RecommendedReasons> recommendedReason = getRecommendedReason(noTask.getRecommendedReasons());
            noTask.setRecommendedReasonsList(recommendedReason);
        }
        return noTask;
    }

    /**
     * 根据任务id删除任务
     * @param taskId
     * @return
     */
    @Transactional
    @Override
    public boolean deleteById(Integer taskId) {
        log.info("将任务相关个人号删除");
        Sql sql = new Sql();
        String delSql = DaoGetSql.getSql("UPDATE  " + DBTaskPersonal + " set deleted = 1 where personal_no_task_id = ?", taskId);
        sql.setSql(delSql);
        boolean b = personalNoTaskPersonalService.deleteBySql(sql);
        if(!b){
            throw new RuntimeException("将任务相关个人号删除失败");
        }
        log.info("将任务相关渠道列表删除");
        delSql = DaoGetSql.getSql("UPDATE " + DBTaskChannel + " set deleted = 1 where personal_no_task_id = ?", taskId);
        sql.setSql(delSql);
        b = personalNoTaskChannelService.deleteBySql(sql);
        if(!b){
            throw new RuntimeException("将任务相关渠道列表删除失败");
        }
        log.info("将任务相关回复消息删除");
        delSql = DaoGetSql.getSql("UPDATE " + DBTaskReplay + " set deleted = 1 where personal_no_task_id = ?", taskId);
        sql.setSql(delSql);
        b = noTaskReplyContentService.delBySql(sql);
        if(!b){
            throw new RuntimeException("将任务相关回复消息删除失败");
        }
        log.info("将任务相关开课提醒删除");
        delSql = DaoGetSql.getSql("UPDATE "+DBBeginRemind+" set deleted = 1 where personal_no_task_id = ?",taskId);
        sql.setSql(delSql);
        b = noTaskBeginRemindService.deleteBySql(sql);
        if(!b){
            throw new RuntimeException("将任务相关开课提醒删除失败");
        }
        log.info("将任务标签删除");
        delSql = DaoGetSql.getSql("UPDATE "+DBTaskLable+" set deleted = 1 where personal_no_task_id = ?",taskId);
        sql.setSql(delSql);
        b = personalNoTaskLableService.deleteBySql(sql);
        if(!b){
            throw new RuntimeException("任务标签删除失败");
        }
        delSql = DaoGetSql.getSql("UPDATE "+DBTask+" set deleted = 1 where id = ?",taskId);
        sql.setSql(delSql);
        int i = noTaskMapper.deleteBySql(sql);
        if(i<0){
            throw new RuntimeException("删除任务失败");
        }
        return false;
    }


    @Override
    public List<PersonalNoTask> listByQueryPersonalData(Sql sql) {
        return noTaskMapper.listByQueryPersonalData(sql);
    }

    @Override
    public List<PersonalNoTask> listBySql(Sql sql) {
        return noTaskMapper.listBySql(sql);
    }

    @Override
    public List<String> listtaskNamesBySql(Sql sql) {
        return noTaskMapper.listtaskNamesBySql(sql);
    }

    @Override
    public boolean updateBySql(Sql sql) {
        return noTaskMapper.updateBySql(sql);
    }

    @Override
    public Long countBySql(Sql sql) {
        return noTaskMapper.countBySql(sql);
    }

    @Override
    public PersonalNoTask getBySql(Sql sql) {
        return noTaskMapper.getbySql(sql);
    }


    //将任务渠道列表转换为渠道名称列表      返回数据使用
    private List<String > getChannelNameList(List<PersonalNoTaskChannel> noTaskChannelList){
        log.info("将任务渠道列表转换为渠道名称列表");
        List<String> channelNameList = new ArrayList<>();
        if(!VerifyUtils.collectionIsEmpty(noTaskChannelList)){
            for (PersonalNoTaskChannel personalNoTaskChannel : noTaskChannelList) {
                channelNameList.add(personalNoTaskChannel.getChannelName());
            }
        }
        return channelNameList;
    }
    //根据理由列表，得到推荐理由
    //统计个人号数量和渠道数量
    private PersonalNoTask getTask(PersonalNoTask noTask) {
        log.info("统计个人号数量和渠道数量");
        int personalNum = 0;
        int channelNum = 0;
        if(!VerifyUtils.collectionIsEmpty(noTask.getNoList())){
            personalNum = noTask.getNoList().size();
        }
        if(!VerifyUtils.collectionIsEmpty(noTask.getPersonalnoChannelList())){
            channelNum = noTask.getPersonalnoChannelList().size();
        }
        noTask.setPersonalNum(personalNum);
        noTask.setChannelNum(channelNum);
        log.info("得到推荐理由集合");
        List<RecommendedReasons> recommendedReasonsList = noTask.getRecommendedReasonsList();
        if(!VerifyUtils.collectionIsEmpty(recommendedReasonsList)){
            log.info("开始拼接推荐理由");
            StringBuffer temp = new StringBuffer();
            for(int i = 0 ; i < recommendedReasonsList.size(); i++){
                if(i > 0){
                    temp.append("|");
                }
                temp.append(recommendedReasonsList.get(i).getContent());
            }
            noTask.setRecommendedReasons(temp.toString());
        }
        log.info("统计结束");
        return noTask;
    }
}
