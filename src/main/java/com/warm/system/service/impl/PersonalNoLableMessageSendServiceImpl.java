package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.robot.common.SunTaskType;
import com.warm.system.entity.*;
import com.warm.system.mapper.PersonalNoLableMessageSendMapper;
import com.warm.system.service.db1.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.VerifyUtils;
import com.warm.utils.WebConst;
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
public class PersonalNoLableMessageSendServiceImpl extends ServiceImpl<PersonalNoLableMessageSendMapper, PersonalNoLableMessageSend> implements PersonalNoLableMessageSendService {
    public static Log log = LogFactory.getLog(PersonalNoLableMessageSendServiceImpl.class);

    @Autowired
    private PersonalNoLableMessageSendContentService personalNoLableMessageSendContentService;
    @Autowired
    private PersonalNoLableMessageSendLableNoService messageSendLableNoService;
    @Autowired
    private PersonalNoService noService;
    @Autowired
    private PersonalNoTaskLableService taskLableService;
    @Autowired
    private PersonalNoPeopleService taskPeopleService;
    @Autowired
    private PersonalNoPhoneTaskGroupService taskGroupService;
    @Autowired
    private PersonalNoPhoneTaskService taskService;
    /*
     * 添加标签消息
     */
    @Transactional
    @Override
    public boolean insertLableMessage(PersonalNoLableMessageSend personalNoLableMessageSend) {
        Date sendTime = personalNoLableMessageSend.getSendTime();
        log.info("开始添加标签消息到数据库");
        personalNoLableMessageSend.setPersonaNolLableMessageStatus("0");
        List<PersonalNoLableMessageSendContent> personalNoLableMessageSendContentList = personalNoLableMessageSend.getPersonalNoLableMessageSendContentList();
        log.info("构建标签消息内容预览开始");
        String lableContentShow = WebConst.getLableContentShow(personalNoLableMessageSendContentList);
        log.info("构建标签消息内容预览成功");
        log.info("构建标签列表集合开始");
        List<String> lableList = personalNoLableMessageSend.getLableList();
        if(!VerifyUtils.collectionIsEmpty(lableList)) {
            log.info("构建标签集合显示");
            StringBuffer lableTemp = new StringBuffer();
            for (int i = 0; i < lableList.size(); i++) {
                if(i>0){
                    lableTemp.append("|");
                }
                lableTemp.append(lableList.get(i));
            }
            personalNoLableMessageSend.setPersonalNoLableMessageLableList(lableTemp.toString());
            log.info("构建标签集合显示成功");
        }
        personalNoLableMessageSend.setPersonalNoLableMessageContentShow(lableContentShow);
        //---------------------------------------------------------------------------
        log.info("将添加的标签消息任务拆解为手机请求的任务");
        log.info("要发送的个人号集合");
        List<PersonalNo> noList = personalNoLableMessageSend.getNoList();
        log.info("标签名称集合");
        List<String> lableList1 = personalNoLableMessageSend.getLableList();
        log.info("根据标签查询任务id集合");
        Set<Integer> integers = taskLableService.listByLableNameList(lableList1);
        log.info("根据个人号微信id和任务id查询粉丝");
        Set<String> userWxId = new HashSet<>();
        for (PersonalNo no : noList) {
            for (Integer taskId : integers) {
                List<String> strings = taskPeopleService.listUserWxIdByTaskIdAndPersonalWxId(taskId, no.getWxId(),null, null);
                userWxId.addAll(strings);
            }
        }
        log.info("拿到标签任务内容");
        List<PersonalNoLableMessageSendContent> personalNoLableMessageSendContentList1 = personalNoLableMessageSend.getPersonalNoLableMessageSendContentList();
        log.info("开始转换任务");

        int num = 0;
        for (PersonalNo no : noList) {
            //用来判断是否是此个人号的好友
            List<String> idList = new ArrayList<>();
            idList = taskPeopleService.listUserWxIdByPersonalWxId(no.getWxId(), personalNoLableMessageSend.getStartTime(), personalNoLableMessageSend.getEndTime());
            if(null!=idList) {
                num += idList.size();
            }
        }
        //--------------------------------------------------------------------------------------
        int insert = 0;
        personalNoLableMessageSend.setPersonaNolLableMessageStatus("0/"+num);
        if(VerifyUtils.isEmpty(personalNoLableMessageSend.getId())){
            log.info("插入标签消息到数据库");
            if(VerifyUtils.isEmpty(sendTime)) {
                personalNoLableMessageSend.setSendTime(new Date());
            }
            insert = baseMapper.insert(personalNoLableMessageSend);
        }else {
            log.info("更新标签消息到数据库");
            insert = baseMapper.updateById(personalNoLableMessageSend);
        }
        if(insert != 1){
            log.info("添加标签消息到数据库失败");
            throw new RuntimeException("添加标签消息到数据库失败");
        }
        boolean b = false;
        b = personalNoLableMessageSendContentService.batchSave(personalNoLableMessageSend);
        if(!b){
            log.info("批量添加标签消息内容失败");
            throw new RuntimeException("批量添加标签消息内容失败");
        }
        b = messageSendLableNoService.batchSave(personalNoLableMessageSend);
        if(!b){
            log.info("批量添加消息个人号标签数据失败");
            throw new RuntimeException("批量添加消息个人号标签数据失败");
        }
        log.info("添加标签消息到数据库成功");
        //----------------------------------------处理手机任务组和任务------------------------
        List<String> idList = new ArrayList<>();
        for (PersonalNo no : noList) {
            //用来判断是否是此个人号的好友
            idList = taskPeopleService.listUserWxIdByPersonalWxId(no.getWxId(), personalNoLableMessageSend.getStartTime(), personalNoLableMessageSend.getEndTime());
            Iterator<String> iterator = userWxId.iterator();
            while (iterator.hasNext()){
                String next = iterator.next();
                if(idList.contains(next)) {
                    log.info("插入组");
                    PersonalNoPhoneTaskGroup taskGroup = new PersonalNoPhoneTaskGroup();
                    taskGroup.setLableSendId(personalNoLableMessageSend.getId());
                    taskGroup.setTotalStep(personalNoLableMessageSendContentList1.size());
                    taskGroup.setNextStep(1);
                    taskGroup.setStatus("未下发");
                    taskGroup.setTname("发送标签消息");
                    taskGroup.setLableSendId(personalNoLableMessageSend.getId());
                    if(!VerifyUtils.isEmpty(sendTime)){
                        taskGroup.setCreateTime(sendTime);
                        taskGroup.setTaskOrder(1);
                    }else {
                        taskGroup.setTaskOrder(0);
                    }
                    taskGroup.setCurrentRobotId(no.getWxId());//将要发送消息的wxid
                    boolean save = taskGroupService.insert(taskGroup);
                    if(!save){
                        log.info("插入任务组失败");
                        throw new RuntimeException("插入任务组失败");
                    }
                    for (int i = 0; i < personalNoLableMessageSendContentList1.size(); i++) {
                        PersonalNoPhoneTask task = new PersonalNoPhoneTask();
                        task.setStep((i+1));
                        task.setTname("发第" + (i+1) + "条标签消息给" + next);
                        task.setCreateTime(new Date());
                        task.setRobotId(next);
                        task.setContent(personalNoLableMessageSendContentList1.get(i).getContent());
                        task.setContentType(personalNoLableMessageSendContentList.get(i).getContentType());
                        task.setStatus("未下发");
                        //发送消息
                        task.setTaskType(SunTaskType.FRIEND_SEND_MSG);
                        task.setTaskGroupId(taskGroup.getId());
                        boolean save1 = taskService.insert(task);
                        if(!save1){
                            log.info("插入任务失败");
                            throw new RuntimeException("插入任务失败");
                        }
                    }
                    iterator.remove();
                }
            }
        }
        //------------------------------------处理手机任务组和任务结束----------------------------------------
        return true;
    }
    /*
     * 分页查询标签消息列表
     */
    @Override
    public Page<PersonalNoLableMessageSend> pageQuery(Page<PersonalNoLableMessageSend> page, Object o) {
        log.info("分页查找标签消息列表开始");
        EntityWrapper<PersonalNoLableMessageSend> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        List<PersonalNoLableMessageSend> personalNoLableMessageSends = baseMapper.selectPage(page, entityWrapper);
        page.setRecords(personalNoLableMessageSends);
        if(!VerifyUtils.collectionIsEmpty(page.getRecords())){
            log.info("根据标签消息id查询标签消息内容开始");
            for (PersonalNoLableMessageSend record : page.getRecords()) {
                List<PersonalNoLableMessageSendContent> lableMessageSendContentList =  personalNoLableMessageSendContentService.listByLableMessageId(record.getId());
                record.setPersonalNoLableMessageSendContentList(lableMessageSendContentList);
                Integer num = 0;
                if(lableMessageSendContentList.size()>0) {
                    num = Integer.parseInt(record.getPersonaNolLableMessageStatus()) / lableMessageSendContentList.size();
                }
                record.setPersonaNolLableMessageStatus(num.toString() + "/" + record.getSendNum());
            }
            log.info("根据标签消息id查询标签消息内容结束");
        }
        log.info("分页查找标签消息列表结束");
        return page;
    }
    /*
     * 根据任务id查询任务消息
     */
    @Override
    public PersonalNoLableMessageSend getLableMessageById(Integer id) {
        log.info("数据库根据id查询标签消息");
        PersonalNoLableMessageSend lableMessageSend = baseMapper.selectById(id);
        if(!VerifyUtils.isEmpty(lableMessageSend)){
            log.info("根据标签消息id查询标签消息内容");
            List<PersonalNoLableMessageSendContent> lableMessageSendContentList = personalNoLableMessageSendContentService.listByLableMessageId(lableMessageSend.getId());
            lableMessageSend.setPersonalNoLableMessageSendContentList(lableMessageSendContentList);
            log.info("根据标签消息id查询标签列表");
            List<PersonalNoLableMessageSendLableNo> messageSendLableNoList = messageSendLableNoService.listByMessageSendId(lableMessageSend.getId());
            Set<PersonalNo> noSet = new HashSet<>();
            if(!VerifyUtils.collectionIsEmpty(messageSendLableNoList)){
                for (PersonalNoLableMessageSendLableNo messageSendLableNo : messageSendLableNoList) {
                    PersonalNo no = noService.selectById(messageSendLableNo.getPersonalNoId());
                    noSet.add(no);
                }
            }
            String[] split = lableMessageSend.getPersonalNoLableMessageLableList().split("\\|");
            List<PersonalNo> noList = new ArrayList<>();
            noList.addAll(noSet);
            lableMessageSend.setNoList(noList);
            lableMessageSend.setLableList(Arrays.asList(split));
        }
        log.info("数据库根据ID查询标签消息结束");
        return lableMessageSend;
    }

}
