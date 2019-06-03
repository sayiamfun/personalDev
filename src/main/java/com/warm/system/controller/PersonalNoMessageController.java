package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoMessage;
import com.warm.system.entity.PersonalNoMessageContent;
import com.warm.system.service.db1.PersonalNoMessageContentService;
import com.warm.system.service.db1.PersonalNoMessageService;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.JsonObjectUtils;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liwenjie123
 * @since 2019-05-06
 */
@CrossOrigin //跨域
@Api(description = "个人号消息")
@RestController
@RequestMapping("/personalNoMessage")
public class PersonalNoMessageController {

    private static Log log = LogFactory.getLog(PersonalNoSendMessageController.class);
    @Autowired
    private PersonalNoMessageService messageService;
    @Autowired
    private PersonalNoMessageContentService messageContentService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private String DBMessage = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_message);
    private String DBMessageContent = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_message_content);

    @ApiOperation(value = "根据消息名称分页查询")
    @GetMapping("/{message}/{pageNum}/{size}/")
    public R listUserByPersonalWxIdAndNickName(
            @ApiParam(name = "message", value = "关键词", required = true)
            @PathVariable("message")String message,

            @ApiParam(name = "pageNum", value = "第几页", required = true)
            @PathVariable("pageNum")Long pageNum,

            @ApiParam(name = "size", value = "每页条数", required = true)
            @PathVariable("size")Long size,
            HttpServletRequest request
    ){
        try {
            log.info("根据关键词分页查询");
            Page<PersonalNoMessage> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            page = messageService.pageQuery(page, message);
            log.info("根据关键词分页查询结束");
            return R.ok().data(page);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(message)+" "+pageNum+" "+size, "根据消息名称分页查询异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据消息名称查询所有")
    @GetMapping("list/{message}/")
    public R list(
            @ApiParam(name = "message", value = "关键词", required = true)
            @PathVariable("message")String message,HttpServletRequest request

    ){
        try {
            List<PersonalNoMessage> messageList = messageService.listByMessage(message);
            return R.ok().data(messageList);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(message), "根据消息名称查询所有异常", "", 1,e);
            return R.error().message("网页走丢了。。。请返回重试");
        }
    }

    @ApiOperation(value = "添加或修改关键字（有id修改，无id添加）")
    @PostMapping("addMessage")
    public R addKeyWord(
            @ApiParam(name = "message", value = "消息信息", required = true)
            @RequestBody @Valid PersonalNoMessage message, BindingResult bindingResult,HttpServletRequest request
            ){
        try {
            log.info("Valid参数验证");
            if(!VerifyUtils.collectionIsEmpty(bindingResult.getAllErrors())){
            	return R.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage().toString());
            }
            int b = messageService.add(message);
            if(b==0){
                return R.error().message("添加消息失败");
            }
            return R.ok().data("");
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(message), "添加或修改关键字异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据id获取消息信息")
    @PostMapping("{messageId}")
    public R getBYId(
            @ApiParam(name = "messageId", value = "消息id", required = true)
            @PathVariable("messageId") Integer messageId,HttpServletRequest request
    ){
        try {
            log.info("根据id获取关键词信息");
            String sql = DaoGetSql.getById(DBMessage, messageId);
            PersonalNoMessage one = messageService.getOne(sql);
            sql = DaoGetSql.getSql("SELECT * FROM "+DBMessageContent+" WHERE `message_id` = ? AND deleted = 0",messageId);
            List<PersonalNoMessageContent> messageContentList = messageContentService.listBySql(new Sql(sql));
            one.setMessageContentList(messageContentList);
            return R.ok().data(one);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(messageId), "根据id获取消息信息异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据id删除个人号消息")
    @DeleteMapping("{messageId}")
    public R deleteById(
            @ApiParam(name = "messageId", value = "消息id", required = true)
            @PathVariable("messageId") Integer messageId,HttpServletRequest request
    ){
        try {
            String getSql = DaoGetSql.getSql("update "+DBMessageContent+" set deleted = 1 where message_id = ?",messageId);
            Sql sql = new Sql(getSql);
            messageContentService.deleteBySql(sql);
            getSql = DaoGetSql.getSql("update "+DBMessage+" set deleted = 1 where id = ?",messageId);
            sql.setSql(getSql);
            messageService.deleteBySql(sql);
            return R.ok().data("");
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(messageId), "根据id删除个人号消息异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }



}

