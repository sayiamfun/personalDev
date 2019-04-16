package com.warm.system.controller;


import com.warm.entity.R;
import com.warm.system.entity.PersonalNoSmallFace;
import com.warm.system.service.db1.PersonalNoSmallFaceService;
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
    @ApiOperation("表情列表")
    @GetMapping("listAll")
    public R listAll(){
        try {
            List<PersonalNoSmallFace> personalNoSmallFaces = smallFaceService.selectList(null);
            return R.ok().data(personalNoSmallFaces);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation("添加或者修改表情")
    @PostMapping("addSmallFace")
    public R addSmaillFace(
            @ApiParam(name = "personalNoSmallFace", value = "表情对象", required = true)
            @RequestBody PersonalNoSmallFace personalNoSmallFace
    ){
        try {
            if(VerifyUtils.isEmpty(personalNoSmallFace.getId())){
                smallFaceService.insert(personalNoSmallFace);
            }else {
                smallFaceService.updateById(personalNoSmallFace);
            }
            return R.ok().data("");
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

    @ApiOperation("根据id删除表情")
    @DeleteMapping("{id}")
    public R deleteById(
            @ApiParam(name = "id", value = "ID", required = true)
            @PathVariable("id") Integer id
    ){
        try {
            smallFaceService.deleteById(id);
            return R.ok().data("");
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }
}

