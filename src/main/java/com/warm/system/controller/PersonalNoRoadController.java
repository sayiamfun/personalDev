package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.requre.QueryRoad;
import com.warm.system.entity.PersonalNoRoad;
import com.warm.system.service.db1.PersonalNoRoadService;
import com.warm.system.service.db1.PersonalNoTaskService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@CrossOrigin //跨域
@Api(description = "任务通道管理")
@RestController
@RequestMapping("/create/personalNoRoad")
public class PersonalNoRoadController {
    private static Log log = LogFactory.getLog(PersonalNoRoadController.class);
    @Autowired
    private PersonalNoRoadService roadService;
    @Autowired
    private PersonalNoTaskService noTaskService;

    private String DBTask = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_task);
    private String DBRoad = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_road);

    @ApiOperation(value = "添加通道")
    @PostMapping(value = "addRoad")
    public R addRoad(
            @ApiParam(name = "road", value = "通道", required = true)
            @RequestBody PersonalNoRoad road
    ){
        try {
            log.info("开始添加通道任务");
            if(VerifyUtils.isEmpty(road)){
                return R.error().message("要添加的参数为空");
            }
            road.setDb(DBRoad);
            road.setDeleted(0);
            boolean save = roadService.add(road);
            if(!save){
                return R.error().message("添加通道失败");
            }
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "分页查找通道列表")
    @PostMapping(value = "{pageNum}/{size}/")
    public R listNoTask(
            @ApiParam(name = "pageNum", value = "当前页码", required = true)
            @PathVariable Long pageNum,

            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable Long size,

            @ApiParam(name = "QueryRoad", value = "通道名称", required = true)
            @RequestBody QueryRoad queryRoad
    ){
        try {
            log.info("开始条件分页通道");
            Page<PersonalNoRoad> page = new Page<>(VerifyUtils.setPageNum(pageNum) , VerifyUtils.setSize(size));
            page = roadService.pageQuery(page, queryRoad);
            //返回数据的集合
            log.info("开始查找对应通道任务数量");
            String getSql = "";
            Sql sql = new Sql();
            for (PersonalNoRoad resultRow : page.getRecords()) {
                getSql =  DaoGetSql.getSql("SELECT count(*) FROM " + DBTask + " where road_id = ? and deleted = 0", resultRow.getId());
                sql.setSql(getSql);
                Long count = noTaskService.countBySql(sql);
                resultRow.setTaskNum(count.intValue());
            }
            log.info("条件分页查找个人号任务完成，返回数据");
            return R.ok().data(page);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }
}

