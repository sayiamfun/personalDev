package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.R;
import com.warm.entity.requre.PeopleNumReq;
import com.warm.system.entity.PersonalNoLableMessageSend;
import com.warm.system.service.db1.PersonalNoLableMessageSendService;
import com.warm.system.service.db1.PersonalNoPeopleService;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(description = "标签消息发送管理")
@RestController
@RequestMapping("/personalNoLableMessageSend")
public class PersonalNoLableMessageSendController {
    private static Log log = LogFactory.getLog(PersonalNoLableMessageSendController.class);
    @Autowired
    private PersonalNoLableMessageSendService personalNoLableMessageSendService;
    @Autowired
    private PersonalNoPeopleService noPeopleService;

    @ApiOperation(value = "添加标签消息")
    @PostMapping(value = "lableMessageSend")
    public R lableMessageSend(
            @ApiParam(name = "personalNoLableMessageSend", value = "要插入的标签消息", required = true)
            @RequestBody PersonalNoLableMessageSend personalNoLableMessageSend){
        try {
            log.info("开始添加标签消息");
            if(VerifyUtils.isEmpty(personalNoLableMessageSend)){
                log.info("要添加的标签消息为空");
                return R.error().message("要添加的标签消息为空");
            }

            boolean b = personalNoLableMessageSendService.insertLableMessage(personalNoLableMessageSend);
            if(!b){
                log.info("添加标签消息失败");
                return R.error().message("添加标签消息失败");
            }
            log.info("添加标签消息成功");
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "分页查找标签消息")
    @PostMapping(value = "{pageNum}/{size}/")
    public R pageQuery(
            @ApiParam(name = "pageNum", value = "当前页码", required = true)
            @PathVariable Long pageNum,

            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable Long size
    ){
        try {
            log.info("分页查找标签消息开始");
            Page<PersonalNoLableMessageSend> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            page = personalNoLableMessageSendService.pageQuery(page, null);
            log.info("分页查找标签消息结束");
            return R.ok().data(page);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "根据id查询标签消息")
    @PostMapping(value = "/{id}/")
    public R getLableMessageById(
            @ApiParam(name = "id", value = "标签消息ID", required = true)
            @PathVariable Integer id
    ){
        try {
            log.info("根据id查询任务消息");
            PersonalNoLableMessageSend lableMessageSend =  personalNoLableMessageSendService.getLableMessageById(id);
            log.info("根据id查询任务消息结束");
            return R.ok().data(lableMessageSend);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message(e.getMessage());
        }
    }

    @ApiOperation(value = "根据标签集合和个人号集合查询粉丝人数（去重）")
    @PostMapping(value = "getPeopleNum")
    public R getPeopleNum(
            @ApiParam(name = "peopleNumReq", value = "求情参数", required = true)
            @RequestBody PeopleNumReq peopleNumReq
    ){
        try {
            log.info("根据标签集合和个人号集合查询粉丝人数（去重）");
            if(!VerifyUtils.isEmpty(peopleNumReq)){
                log.info("请求参数为空");
            }
            List<String> peopleWxIdList = noPeopleService.getByNoListAndLableNameList(peopleNumReq);
            return R.ok().data(peopleWxIdList.size());
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新后重试。。。");
        }
    }

}

