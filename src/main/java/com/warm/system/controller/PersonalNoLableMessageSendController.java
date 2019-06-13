package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.requre.PeopleNumReq;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoLableMessageSend;
import com.warm.system.entity.PersonalNoPeople;
import com.warm.system.entity.PersonalNoSuperuesr;
import com.warm.system.service.db1.PersonalNoLableMessageSendService;
import com.warm.system.service.db1.PersonalNoPeopleService;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.JsonObjectUtils;
import com.warm.utils.VerifyUtils;
import com.warm.utils.WebConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@CrossOrigin //跨域
@Api(description = "标签消息发送管理")
@RestController
@RequestMapping("/personalNoLableMessageSend")
public class PersonalNoLableMessageSendController {
    private static Log log = LogFactory.getLog(PersonalNoLableMessageSendController.class);
    @Autowired
    private PersonalNoLableMessageSendService personalNoLableMessageSendService;
    @Autowired
    private PersonalNoPeopleService noPeopleService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    //指定唯一锁
    private Lock lock = new ReentrantLock();    //注意这个地方

    @ApiOperation(value = "添加标签消息")
    @PostMapping(value = "lableMessageSend")
    public R lableMessageSend(
            @ApiParam(name = "personalNoLableMessageSend", value = "要插入的标签消息", required = true)
            @RequestBody PersonalNoLableMessageSend personalNoLableMessageSend, HttpServletRequest request
            ){
        if(lock.tryLock()) {
            try {
                log.info("开始添加标签消息");
                if(VerifyUtils.collectionIsEmpty(personalNoLableMessageSend.getNoWxIdList())){
                    log.info("要添加的标签消息个人号列表为空");
                    return R.error().message("要添加的标签消息个人号列表为空");
                }
                PersonalNoSuperuesr superuesr = (PersonalNoSuperuesr) request.getAttribute(WebConst.SUPERUSER);
                personalNoLableMessageSend.setSuperId(superuesr.getId());
                boolean b = personalNoLableMessageSendService.insertLableMessage(personalNoLableMessageSend);
                if(!b){
                    log.info("添加标签消息失败");
                    return R.error().message("添加标签消息失败");
                }
                log.info("添加标签消息成功");
                return R.ok();
            } catch (Exception e) {
                G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(personalNoLableMessageSend), "添加标签消息异常", "", 1,e);
                return R.error().message("网页走丢了，请返回重试。。。");
            }finally {
                lock.unlock();
            }
        }else {
            return R.error().message("服务器正忙，请稍等一会再点击添加");
        }
    }

    @ApiOperation(value = "分页查找标签消息")
    @PostMapping(value = "{pageNum}/{size}/")
    public R pageQuery(
            @ApiParam(name = "pageNum", value = "当前页码", required = true)
            @PathVariable Long pageNum,

            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable Long size,
            HttpServletRequest request
    ){
        try {
            log.info("分页查找标签消息开始");
            Page<PersonalNoLableMessageSend> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            page = personalNoLableMessageSendService.pageQuery(page, null);
            log.info("分页查找标签消息结束");
            return R.ok().data(page);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, ""+pageNum+" "+size, "分页查找标签消息异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "根据id查询标签消息")
    @PostMapping(value = "/{id}/")
    public R getLableMessageById(
            @ApiParam(name = "id", value = "标签消息ID", required = true)
            @PathVariable Integer id,HttpServletRequest request
    ){
        try {
            log.info("根据id查询任务消息");
            PersonalNoLableMessageSend lableMessageSend =  personalNoLableMessageSendService.getLableMessageById(id);
            log.info("根据id查询任务消息结束");
            return R.ok().data(lableMessageSend);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, ""+id, "根据id查询标签消息异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "根据标签集合和个人号集合查询粉丝人数（去重）")
    @PostMapping(value = "getPeopleNum")
    public R getPeopleNum(
            @ApiParam(name = "peopleNumReq", value = "求情参数", required = true)
            @RequestBody PeopleNumReq peopleNumReq,HttpServletRequest request
    ){
        try {
            log.info("根据标签集合和个人号集合查询粉丝人数（去重）");
            if(!VerifyUtils.isEmpty(peopleNumReq)){
                log.info("请求参数为空");
            }
            List<PersonalNoPeople> peopleList = noPeopleService.listByLableAndPersonal(peopleNumReq);
            return R.ok().data(peopleList.size());
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(peopleNumReq), "根据标签集合和个人号集合查询粉丝人数异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新后重试。。。");
        }
    }

}

