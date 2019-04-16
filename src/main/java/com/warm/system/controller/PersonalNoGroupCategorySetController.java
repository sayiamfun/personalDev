package com.warm.system.controller;


import com.warm.entity.R;
import com.warm.system.entity.PersonalNoGroupCategorySet;
import com.warm.system.service.db3.PersonalNoGroupCategorySetService;
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
@Api(description = "群类别集合管理")
@RestController
@RequestMapping("/groupCategorySet")
public class PersonalNoGroupCategorySetController {

    private static Log log = LogFactory.getLog(PersonalNoGroupCategorySetController.class);
    @Autowired
    private PersonalNoGroupCategorySetService groupCategorySetService;

    @ApiOperation(value = "查询所有的群类别集合")
    @GetMapping("{index}")
    public R listAll(
            @ApiParam(name = "index", value = "查询表示（几号后台的库)",required = true)
            @PathVariable("index") Integer index
    ){
        try {
            log.info("查询所有的类别集合");
            List<PersonalNoGroupCategorySet> list = null;
            switch (index){
                case 0:
                     list = groupCategorySetService.listAll();
                    break;
                case 1:
                    list = groupCategorySetService.listQunLie01All();
                    break;
            }
            log.info("查询所有的类别集合结束");
            return R.ok().data(list);
        }catch (Exception e){
            e.printStackTrace();
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }

}

