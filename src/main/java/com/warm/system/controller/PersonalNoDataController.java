package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;

import com.warm.entity.R;
import com.warm.entity.query.QueryPersonalData;
import com.warm.entity.result.ResultPersonalData;
import com.warm.system.entity.PersonalNoData;
import com.warm.system.service.db1.PersonalNoDataService;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liwenjie
 * @since 2019-03-19
 */
@CrossOrigin //跨域
@Api(description = "个人号任务数据统计")
@RestController
@RequestMapping("/data")
public class PersonalNoDataController {

    public static Log log = LogFactory.getLog(PersonalNoDataController.class);
    @Autowired
    private PersonalNoDataService dataService;

    @ApiOperation(value = "查询总体数据")
    @PostMapping("pagePersonalDate")
    public R pagePersonalDate(
            @ApiParam(name = "queryPersonalData", value = "查询参数", required = true)
            @RequestBody QueryPersonalData queryPersonalData
    ){
        try {
            log.info("分页条件查询个人号任务数据");
            List<PersonalNoData> list = dataService.listAll(queryPersonalData);
            ResultPersonalData resultPersonalData = dataService.getInfoByDateList(list);
            return R.ok().data(resultPersonalData);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "查询总体数据")
    @PostMapping("pagePersonalDate/{flag}/")
    public R pagePersonalDate(
            @ApiParam(name = "flag", value = "查询标识", required = true)
            @PathVariable String flag,

            @ApiParam(name = "queryPersonalData", value = "查询参数", required = true)
            @RequestBody QueryPersonalData queryPersonalData
    ){
        try {
            log.info("分页条件查询个人号任务数据");
            List<PersonalNoData> list = dataService.listAllAsc(queryPersonalData);
            Map<String,List<String>> dateList = dataService.getDateByDateList(flag, list);
            return R.ok().data(dateList);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }


}

