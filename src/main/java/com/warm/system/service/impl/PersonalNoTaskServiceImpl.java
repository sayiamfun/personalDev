package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.query.QueryPersonalTask;
import com.warm.entity.requre.RecommendedReasons;
import com.warm.system.entity.*;
import com.warm.system.mapper.PersonalNoTaskMapper;
import com.warm.system.service.db1.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
    private PersonalNoService noService;
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
    private PersonalNoTaskDataService taskDataService;
    @Autowired
    private PersonalNoPeopleService peopleService;
    @Autowired
    private PersonalNoGroupCategoryService groupCategoryService;

    private String ZCDBPersonalNo = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no);
    private String ZCDBNoPeople = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_people);
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
            task.setRecommendedReasons(temp.toString());
        }
        PersonalNoTask noTask = getTask(task);
        int insert = 0;
        noTask.setCreateTime(new Date());
        if(VerifyUtils.isEmpty(noTask.getId())){
            log.info("添加个人号任务到数据库");
            noTask.setAddFriendIndex(0);
            insert = baseMapper.insert(noTask);
        }else {
            log.info("修改个人号任务到数据库");
            insert = baseMapper.updateById(noTask);
        }
        if(insert != 1){
            log.info("数据库添加任务信息失败");
            return false;
        }
        log.info("将个人号列表插入到数据库");
        boolean b = false;
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
        log.info("将任务数据添加到数据库");
        PersonalNoTaskData byTaskId = taskDataService.getByTaskId(noTask.getId());
        if(!VerifyUtils.isEmpty(byTaskId)) {
            PersonalNoTaskData taskData = new PersonalNoTaskData();
            taskData.setPersonalNoTaskId(noTask.getId());
            b = taskDataService.insert(taskData);
            if (!b) {
                log.info("将任务数据保存到数据库失败");
                return false;
            }
        }
        log.info("将标签列表保存到数据库");
        b = personalNoTaskLableService.batchSave(noTask);
        if(!b){
            log.info("将标签列表保存到数据库失败");
            return false;
        }
        log.info("将任务保存到数据库成功");
        return true;
    }
    /*
     * 条件分页查询个人号任务
     */
    @Override
    public Page<PersonalNoTask> pageQuery(Page<PersonalNoTask> page, QueryPersonalTask queryPersonalTask) {
        EntityWrapper<PersonalNoTask> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        if(!VerifyUtils.isEmpty(queryPersonalTask)){
            log.info("开始条件查询");
            if(!VerifyUtils.isEmpty(queryPersonalTask.getActivityType())){
                log.info("根据状态查询个人号任务");
                entityWrapper.eq("activity_type" , queryPersonalTask.getActivityType());
            }
            if(!VerifyUtils.isEmpty(queryPersonalTask.getTaskName())){
                log.info("根据个人号任务名称查询个人号任务");
                entityWrapper.like("task_name" , queryPersonalTask.getTaskName());
            }
            log.info("查询完成，返回数据");
            List<PersonalNoTask> personalNoTasks = baseMapper.selectPage(page, entityWrapper);
            for (PersonalNoTask personalNoTask : personalNoTasks) {
                List<PersonalNoTaskReplyContent> listByTaskId = noTaskReplyContentService.getListByTaskId(personalNoTask.getId());
                for (PersonalNoTaskReplyContent personalNoTaskReplyContent : listByTaskId) {
                    if("邀请入群".equals(personalNoTaskReplyContent.getContentType())){
                        String[] split = personalNoTaskReplyContent.getContent().split("/");
                        PersonalNoGroupCategory personalNoGroupCategory = groupCategoryService.getPersonalNoGroupCategory(split);
                        personalNoTask.setCategoryName1(personalNoGroupCategory==null?"":personalNoGroupCategory.getCname());
                    }
                }
                List<PersonalNoTaskBeginRemind> listByTaskId1 = noTaskBeginRemindService.getListByTaskId(personalNoTask.getId());
                for (PersonalNoTaskBeginRemind personalNoTaskBeginRemind : listByTaskId1) {
                    if("邀请入群".equals(personalNoTaskBeginRemind.getContentType())){
                        String[] split = personalNoTaskBeginRemind.getContent().split("/");
                        PersonalNoGroupCategory personalNoGroupCategory = groupCategoryService.getPersonalNoGroupCategory(split);
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
        EntityWrapper<PersonalNoTask> queryWrapper = new EntityWrapper<>();
        queryWrapper.eq("activity_type",0);
        List<PersonalNoTask> noTasks = baseMapper.selectList(queryWrapper);
        log.info("开始判断个人号数量是否为0");
        Iterator<PersonalNoTask> iterator = noTasks.iterator();
        while (iterator.hasNext()){
            PersonalNoTask noTask = iterator.next();
            List<PersonalNoTaskPersonal> listByTaskId = personalNoTaskPersonalService.listByTaskId(noTask.getId());
            boolean flag = false;
            if(VerifyUtils.collectionIsEmpty(listByTaskId)){
                //个人号数量为 0
                flag = true;
            }else {
                log.info("判断当前个人号是否正常使用");
                String sql = null;
                for (PersonalNoTaskPersonal personalNoTaskPersonal : listByTaskId) {
                    sql = DaoGetSql.getSql("select * from "+ZCDBPersonalNo+" where wx_id = ? limit 0,1",personalNoTaskPersonal.getPersonalNoWxId());
                    PersonalNo byId = noService.getOne(sql);
                    if(!VerifyUtils.isEmpty(byId)) {
                        if (!"封禁".equals(byId.getEquipmentStatus()) && !"暂停服务".equals(byId.getEquipmentStatus()) && !"断开".equals(byId.getEquipmentStatus())) {
                            flag = false;
                            break;
                        }
                        flag = true;
                    }
                }
            }
            if(flag){
                iterator.remove();
            }
            log.info("个人号数量不为0");
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
        PersonalNoTask personalNoTask = baseMapper.selectById(taskId);
        PersonalNoTask noTaskById = getTaskInfoById(personalNoTask);
        if(!VerifyUtils.isEmpty(noTaskById)){
            log.info("根据推荐理由转为推荐理由列表");
            String recommendedReason = noTaskById.getRecommendedReasons();
            if(!VerifyUtils.isEmpty(recommendedReason)){
                String[] split = recommendedReason.split("&");
                if(null != split){
                    List<RecommendedReasons> reasonsList = new ArrayList<>();
                    for (String s : split) {
                        RecommendedReasons recommendedReasons = new RecommendedReasons();
                        recommendedReasons.setContent(s);
                        reasonsList.add(recommendedReasons);
                    }
                    noTaskById.setRecommendedReasonsList(reasonsList);
                }
            }
            log.info("根据任务id号查询自动回复列表");
            List<PersonalNoTaskReplyContent> replyContentList = noTaskReplyContentService.getListByTaskId(taskId);
            noTaskById.setNoTaskReplyContentList(replyContentList);
            log.info("根据任务id号查询开课提醒列表");
            List<PersonalNoTaskBeginRemind> beginRemindList = noTaskBeginRemindService.getListByTaskId(taskId);
            noTaskById.setNoTaskBeginRemindList(beginRemindList);
            log.info("根据任务id查询任务标签列表");
            List<PersonalNoTaskLable> personalNoTaskLables = personalNoTaskLableService.listByTaskId(taskId);
            noTaskById.setNoLableList(personalNoTaskLables);
            //将任务渠道列表转换为渠道名称列表
            noTaskById.setChannelNameList(getChannelNameList(noTaskById.getPersonalnoChannelList()));
            //将渠道信息列表转换为渠道名称列表
            String sql = DaoGetSql.getSql("select channel_name from " + DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_channel));
            List<String> list = noChannelService.listString(sql);
            noTaskById.setAllChannelNameList(list);
            log.info("数据库ID查询任务结束");
        }
        return noTaskById;
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
            List<PersonalNoTaskLable> lableList = personalNoTaskLableService.listByTaskId(byId.getId());
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
            List<PersonalNoTaskPersonal> personalList = personalNoTaskPersonalService.listByTaskId(byId.getId());
            //设置个人号数量
            byId.setPersonalNum(personalList.size());
            byId.setNoList(personalList);
            log.info("根据任务id号查询渠道列表");
            List<PersonalNoTaskChannel> channelList = personalNoTaskChannelService.getListByTaskId(byId.getId());
            //设置渠道数量和渠道列表信息
            byId.setChannelNum(channelList.size());
            byId.setPersonalnoChannelList(channelList);
        }
        log.info("数据库根据id查询任务标签，个人号，渠道结束");
        return byId;
    }

    @Override
    public List<Integer> listByPersonalWxId(String wxId) {
        return noTaskMapper.listByPersonalWxId(wxId);
    }

    /**
     * 根据通道id查询任务
     * @param id
     * @return
     */
    @Override
    public List<PersonalNoTask> listByRoadId(Integer id) {
        return noTaskMapper.listByRoadId(id);
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
        PersonalNoTaskData taskData = taskDataService.getByTaskId(taskId);
        if(!VerifyUtils.isEmpty(taskData)) {
            log.info("开始查询任务对应的个人号的数据");
            List<PersonalNoTaskPersonal> personalNoTaskPersonals = personalNoTaskPersonalService.listByTaskId(taskId);
            log.info("根据个人号和任务id查询好友人数");
            Integer num = 0;
            String sql = "";
            for (PersonalNoTaskPersonal personalNoTaskPersonal : personalNoTaskPersonals) {
                sql = DaoGetSql.getSql("SELECT id,personal_friend_wx_id,personal_task_id,personal_no_wx_id,channel_id,flag,be_friend_time,remarks,personal_friend_nick_name,deleted FROM "+ZCDBNoPeople+" WHERE personal_task_id = ? and personal_no_wx_id = ? and deleted = 0",taskId, personalNoTaskPersonal.getPersonalNoWxId());
                List<PersonalNoPeople> peopleList = peopleService.list(sql);
                personalNoTaskPersonal.setPeopleNum(peopleList == null ? 0 : peopleList.size());
                num += (peopleList == null ? 0 : peopleList.size());
            }
            taskData.setPeopleNum(num);
            map.put("taskData", taskData);
            map.put("personalList", personalNoTaskPersonals);
        }else {
            map.put("No", "无数据");
        }
        return map;
    }
    /**
     * 取得当天的任务数据
     * @param date
     * @param datTaskDate
     * @return
     */
    @Override
    public List<PersonalNoTask> listByStartTimeAndEndTime(Date date, Date datTaskDate) {
        log.info("取得当天正在执行的任务");
        EntityWrapper<PersonalNoTask> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        entityWrapper.le("create_time", date).ge("task_end_time", datTaskDate);
        entityWrapper.or().between("create_time", date, datTaskDate).or().between("task_end_time", date, datTaskDate);
        return baseMapper.selectList(entityWrapper);
    }

    @Override
    public PersonalNoTask getTaskByIdLess(Integer taskId) {
        PersonalNoTask noTask = baseMapper.selectById(taskId);
        if (!VerifyUtils.isEmpty(noTask)) {
            log.info("根据推荐理由转为推荐理由列表");
            String recommendedReason = noTask.getRecommendedReasons();
            if (!VerifyUtils.isEmpty(recommendedReason)) {
                String[] split = recommendedReason.split("&");
                if (null != split) {
                    List<RecommendedReasons> reasonsList = new ArrayList<>();
                    for (String s : split) {
                        RecommendedReasons recommendedReasons = new RecommendedReasons();
                        recommendedReasons.setContent(s);
                        reasonsList.add(recommendedReasons);
                    }
                    noTask.setRecommendedReasonsList(reasonsList);
                }
            }
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
        boolean b = personalNoTaskPersonalService.deleteByTaskId(taskId);
        if(!b){
            throw new RuntimeException("将任务相关个人号删除失败");
        }
        log.info("将任务相关渠道列表删除");
        b = personalNoTaskChannelService.deleteByTaskId(taskId);
        if(!b){
            throw new RuntimeException("将任务相关渠道列表删除失败");
        }
        log.info("将任务相关回复消息删除");
        b = noTaskReplyContentService.deleteByTaskId(taskId);
        if(!b){
            throw new RuntimeException("将任务相关回复消息删除失败");
        }
        log.info("将任务相关开课提醒删除");
        b = noTaskBeginRemindService.deleteByTaskId(taskId);
        if(!b){
            throw new RuntimeException("将任务相关开课提醒删除失败");
        }
        log.info("将任务标签删除");
        b = personalNoTaskLableService.deleteByTaskId(taskId);
        if(!b){
            throw new RuntimeException("将任务标签删除失败");
        }
        int i = baseMapper.deleteById(taskId);
        if(i<=0){
            throw new RuntimeException("删除任务失败");
        }
        return false;
    }

    /**
     * 修改任务状态
     * @param taskId
     * @return
     */
    @Override
    public boolean stopTaskById(Integer taskId) {
        noTaskMapper.stopTaskById(taskId);
        return true;
    }

    /**
     * 查询所有任务名称
     * @param o
     * @return
     */
    @Override
    public List<String> listAllTaskName(Object o) {
        return noTaskMapper.listAllTaskName();
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
