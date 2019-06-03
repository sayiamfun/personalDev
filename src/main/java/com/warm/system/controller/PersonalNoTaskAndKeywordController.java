package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoKeyword;
import com.warm.system.entity.PersonalNoTaskAndKeyword;
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
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liwenjie123
 * @since 2019-04-28
 */
@CrossOrigin //跨域
@Api(description = "任务和关键词对应关系管理")
@RestController
@RequestMapping("/personalNoTaskAndKeyword")
public class PersonalNoTaskAndKeywordController {

    private static Log log = LogFactory.getLog(PersonalNoTaskAndKeywordController.class);

    @Autowired
    private PersonalNoTaskAndKeywordService taskAndKeywordService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private String ZCDBTaskAndKeyWord = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task_and_keyword);

    @ApiOperation("分页查询个人号对应消息")
    @GetMapping("/{taskName}/{keywordName}/{pageNum}/{size}/")
    public R pageQuer(
            @ApiParam(name = "keywordName", value = "关键词名称", required = true)
            @PathVariable("keywordName")String keywordName,

            @ApiParam(name = "taskName", value = "任务名称", required = true)
            @PathVariable("taskName")String taskName,

            @ApiParam(name = "pageNum", value = "第几页", required = true)
            @PathVariable("pageNum")Long pageNum,

            @ApiParam(name = "size", value = "每页条数", required = true)
            @PathVariable("size")Long size,
            HttpServletRequest request
    ){
        try {
            Page<PersonalNoTaskAndKeyword> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            Map<String, String> map = new HashMap<>();
            map.put("taskName",taskName);
            map.put("keywordName",keywordName);
            page = taskAndKeywordService.pageQuery(page, map);
            return R.ok().data(page);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(keywordName)+" "+taskName+" "+pageNum+" "+size, "分页查询个人号对应消息异常", "", 1,e);
            return R.error().message("网页走丢了。。。请返回重试");
        }
    }

    @ApiOperation(value = "添加任务关键词对应关系")
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
            Integer id = taskAndKeywordService.add(keyword);
            if(id<0){
                return R.error().message("添加失败");
            }
            return R.ok();
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(keyword), "添加任务关键词对应关系异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据id删除任务关键词对应关系")
    @DeleteMapping("deleteById/{id}/")
    public R deletebyId(
            @ApiParam(name = "id", value = "id", required = true)
            @PathVariable("id") Integer id,HttpServletRequest request
    ){
        try {
            String getSql = DaoGetSql.getSql("DELETE FROM " + ZCDBTaskAndKeyWord + " where id = ?", id);
            Integer result = taskAndKeywordService.deleteBySql(new Sql(getSql));
            return R.ok().data("");
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(id), "根据id删除任务关键词对应关系异常", "", 1,e);
            return R.error().message("网页走丢了。。。请返回重试");
        }
    }




}

