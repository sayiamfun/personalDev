package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoFriends;
import com.warm.system.entity.PersonalNoUser;
import com.warm.system.service.db1.PersonalNoFriendsService;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
import com.warm.utils.JsonObjectUtils;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@CrossOrigin //跨域
@Api(description = "个人号好友管理")
@RestController
@RequestMapping("/personalNoFriends")
public class PersonalNoFriendsController {
    private static Log log = LogFactory.getLog(PersonalNoFriendsController.class);
    @Autowired
    private PersonalNoFriendsService friendsService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);

    @ApiOperation(value = "根据个人号wxId分页查询对应的个人号好友信息")
    @GetMapping("/{personalWxId}/{pageNum}/{size}/")
    public R listUserByPersonalWxId(
            @ApiParam(name = "personalWxId", value = "个人号wxId", required = true)
            @PathVariable("personalWxId")String personalWxId,

            @ApiParam(name = "pageNum", value = "第几页", required = true)
            @PathVariable("pageNum")Long pageNum,

            @ApiParam(name = "size", value = "每页条数", required = true)
            @PathVariable("size")Long size,
            HttpServletRequest request
    ){
        try {
            log.info("开始查询对应个人号的好友列表");
            Page<PersonalNoFriends> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            Map<String, String> map = new HashMap<>();
            map.put("personalWxId", personalWxId);
            Page<PersonalNoUser> resultPage = friendsService.pageQuery(page, map);
            log.info("查询结束返回数据");
            return R.ok().data(resultPage);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(personalWxId)+" "+pageNum+" "+size, "根据个人号wxId分页查询对应的个人号好友信息异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据个人号wxId和用户昵称分页查询对应的个人号好友信息")
    @GetMapping("/{personalWxId}/{nickName}/{pageNum}/{size}/")
    public R listUserByPersonalWxIdAndNickName(
            @ApiParam(name = "personalWxId", value = "个人号wxId", required = true)
            @PathVariable("personalWxId")String personalWxId,

            @ApiParam(name = "nickName", value = "用户昵称", required = true)
            @PathVariable("nickName")String nickName,

            @ApiParam(name = "pageNum", value = "第几页", required = true)
            @PathVariable("pageNum")Long pageNum,

            @ApiParam(name = "size", value = "每页条数", required = true)
            @PathVariable("size")Long size,
            HttpServletRequest request
    ){
        try {
            log.info("开始查询对应个人号的好友列表");
            Map<String, String> map = new HashMap<>();
            map.put("personalWxId", personalWxId);
            map.put("nickName", nickName);
            Page<PersonalNoFriends> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            Page<PersonalNoUser> personalNoUserPage = friendsService.pageQuery(page, map);
            log.info("查询结束返回数据");
            return R.ok().data(personalNoUserPage);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(personalWxId)+" "+pageNum+" "+size, "根据个人号wxId和用户昵称分页查询对应的个人号好友信息异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }


    @ApiOperation(value = "根据个人号wxId和用户wxId删除个人号好友信息")
    @PostMapping("delete/{personalWxId}/")
    public R deleteFrieds(
            @ApiParam(name = "personalWxId", value = "个人号微信id", required = true)
            @PathVariable("personalWxId")String personalWxId,

            @ApiParam(name = "users", value = "要删除的好友微信id结合", required = true)
            @RequestBody List<PersonalNoUser> users,
            HttpServletRequest request
    ){
        try {
            if(VerifyUtils.collectionIsEmpty(users)){
                log.error("要删除的好友微信id为空");
                return R.error().message("要删除的好友微信id为空");
            }
            boolean b = friendsService.deleteFriends(personalWxId, users);
            if(!b){
                return R.error().message("删除好友失败");
            }
            return R.ok().data("");
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(users)+" "+personalWxId, "根据个人号wxId和用户wxId删除个人号好友信息异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据个人号wxId和用户wxId将好友加入黑名单信息")
    @PostMapping("black/{personalWxId}/")
    public R black(
            @ApiParam(name = "personalWxId", value = "个人号微信id", required = true)
            @PathVariable("personalWxId")String personalWxId,

            @ApiParam(name = "users", value = "要加入黑名单的好友微信id结合", required = true)
            @RequestBody PersonalNoUser user,
            HttpServletRequest request
    ){
        try {
            if(VerifyUtils.isEmpty(user)){
                log.error("要加入黑名单的好友微信id为空");
                return R.error().message("要加入黑名单的好友微信id为空");
            }
            boolean b = friendsService.blackFriends(personalWxId, user);
            if(!b){
                return R.error().message("拉好友进黑名单失败");
            }
            return R.ok().data("");
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(user)+" "+personalWxId, "根据个人号wxId和用户wxId将好友加入黑名单信息异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

}

