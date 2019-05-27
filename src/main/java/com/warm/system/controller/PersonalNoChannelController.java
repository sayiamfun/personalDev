package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.system.entity.PersonalNoChannel;
import com.warm.system.service.db1.PersonalNoChannelService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *  前端控制器
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

    private String DBChannel = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_channel);


    @ApiOperation(value = "分页渠道列表")
    @GetMapping(value = "{pageNum}/{size}/")
    public R pageQuery(
            @ApiParam(name = "pageNum", value = "当前页码", required = true)
            @PathVariable Long pageNum,

            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable Long size
    ){
        try {
            log.info("分页渠道列表开始");
            Page<PersonalNoChannel> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            log.info("数据库查找渠道列表");
            String sql = DaoGetSql.getSql("select * from " + DBChannel + " where deleted = 0 order by id desc limit ?,?",page.getOffset(),page.getLimit());
            List<PersonalNoChannel> list = personalnoChannelService.list(sql);
            sql = DaoGetSql.getSql("select count(*) from " + DBChannel+" where deleted = 0");
            Long count = personalnoChannelService.getCount(sql);
            page.setTotal(count.intValue());
            page.setRecords(list);
            log.info("数据库查找分页列表成功,返回数据");
            return R.ok().data(page);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "查询所有渠道列表")
    @GetMapping
    public R list(){
        try {
            log.info("查询所有渠道列表开始");
            String sql = DaoGetSql.getSql("select * from " + DBChannel + " where deleted = 0 order by id desc");
            List<PersonalNoChannel> list = personalnoChannelService.list(sql);
            log.info("查询所有渠道列表成功,返回数据");
            return R.ok().data(list);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "个人号任务页面查询所有渠道列表")
    @GetMapping(value = "taskChannelList")
    public R taskChannelList(){
        try {
            log.info("个人号任务页面查询所有渠道列表开始");
            String sql = DaoGetSql.getSql("select channel_name from " + DBChannel + " where deleted = 0 order by id desc");
            List<String> list = personalnoChannelService.listString(sql);
            log.info("个人号任务页面查询所有渠道列表成功,返回数据");
            return R.ok().data(list);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "添加渠道")
    @PostMapping("addChannel")
    public R addChannel(
            @ApiParam(name = "personalnoChannel", value = "添加渠道对象", required = true)
            @RequestBody @Valid PersonalNoChannel personalnoChannel
    ){
        try {
            log.info("添加渠道开始");
            if(VerifyUtils.isEmpty(personalnoChannel)){
                log.info("待添加渠道信息为空,添加失败");
                return R.error().message("待添加的渠道信息为空");
            }
            /*
             * 如果存在id则做修改操作
             * 如果不存在id则作
             */
//            personalnoChannel.setDb(DBChannel);
//            personalnoChannel.setDeleted(0);
//            Integer add = personalnoChannelService.add(personalnoChannel);
//            if(add==0){
//                return R.error().message("添加渠道信息到数据库失败");
//            }
            log.info("添加渠道到数据库成功");
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "根据id查找渠道")
    @GetMapping("{channelId}")
    public R selectChannelById(
            @ApiParam(name = "channelId", value = "渠道id", required = true)
            @PathVariable("channelId")Long channelId
    ){
        try {
            log.info("根据id查找渠道开始");
            if(VerifyUtils.isEmpty(channelId)){
                log.info("id为空,查找失败");
                return R.error().message("查找id为空");
            }
            String sql = DaoGetSql.getSql("select * from " + DBChannel + " where id = ? and deleted = 0",channelId);
            PersonalNoChannel channel = personalnoChannelService.getOne(sql);
            log.info("根据id查找渠道成功返回数据");
            return R.ok().data(channel);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }


    @ApiOperation(value = "根据id删除渠道")
    @DeleteMapping("{channelId}")
    public R deleteChannelById(
            @ApiParam(name = "channelId", value = "待删除渠道对象id", required = true)
            @PathVariable("channelId")Long channelId
    ){
        try {
            log.info("根据id删除渠道开始");
            if(VerifyUtils.isEmpty(channelId)){
                log.info("id为空,删除失败");
                return R.error().message("待删除渠道id为空");
            }
            String sql = DaoGetSql.getSql("delete from " + DBChannel + " where id = ?", channelId);
            personalnoChannelService.delete(sql);
            log.info("根据id删除渠道成功");
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }


}

