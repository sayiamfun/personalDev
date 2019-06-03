package com.warm.system.controller;


import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoSmallFace;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
import com.warm.system.service.db1.PersonalNoSmallFaceService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.JsonObjectUtils;
import com.warm.utils.VerifyUtils;
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
 * 表情库 前端控制器
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@CrossOrigin //跨域
@Api(description = "表情管理")
@RestController
@RequestMapping("/personalNoSmallFace")
public class PersonalNoSmallFaceController {
    private static Log log = LogFactory.getLog(PersonalNoSmallFaceController.class);
    @Autowired
    private PersonalNoSmallFaceService smallFaceService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private String ZCSmallFace = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_small_face);


    @ApiOperation("表情列表")
    @GetMapping("listAll")
    public R listAll(HttpServletRequest request){
        try {
            String getSql = DaoGetSql.getSql("select * from " + ZCSmallFace + " order by id desc");
            List<PersonalNoSmallFace> personalNoSmallFaces = smallFaceService.listBySql(new Sql(getSql));
            return R.ok().data(personalNoSmallFaces);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, "", "表情列表异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation("添加或者修改表情")
    @PostMapping("addSmallFace")
    public R addSmaillFace(
            @ApiParam(name = "personalNoSmallFace", value = "表情对象", required = true)
            @RequestBody PersonalNoSmallFace personalNoSmallFace,HttpServletRequest request
    ){
        try {
            personalNoSmallFace.setDb(ZCSmallFace);
            smallFaceService.add(personalNoSmallFace);
            return R.ok().data("");
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, "", "添加或者修改表情异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation("根据id删除表情")
    @DeleteMapping("{id}")
    public R deleteById(
            @ApiParam(name = "id", value = "ID", required = true)
            @PathVariable("id") Integer id,HttpServletRequest request
    ){
        try {
            String delSql = DaoGetSql.deleteById(ZCSmallFace, id);
            smallFaceService.deleteBySql(new Sql(delSql));
            return R.ok().data("");
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, "", "根据id删除表情异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }
}

