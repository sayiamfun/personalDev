package com.warm.system.controller;


import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoLogInfo;
import com.warm.system.entity.PersonalNoRoad;
import com.warm.system.service.db1.PersonalNoLogInfoService;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
import com.warm.utils.JsonObjectUtils;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dgd123
 * @since 2019-04-25
 */
@CrossOrigin //跨域
@Api(description = "操作日志")
@RestController
@RequestMapping("/personalNoLogInfo")
public class PersonalNoLogInfoController {

    @Autowired
    private PersonalNoLogInfoService logInfoService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private String DBLogInfo = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_log_info);

    @ApiOperation(value = "添加日志")
    @PostMapping(value = "addLog")
    public R addRoad(
            @ApiParam(name = "loInfo", value = "通道", required = true)
            @RequestBody PersonalNoLogInfo logInfo, HttpServletRequest request
            ){
        try {
            if(VerifyUtils.isEmpty(logInfo)){
                return R.error().message("参数为空");
            }
            logInfo.setDb(DBLogInfo);
            logInfoService.add(logInfo);
            return R.ok();
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(logInfo), "添加日志异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

}

