package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoChannel;
import com.warm.system.service.db1.PersonalNoChannelService;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
import com.warm.system.service.db1.PersonalNoTaskChannelService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.JsonObjectUtils;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@CrossOrigin //跨域
@Api(description = "渠道管理")
@RestController
@RequestMapping("/channelManger")
public class PersonalNoChannelController {
    private static Log log = LogFactory.getLog(PersonalNoChannelController.class);
    @Autowired
    private PersonalNoChannelService personalnoChannelService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;
    @Autowired
    private PersonalNoTaskChannelService taskChannelService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private String DBChannel = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_channel);
    private String DBTaskChannel = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_task_channel);


    @ApiOperation(value = "分页渠道列表")
    @GetMapping(value = "{pageNum}/{size}/")
    public R pageQuery(
            @ApiParam(name = "pageNum", value = "当前页码", required = true)
            @PathVariable Long pageNum,

            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable Long size,
            HttpServletRequest request
    ) {
        try {
            log.info("分页渠道列表开始");
            Page<PersonalNoChannel> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            log.info("数据库查找渠道列表");
            String sql = DaoGetSql.getSql("select * from " + DBChannel + " where deleted = 0 order by id desc limit ?,?", page.getOffset(), page.getLimit());
            List<PersonalNoChannel> list = personalnoChannelService.list(sql);
            sql = DaoGetSql.getSql("select count(*) from " + DBChannel + " where deleted = 0");
            Long count = personalnoChannelService.getCount(sql);
            page.setTotal(count.intValue());
            page.setRecords(list);
            log.info("数据库查找分页列表成功,返回数据");
            return R.ok().data(page);
        } catch (Exception e) {
            G.requestException(DBRequestException, requestExceptionService, request, "" + pageNum + "   " + size, "分页渠道列表异常", "", 1, e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "查询所有渠道列表")
    @GetMapping
    public R list(HttpServletRequest request) {
        try {
            log.info("查询所有渠道列表开始");
            String sql = DaoGetSql.getSql("select * from " + DBChannel + " where deleted = 0 order by id desc");
            List<PersonalNoChannel> list = personalnoChannelService.list(sql);
            log.info("查询所有渠道列表成功,返回数据");
            return R.ok().data(list);
        } catch (Exception e) {
            G.requestException(DBRequestException, requestExceptionService, request, "", "查询所有渠道列表异常", "", 1, e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "个人号任务页面查询所有渠道列表")
    @GetMapping(value = "taskChannelList")
    public R taskChannelList(HttpServletRequest request) {
        try {
            log.info("个人号任务页面查询所有渠道列表开始");
            String sql = DaoGetSql.getSql("select channel_name from " + DBChannel + " where deleted = 0 order by id desc");
            List<String> list = personalnoChannelService.listString(sql);
            log.info("个人号任务页面查询所有渠道列表成功,返回数据");
            return R.ok().data(list);
        } catch (Exception e) {
            G.requestException(DBRequestException, requestExceptionService, request, "", "个人号任务页面查询所有渠道列表异常", "", 1, e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "添加渠道")
    @PostMapping("addChannel")
    @Transactional
    public R addChannel(
            @ApiParam(name = "personalnoChannel", value = "添加渠道对象", required = true)
            @RequestBody @Valid PersonalNoChannel personalnoChannel, BindingResult bindingResult, HttpServletRequest request
    ) {
        try {
            log.info("添加渠道开始");
            if (!VerifyUtils.collectionIsEmpty(bindingResult.getAllErrors())) {
                return R.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage().toString());
            }
            String getSql = DaoGetSql.getSql("SELECT * FROM " + DBChannel + " WHERE `channel_name` = ? AND `deleted` = 0 limit 0,1", personalnoChannel.getChannelName());
            PersonalNoChannel one = personalnoChannelService.getOne(getSql);
            if (!VerifyUtils.isEmpty(one)) {
                if(VerifyUtils.isEmpty(personalnoChannel.getId())) {
                    return R.error().message("此渠道已经存在");
                }else if(one.getId()!=personalnoChannel.getId()){
                    return R.error().message("此渠道已经存在");
                }else if(!VerifyUtils.isEmpty(one.getRemarks()) && one.getRemarks().equals(personalnoChannel.getRemarks())){
                    return R.error().message("您未做任何修改");
                }
            }
            if(!VerifyUtils.isEmpty(personalnoChannel.getId())){
                log.info("修改任务渠道表的渠道名称");
                getSql = DaoGetSql.getSql(" UPDATE "+DBTaskChannel+" SET `channel_name` = ? WHERE `channel_id` = ?",personalnoChannel.getChannelName(),personalnoChannel.getId());
                Sql sql = new Sql(getSql);
                taskChannelService.updateBySql(sql);
            }
            personalnoChannel.setDb(DBChannel);
            personalnoChannel.setDeleted(0);
            Integer add = personalnoChannelService.add(personalnoChannel);
            if (add == 0) {
                return R.error().message("添加渠道信息到数据库失败");
            }
            log.info("添加渠道到数据库成功");
            return R.ok();
        } catch (Exception e) {
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(personalnoChannel), "个人号任务页面查询所有渠道列表异常", "", 1, e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "根据id查找渠道")
    @GetMapping("{channelId}")
    public R selectChannelById(
            @ApiParam(name = "channelId", value = "渠道id", required = true)
            @PathVariable("channelId") Long channelId, HttpServletRequest request
    ) {
        try {
            log.info("根据id查找渠道开始");
            if (VerifyUtils.isEmpty(channelId)) {
                log.info("id为空,查找失败");
                return R.error().message("查找id为空");
            }
            String sql = DaoGetSql.getSql("select * from " + DBChannel + " where id = ? and deleted = 0", channelId);
            PersonalNoChannel channel = personalnoChannelService.getOne(sql);
            log.info("根据id查找渠道成功返回数据");
            return R.ok().data(channel);
        } catch (Exception e) {
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(channelId), "根据id查找渠道异常", "", 1, e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }


    @ApiOperation(value = "根据id删除渠道")
    @DeleteMapping("{channelId}")
    @Transactional
    public R deleteChannelById(
            @ApiParam(name = "channelId", value = "待删除渠道对象id", required = true)
            @PathVariable("channelId") Long channelId, HttpServletRequest request
    ) {
        try {
            log.info("根据id删除渠道开始");
            if (VerifyUtils.isEmpty(channelId)) {
                log.info("id为空,删除失败");
                return R.error().message("待删除渠道id为空");
            }
            String sql = DaoGetSql.getSql("update  " + DBChannel + " set deleted = 1 where id = ?", channelId);
            personalnoChannelService.deleteBySql(new Sql(sql));
            log.info("根据id删除渠道成功");
            return R.ok();
        } catch (Exception e) {
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(channelId), "根据id删除渠道异常", "", 1, e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }


}

