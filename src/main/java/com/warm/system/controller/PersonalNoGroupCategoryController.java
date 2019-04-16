package com.warm.system.controller;


import com.warm.entity.R;
import com.warm.system.entity.PersonalNoGroupCategory;
import com.warm.system.service.db3.PersonalNoGroupCategoryService;
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
@Api(description = "群类别管理")
@RestController
@RequestMapping("/groupCategory")
public class PersonalNoGroupCategoryController {

    private static Log log = LogFactory.getLog(PersonalNoGroupCategoryController.class);
    @Autowired
    private PersonalNoGroupCategoryService groupCategoryService;
    @ApiOperation(value = "根据类别集合id查询所有的群类别")
    @GetMapping("/{setId}/{flag}/")
    public R listBySetId(
            @ApiParam(name = "setId", value = "类别集合id", required = true)
            @PathVariable("setId") Integer setId,

            @ApiParam(name = "flag", value = "数据库表识", required = true)
            @PathVariable("flag") Integer flag
    ){
        try {
            log.info("根据群类别集合id查询所有的群类别");
            List<PersonalNoGroupCategory> groupCategoryList = null;
            switch (flag){
                case 0:
                    groupCategoryList = groupCategoryService.listBySetId(setId);
                    break;
                case 1:
                    groupCategoryList = groupCategoryService.listBySetIdFromQunLie01(setId);
                    break;
            }

            log.info("根据群类别集合id查询所有的群类别结束");
            return R.ok().data(groupCategoryList);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

}

