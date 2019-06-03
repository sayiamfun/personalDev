package com.warm.system.controller;


import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoGroupCategorySet;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
import com.warm.system.service.db3.PersonalNoGroupCategorySetService;
import com.warm.utils.DaoGetSql;
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
@Api(description = "群类别集合管理")
@RestController
@RequestMapping("/groupCategorySet")
public class PersonalNoGroupCategorySetController {

    private static Log log = LogFactory.getLog(PersonalNoGroupCategorySetController.class);
    @Autowired
    private PersonalNoGroupCategorySetService groupCategorySetService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private String DBGroupCategory = DB.DBAndTable(DB.PERSONAL_ZC_WX_GROUP,DB.group_category_set);
    private String QUNLIEBIAN_01 = DB.DBAndTable(DB.QUNLIEBINA_01,DB.group_category_set);

    @ApiOperation(value = "查询所有的群类别集合")
    @GetMapping("{index}")
    public R listAll(
            @ApiParam(name = "index", value = "查询表示（几号后台的库)",required = true)
            @PathVariable("index") Integer index, HttpServletRequest request
            ){
        try {
            log.info("查询所有的类别集合");
            String database = DBGroupCategory;
            switch (index){
                case 0:
                    break;
                case 1:
                    database = QUNLIEBIAN_01;
                    break;
            }
            log.info("查询所有的类别集合结束");
            String sql = " select * from "+database+" order by id desc";
            List<PersonalNoGroupCategorySet> list = groupCategorySetService.list(sql);
            return R.ok().data(list);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(index), "查询所有的群类别集合异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

}

