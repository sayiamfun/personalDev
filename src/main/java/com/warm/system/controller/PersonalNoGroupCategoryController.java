package com.warm.system.controller;


import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.robot.CreateGroupCategoryEntity;
import com.warm.entity.robot.G;
import com.warm.entity.robot.GroupCategory;
import com.warm.entity.robot.ResponseInfo;
import com.warm.system.entity.PersonalNoGroupCategory;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
import com.warm.system.service.db1.PersonalNoWxUserService;
import com.warm.system.service.db3.PersonalNoGroupCategoryService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.HttpClientUtil;
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
import java.util.Date;
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
@Api(description = "群类别管理")
@RestController
@RequestMapping("/groupCategory")
public class PersonalNoGroupCategoryController {

    @Autowired
    private PersonalNoWxUserService wxUserService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private static Log log = LogFactory.getLog(PersonalNoGroupCategoryController.class);
    private String DBGroupCategory = DB.DBAndTable(DB.PERSONAL_ZC_WX_GROUP,DB.group_category);
    private String QUNLIEBIAN = DB.DBAndTable(DB.QUNLIEBINA_01,DB.group_category);
    private String DBWxUser = DB.DBAndTable(DB.QUNLIEBINA_01,DB.wx_user);
    @Autowired
    private PersonalNoGroupCategoryService groupCategoryService;

    @ApiOperation(value = "根据类别集合id查询所有的群类别")
    @GetMapping("/{setId}/{flag}/")
    public R listBySetId(
            @ApiParam(name = "setId", value = "类别集合id", required = true)
            @PathVariable("setId") Integer setId,

            @ApiParam(name = "flag", value = "数据库表识", required = true)
            @PathVariable("flag") Integer flag,
            HttpServletRequest request
    ){
        try {
            log.info("根据群类别集合id查询所有的群类别");
            String database = DBGroupCategory;
            switch (flag){
                case 0:
                    break;
                case 1:
                    database = QUNLIEBIAN;
                    break;
            }
            String sql = DaoGetSql.getSql("select * from " + database + " where `group_category_set_id` = ? order by id desc", setId);
            List<PersonalNoGroupCategory> list = groupCategoryService.list(sql);
            log.info("根据群类别集合id查询所有的群类别结束");
            return R.ok().data(list);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(setId)+" "+flag, "根据类别集合id查询所有的群类别异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "添加群类别")
    @PostMapping("addGroupCategory")
    public R listBySetId(
            @RequestBody @Valid PersonalNoGroupCategory personalNoGroupCategory, BindingResult bindingResult, HttpServletRequest request
            ){
        try {
            log.info("Valid参数验证");
            if(!VerifyUtils.collectionIsEmpty(bindingResult.getAllErrors())){
                return R.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage().toString());
            }
            log.info("拼接U助手微信id");
            List<String> personalWxidList = personalNoGroupCategory.getPersonalWxidList();
            String temp = "";
            for (String s : personalWxidList) {
                temp+=s+"||";
            }
            GroupCategory groupCategory = new GroupCategory(null,personalNoGroupCategory.getGroupCategorySetId(),personalNoGroupCategory.getCname(),personalNoGroupCategory.getUpLimit(),personalNoGroupCategory.getPrefix(),personalNoGroupCategory.getPostfix(),personalNoGroupCategory.getBeginIndex(),personalNoGroupCategory.getCurrentIndex(),personalNoGroupCategory.getAssistantIds(),personalNoGroupCategory.getFullVerify(),personalNoGroupCategory.getCdescription(),new Date(),temp,null);
            CreateGroupCategoryEntity createGroupCategoryEntity = new CreateGroupCategoryEntity();
            createGroupCategoryEntity.group_category_set_id=personalNoGroupCategory.getGroupCategorySetId();
            createGroupCategoryEntity.group_category = groupCategory;
            createGroupCategoryEntity.assistantList = personalNoGroupCategory.getAssistantList();
            createGroupCategoryEntity.empty_group_count  = personalNoGroupCategory.getGroupTotal();
            String s = HttpClientUtil.sendPost("http://www.youyoudk.cn/SpringBootService/createGroupCategory", JsonObjectUtils.objectToJson(createGroupCategoryEntity));
            ResponseInfo responseInfo = JsonObjectUtils.jsonToPojo(s, ResponseInfo.class);
            if(responseInfo.code!=0){
                return R.error().message("创建失败");
            }
            log.info("添加结束");
            return R.ok().data("");
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(personalNoGroupCategory), "添加群类别异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "U助手集合")
    @PostMapping("listU")
    public R listU(HttpServletRequest request){
        try {
            String getSql = DaoGetSql.getSql("select nick_name from "+DBWxUser+" where is_assistant = 1");
            List<String> list = wxUserService.listBySql(new Sql(getSql));
            return R.ok().data(list);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, "", "U助手集合异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

}

