package com.warm.system.controller;


import com.warm.entity.R;
import com.warm.system.entity.PersonalNoKeywordContent;
import com.warm.system.service.db1.PersonalNoKeywordContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
@Api(description = "关键词内容管理")
@RestController
@RequestMapping("/personalNoKeywordContent")
public class PersonalNoKeywordContentController {
    private static Log log = LogFactory.getLog(PersonalNoKeywordContentController.class);
    @Autowired
    private PersonalNoKeywordContentService keywordContentService;

    @ApiOperation(value = "根据关键词id查询内容")
    @GetMapping("/{keyWordId}/")
    public R listUserByPersonalWxIdAndNickName(
            @ApiParam(name = "keyWordId", value = "关键词Id", required = true)
            @PathVariable("keyWordId") Integer keyWordId
    ){
        try {
            log.info("开始查询对应个人号的好友列表");
            List<PersonalNoKeywordContent> personalNoKeywordContents = keywordContentService.listByKeywordId(keyWordId);
            return R.ok().data(personalNoKeywordContents);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

}

