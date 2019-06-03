package com.warm.system.controller;


import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoKeywordContent;
import com.warm.system.service.db1.PersonalNoKeywordContentService;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
import com.warm.utils.JsonObjectUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private String DBKeywordContent = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_keyword_content);

    @ApiOperation(value = "根据关键词id查询内容")
    @GetMapping("/{keyWordId}/")
    public R listUserByPersonalWxIdAndNickName(
            @ApiParam(name = "keyWordId", value = "关键词Id", required = true)
            @PathVariable("keyWordId") Integer keyWordId, HttpServletRequest request
            ){
        try {
            log.info("根据关键词id查询关键词内容");
            String sql = "select * from "+DBKeywordContent+" where personal_no_keyword_id = "+keyWordId+" and deleted = 0";
            List<PersonalNoKeywordContent> personalNoKeywordContents = keywordContentService.list(sql);
            return R.ok().data(personalNoKeywordContents);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(keyWordId), "根据关键词id查询内容异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

}

