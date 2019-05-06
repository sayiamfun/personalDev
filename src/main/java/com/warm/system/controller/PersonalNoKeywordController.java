package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.system.entity.PersonalNoKeyword;
import com.warm.system.service.db1.PersonalNoKeywordService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "根据关键词分页查询")
    @GetMapping("/{keyWord}/{pageNum}/{size}/")
    public R listUserByPersonalWxIdAndNickName(
            @ApiParam(name = "keyWord", value = "关键词", required = true)
            @PathVariable("keyWord")String keyWord,

            @ApiParam(name = "pageNum", value = "第几页", required = true)
            @PathVariable("pageNum")Long pageNum,

            @ApiParam(name = "size", value = "每页条数", required = true)
            @PathVariable("size")Long size
    ){
        try {
            log.info("根据关键词分页查询");
            Page<PersonalNoKeyword> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            page = keywordService.pageQuery(page, keyWord);
            log.info("根据关键词分页查询结束");
            return R.ok().data(page);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "添加或修改关键字（有id修改，无id添加）")
    @PostMapping("addKeyWord")
    public R addKeyWord(
            @ApiParam(name = "keyword", value = "关键词信息", required = true)
            @RequestBody PersonalNoKeyword keyword
    ){
        try {
            if(VerifyUtils.isEmpty(keyword)){
                return R.error().message("添加参数为空");
            }
            int b = keywordService.add(keyword);
            if(b==0){
                return R.error().message("添加关键词失败");
            }
            return R.ok().data("");
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据id获取关键词信息")
    @PostMapping("{keyWordId}")
    public R getBYId(
            @ApiParam(name = "keyWordId", value = "关键词id", required = true)
            @PathVariable("keyWordId") Integer keyWordId
    ){
        try {
            log.info("根据id获取关键词信息");
            String sql = DaoGetSql.getById(DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_keyword), keyWordId);
            PersonalNoKeyword one = keywordService.getOne(sql);
            return R.ok().data(keywordService.getInfoById(one));
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据id删除关键词信息")
    @DeleteMapping("{keyWordId}")
    public R deleteById(
            @ApiParam(name = "keyWordId", value = "关键词id", required = true)
            @PathVariable("keyWordId") Integer keyWordId
    ){
        try {
            keywordService.deleteById(keyWordId);
            return R.ok().data("");
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "根据id开启关键词信息")
    @GetMapping("on/{keyWordId}")
    public R onById(
            @ApiParam(name = "keyWordId", value = "关键词id", required = true)
            @PathVariable("keyWordId") Integer keyWordId
    ){
        try {
            keywordService.onById(keyWordId);
            return R.ok().data("");
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation(value = "获取前三条数据")
    @GetMapping("getStart")
    public R getStart(){
        try {
            List<PersonalNoKeyword> list = keywordService.getStart();
            return R.ok().data(list);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

}

