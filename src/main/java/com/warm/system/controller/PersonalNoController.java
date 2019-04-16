package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.R;
import com.warm.entity.query.QueryPersonal;
import com.warm.entity.requre.BatchUpdateObject;
import com.warm.entity.result.Num;
import com.warm.system.entity.*;
import com.warm.system.service.db1.PersonalNoFriendsService;
import com.warm.system.service.db1.PersonalNoService;
import com.warm.system.service.db1.PersonalNoTaskService;
import com.warm.system.service.db1.PersonalNoUserService;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@CrossOrigin //跨域
@Api(description = "个人号管理")
@RestController
@RequestMapping("/personalManager")
public class PersonalNoController {
    private static Log log = LogFactory.getLog(PersonalNoController.class);
    @Autowired
    private PersonalNoService noService;
    @Autowired
    private PersonalNoTaskService taskService;
    @Autowired
    private PersonalNoFriendsService friendsService;
    @Autowired
    private PersonalNoUserService userService;

    @ApiOperation(value = "根据标签id查询所有个人号列表")
    @GetMapping("/{lableId}/")
    public R listByLableId(
            @ApiParam(name = "lableId", value = "查询类别", required = true)
            @PathVariable("lableId") Integer lableId
    ){
        try {
            log.info("根据标签名称查询个人号");
            Set<PersonalNo> list = noService.listByLableName(lableId);
            log.info("根据标签名称查询个人号结束");
            return R.ok().data(list);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新后重试。。。");
        }
    }

    @ApiOperation(value = "查询所有个人号列表")
    @GetMapping
    public R list(){
        try {
            log.info("开始查询所有个人号");
            List<PersonalNo> personalList = noService.selectList(null);
            log.info("查询成功返回数据列表");
            return R.ok().data(personalList);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "根据昵称查询所有个人号列表")
    @GetMapping("getByNickName/{nickName}/")
    public R listByBickName(@PathVariable("nickName") String nickName){
        try {
            log.info("开始查询所有个人号");
            List<PersonalNo> personalList = noService.ListByNickName(nickName);
            log.info("查询成功返回数据列表");
            return R.ok().data(personalList);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "根据条件查询所有个人号列表（flag为1，根据类别。flag为0，根据昵称）(type为1 ，个人号。type为0，朋友圈。type为3，返回个人号列表。)")
    @GetMapping(value = "getByCategory/{category}/{flag}/{type}/")
    public R list(
            @ApiParam(name = "flag", value = "根据什么参数查询标志", required = true)
            @PathVariable("flag") String flag,

            @ApiParam(name = "type", value = "根据什么参数查询标志", required = true)
            @PathVariable("type") String type,

            @ApiParam(name = "category", value = "查询类别", required = true)
            @PathVariable("category") String category
    ){
        try {
            List<PersonalNo> personalList = new ArrayList<>();
            if("1".equals(flag)){
                log.info("开始根据类别查询个人号");
                personalList = noService.listByCategory(category);
                log.info("根据类别查询个人号成功,返回数据");
            }else {
                log.info("开始根据昵称查询个人号");
                personalList = noService.listByNickName(category);
            }
            if (!VerifyUtils.collectionIsEmpty(personalList)) {
                if("1".equals(type)) {
                    log.info("将个人号类型  转换成任务添加的  任务个人号类型");
                    List<PersonalNoTaskPersonal> personalListResult = new ArrayList<>();
                    for (PersonalNo no : personalList) {
                        PersonalNoTaskPersonal personalNoTaskPersonal = new PersonalNoTaskPersonal();
                        personalNoTaskPersonal.setPersonalNoWxId(no.getWxId());
                        personalNoTaskPersonal.setPersonalNoName(no.getNickname());
                        personalNoTaskPersonal.setEquipmentId(no.getEquipmentId());
                        personalListResult.add(personalNoTaskPersonal);
                    }
                    log.info("装换为任务个人号成功");
                    return R.ok().data(personalListResult);
                }else if("2".equals(type)){
                    log.info("将个人号类型  转换成朋友圈添加的  朋友圈个人号类型");
                    List<PersonalNoFriendsCirclePersonal> personalListResult = new ArrayList<>();
                    for (PersonalNo no : personalList) {
                        PersonalNoFriendsCirclePersonal circlePersonal = new PersonalNoFriendsCirclePersonal();
                        circlePersonal.setPersonalNoId(no.getId());
                        circlePersonal.setPersonalNoName(no.getNickname());
                        personalListResult.add(circlePersonal);
                    }
                    log.info("转换为朋友圈个人号成功");
                    return R.ok().data(personalListResult);
                }else {
                    return R.ok().data(personalList);
                }
            }else {
                return R.ok();
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "分页个人号列表")
    @PostMapping(value = "{pageNum}/{size}/")
    public R pageQuery(
            @ApiParam(name = "pageNum", value = "当前页码", required = true)
            @PathVariable Long pageNum,

            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable Long size,

            @ApiParam(name = "searchObj", value = "查询条件", required = true)
            @RequestBody QueryPersonal searchObj
    ){
        try {
            log.info("开始条件查询个人号");
            Page<PersonalNo> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            page = noService.pageQuery(page, searchObj);
            log.info("分页条件查找个人号成功,返回数据");
            return R.ok().data(page);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "根据id编辑个人号")
    @PutMapping(value = "updatePersonal/{personalId}/")
    public R updatePersonal(
            @ApiParam(name = "personalId", value = "要修改的个人号id", required = true)
            @PathVariable("personalId") Integer personalId,

            @ApiParam(name = "no", value = "要修改的个人号信息", required = true)
            @RequestBody PersonalNo no
    ){
        try {
            log.info("开始根据id修改个人号信息");
            if (VerifyUtils.isEmpty(personalId)){
                log.info("要修改的个人号id为空");
                return R.error().message("要修改的个人号id为空");
            }
            if(VerifyUtils.isEmpty(no)){
                log.info("要修改的个人号信息为空");
                return R.error().message("要修改的个人号信息为空");
            }
            PersonalNo byId = noService.selectById(personalId);
            if(!VerifyUtils.isEmpty(byId)){
                log.info("开始修改个人号数据");
                byId.setPersonalNoCategory(no.getPersonalNoCategory());
                byId.setSalesGroup(no.getSalesGroup());
                boolean b = noService.updateById(byId);
                if(!b){
                    log.info("根据id修改个人号信息失败");
                    return R.error().message("根据id修改个人号信息失败");
                }
            }
            log.info("根据id修改个人号信息成功");
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "查询首页三个总数数据")
    @GetMapping(value = "Num")
    public R Num(){
        try {
            log.info("开始查询首页显示的三个总数据");
            Num num = new Num();
            List<PersonalNo> personalList = noService.selectList(null);
            if(!VerifyUtils.collectionIsEmpty(personalList)){
                Integer friendsNum = 0;
                log.info("个人号集合查询成功,开始计算好友数量");
                for (PersonalNo no : personalList) {
                    friendsNum += no.getFriendsNum();
                }
                log.info("计算完成将数据返回");
                num.setTotal(personalList.size());
                num.setFriendsNum(friendsNum);
            }else {
                num.setTotal(0);
                num.setFriendsNum(0);
            }
            //查询承担的活动总数量(正在进行的活动并且参与个人号数量不为0)
            int taskNum = taskService.getOnGoingTaskNum();
            num.setActivityNum(taskNum);
            log.info("开始查询首页显示的三个总数据结束");
            return R.ok().data(num);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "根据个人号id查询个人号下的所有好友数量")
    @PutMapping(value = "getUserBuPersonal/{personalId}/")
    public R getUserByPersonal(
            @ApiParam(name = "personalId", value = "个人号id", required = true)
            @PathVariable("personalId") Integer personalId
    ){
        try {
            log.info("查询此个人号下的所有好友列表");
            List<PersonalNoFriends> personalNoFriends = friendsService.listByPersonalId(personalId);
            Set<PersonalNoUser> userSet = new HashSet<>();
            log.info("根据好友表的好友id查询好友详细信息");
            for (PersonalNoFriends personalNoFriend : personalNoFriends) {
                PersonalNoUser byId = userService.selectById(personalNoFriend.getUserId());
                userSet.add(byId);
            }
            boolean b = noService.updateFriends(personalId);
            log.info("查询结束");
            return R.ok().data(userSet);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "批量修改个人号类别")
    @PutMapping(value = "batchUpdatePersonalCategory")
    public R batchUpdatePersonalCategory(
            @ApiParam(name = "batchUpdateObject", value = "批量修改个人号类别参数", required = true)
            @RequestBody BatchUpdateObject batchUpdateObject
    ){
        try {
            if(VerifyUtils.isEmpty(batchUpdateObject)){
                return R.error().message("参数不能为空");
            }
            log.info("开始批量修改个人号类别");
            boolean b = noService.batchUpdateCategory(batchUpdateObject.getPersonalList(),batchUpdateObject.getObject());
            if(!b){
                log.info("批量修改个人号类别失败");
                return R.error().message("批量修改个人号类别失败");
            }
            log.info("批量修改个人号数量成功");
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "批量修改个人号销售组")
    @PutMapping(value = "batchUpdatePersonalGroup")
    public R batchUpdatePersonalGroup(
            @ApiParam(name = "batchUpdateObject", value = "批量修改个人号销售组参数", required = true)
            @RequestBody BatchUpdateObject batchUpdateObject
    ){
        try {
            if(VerifyUtils.isEmpty(batchUpdateObject)){
                return R.error().message("参数不能为空");
            }
            log.info("开始批量修改个人号销售组");
            boolean b = noService.batchUpdateGroup(batchUpdateObject.getPersonalList(),batchUpdateObject.getObject());
            if(!b){
                log.info("批量修改个人号销售组失败");
                return R.error().message("批量修改个人号销售组失败");
            }
            log.info("批量修改个人号销售组成功");
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

}

