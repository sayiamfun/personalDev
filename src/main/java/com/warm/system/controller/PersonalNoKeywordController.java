package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoKeyword;
import com.warm.system.entity.PersonalNoKeywordContent;
import com.warm.system.service.db1.PersonalNoAndKeywordService;
import com.warm.system.service.db1.PersonalNoKeywordService;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
import com.warm.system.service.db1.PersonalNoTaskAndKeywordService;
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

import javax.servlet.http.HttpServletRequest;
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
@Api(description = "关键词管理")
@RestController
@RequestMapping("/personalNoKeyword")
public class PersonalNoKeywordController {
    private static Log log = LogFactory.getLog(PersonalNoKeywordController.class);
    @Autowired
    private PersonalNoKeywordService keywordService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;
    @Autowired
    private PersonalNoTaskAndKeywordService taskAndKeywordService;
    @Autowired
    private PersonalNoAndKeywordService noAndKeywordService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private String DBKeyword = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_keyword);
    private String DBTaskAndKeyword = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_and_keyword);
    private String DBNoAndKeyword = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_and_keyword);

    @ApiOperation(value = "根据关键词分页查询")
    @GetMapping("/{keyWord}/{pageNum}/{size}/")
    public R listUserByPersonalWxIdAndNickName(
            @ApiParam(name = "keyWord", value = "关键词", required = true)
            @PathVariable("keyWord")String keyWord,

            @ApiParam(name = "pageNum", value = "第几页", required = true)
            @PathVariable("pageNum")Long pageNum,

            @ApiParam(name = "size", value = "每页条数", required = true)
            @PathVariable("size")Long size,
            HttpServletRequest request
    ){
        try {
            log.info("根据关键词分页查询");
            Page<PersonalNoKeyword> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            page = keywordService.pageQuery(page, keyWord);
            log.info("根据关键词分页查询结束");
            return R.ok().data(page);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(keyWord)+" "+pageNum+" "+size, "根据关键词分页查询异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "添加或修改关键字（有id修改，无id添加）")
    @PostMapping("addKeyWord")
    public R addKeyWord(
            @ApiParam(name = "keyword", value = "关键词信息", required = true)
            @RequestBody @Valid PersonalNoKeyword keyword, BindingResult bindingResult,HttpServletRequest request
            ){
        try {
            log.info("Valid参数验证");
            if(!VerifyUtils.collectionIsEmpty(bindingResult.getAllErrors())){
            	return R.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage().toString());
            }
            String s = verifyKeyword(keyword);
            if(!"true".equals(s)){
                return R.error().message(s);
            }
            int b = keywordService.add(keyword);
            if(b==0){
                return R.error().message("添加关键词失败");
            }
            return R.ok().data("");
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(keyword), "添加或修改关键字异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据id获取关键词信息")
    @PostMapping("{keyWordId}")
    public R getBYId(
            @ApiParam(name = "keyWordId", value = "关键词id", required = true)
            @PathVariable("keyWordId") Integer keyWordId,HttpServletRequest request
    ){
        try {
            log.info("根据id获取关键词信息");
            String sql = DaoGetSql.getById(DBKeyword, keyWordId);
            PersonalNoKeyword one = keywordService.getOne(sql);
            if(VerifyUtils.isEmpty(one)){
                return R.ok().data("");
            }
            return R.ok().data(keywordService.getInfoById(one));
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(keyWordId), "根据id获取关键词信息异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据id删除关键词信息")
    @DeleteMapping("{keyWordId}")
    public R deleteById(
            @ApiParam(name = "keyWordId", value = "关键词id", required = true)
            @PathVariable("keyWordId") Integer keyWordId,HttpServletRequest request
    ){
        try {
            String getSql = DaoGetSql.getSql("SELECT count(*) FROM "+DBTaskAndKeyword+" WHERE `keyword_id` = ?",keyWordId);
            Sql sql = new Sql(getSql);
            Long count = taskAndKeywordService.countBySql(sql);
            if(count.intValue()>0){
                return R.error().message("有任务使用此关键词，不能删除哦");
            }
            getSql = DaoGetSql.getSql("SELECT COUNT(*) FROM "+DBNoAndKeyword+" WHERE `keyword_id` = ?",keyWordId);
            sql.setSql(getSql);
            count = noAndKeywordService.countBySql(sql);
            if(count.intValue()>0){
                return R.error().message("有个人号使用此关键词，不能删除哦");
            }
            keywordService.deleteById(keyWordId);
            return R.ok().data("");
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(keyWordId), "根据id删除关键词信息异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    private String verifyKeyword(PersonalNoKeyword keyword){
        if(VerifyUtils.collectionIsEmpty(keyword.getKeywordContentList())){
            for (PersonalNoKeywordContent personalNoKeywordContent : keyword.getKeywordContentList()) {
                if(VerifyUtils.isEmpty(personalNoKeywordContent.getContent())){
                    return "关键词内容不能为空";
                }
            }
        }
        return "true";
    }


}

