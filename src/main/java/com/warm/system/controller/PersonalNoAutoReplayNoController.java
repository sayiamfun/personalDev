package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.system.entity.PersonalNo;
import com.warm.system.entity.PersonalNoAutoReplayNo;
import com.warm.system.service.db1.PersonalNoAutoReplayNoService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dgd123
 * @since 2019-04-03
 */
@CrossOrigin //跨域
@Api(description = "不走通道自动回复个人号管理")
@RestController
@RequestMapping("/personalNoAutoReplayNo")
public class PersonalNoAutoReplayNoController {
    @Autowired
    private PersonalNoAutoReplayNoService autoReplayNoService;

    @ApiOperation("分页查询列表(nickName为-1查询所有")
    @GetMapping("listAutoNo/{pageNum}/{size}/{nickName}/")
    public R listAutoNo(
            @ApiParam(name = "pageNum", value = "当前页码", required = true)
            @PathVariable Long pageNum,

            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable Long size,

            @ApiParam(name = "nickName", value = "查询条件", required = true)
            @PathVariable String nickName
    ){
        try {
            Page<PersonalNoAutoReplayNo> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            String sql = DaoGetSql.getSql("select * from " + DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_auto_replay_no) + " order by id desc limit ?,?", page.getOffset(), page.getLimit());
            List<PersonalNoAutoReplayNo> list = autoReplayNoService.list(sql);
            Long count = autoReplayNoService.getCount("select count(*) from " + DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_auto_replay_no));
            page.setRecords(list);
            page.setTotal(count.intValue());
            return R.ok().data(page);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation("添加")
    @PostMapping("add")
    public R add(@RequestBody PersonalNo no){
        try {
            PersonalNoAutoReplayNo autoReplayNo = new PersonalNoAutoReplayNo();
            autoReplayNo.setWxId(no.getWxId());
            autoReplayNo.setNickName(no.getNickname());
            autoReplayNo.setDb(DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_auto_replay_no));
            Integer add = autoReplayNoService.add(autoReplayNo);
            return R.ok().data("");
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation("删除")
    @DeleteMapping("delete/{id}/")
    public R deleteById(@PathVariable("id") Integer id){
        try {
            String sql = DaoGetSql.getSql("delete from " + DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_auto_replay_no) + " where id = ?", id);
            autoReplayNoService.delete(sql);
            return R.ok();
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }
}

