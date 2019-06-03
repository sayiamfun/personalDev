package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.query.QueryFriendsCircle;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoFriendsCircle;
import com.warm.system.entity.PersonalNoFriendsCirclePersonal;
import com.warm.system.entity.PersonalNoFriendsCirclePhoto;
import com.warm.system.service.db1.PersonalNoFriendsCirclePersonalService;
import com.warm.system.service.db1.PersonalNoFriendsCirclePhotoService;
import com.warm.system.service.db1.PersonalNoFriendsCircleService;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.JsonObjectUtils;
import com.warm.utils.VerifyUtils;
import com.warm.utils.WebConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
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
@Api(description = "朋友圈管理")
@RestController
@RequestMapping("noFriendsCircleManager")
public class PersonalNoFriendsCircleController {
    private static Log log = LogFactory.getLog(PersonalNoFriendsCircleController.class);
    @Autowired
    private PersonalNoFriendsCircleService noFriendsCircleService;
    @Autowired
    private PersonalNoFriendsCirclePersonalService noFriendsCirclePersonalService;
    @Autowired
    private PersonalNoFriendsCirclePhotoService circlePhotoService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private String DBFriendsCirclePersonal = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_friends_circle_personal);
    private String DBFriendsCirclePhoto = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_friends_circle_photo);

    @ApiOperation(value = "分页查询朋友圈列表")
    @PostMapping(value = "{pageNum}/{size}/")
    public R pageQuery(
            @ApiParam(name = "pageNum", value = "当前页码", required = true)
            @PathVariable Long pageNum,

            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable Long size,

            @ApiParam(name = "searchObj", value = "查询条件", required = true)
            @RequestBody QueryFriendsCircle searchObj,
            HttpServletRequest request

    ){
        try {
            log.info("开始条件查询朋友圈");
            Page<PersonalNoFriendsCircle> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            page = noFriendsCircleService.pageQuery(page, searchObj);
            log.info("根据朋友圈id查询对应的个人号数量，朋友圈图片列表");
            String sql = "";
            for (PersonalNoFriendsCircle noFriendsCircle : page.getRecords()) {
                sql = DaoGetSql.getSql("select * from "+ DBFriendsCirclePersonal + " where friends_circle_id = ? and deleted = 0",noFriendsCircle.getId());
                List<PersonalNoFriendsCirclePersonal> personals = noFriendsCirclePersonalService.list(sql);
                noFriendsCircle.setPersonalNum(personals.size());
                sql = DaoGetSql.getSql("select * from "+DBFriendsCirclePhoto+" where `friends_circle_id` = ? and deleted = 0",noFriendsCircle.getId());
                List<PersonalNoFriendsCirclePhoto> photoList = circlePhotoService.list(sql);
                noFriendsCircle.setPhotoList(photoList);
            }
            if(!VerifyUtils.collectionIsEmpty(page.getRecords())){
                log.info("开始拼接朋友圈类型");
                for (PersonalNoFriendsCircle row : page.getRecords()) {
                    String circleType = getCircleType(row);
                    row.setCircleType(circleType);
                }
            }
            log.info("分页条件查朋友圈成功,返回数据");
            return R.ok().data(page);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(searchObj)+" "+pageNum+" "+size, "分页查询朋友圈列表异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }
    //返回朋友圈类型
    private String getCircleType(PersonalNoFriendsCircle row) {
        StringBuffer temp = new StringBuffer();
        boolean flag = false;
        if(!VerifyUtils.isEmpty(row.getFriendsCircleOfficial())){
            temp.append("文案");
            flag = true;
        }
        if(!VerifyUtils.isEmpty(row.getCardUrl())){
            if(flag){
                temp.append("+");
            }
            temp.append("卡片");
            flag = true;
        }
        if(!VerifyUtils.collectionIsEmpty(row.getPhotoList())){
            if(flag){
                temp.append("+");
            }
            temp.append("" + row.getPhotoList().size() +  "图片");
        }
        return temp.toString();
    }

    @ApiOperation(value = "插入朋友圈")
    @PostMapping(value = "addCircle")
    public R addCircle(
            @ApiParam(name = "noFriendsCircle", value = "要添加的朋友圈信息", required = true)
            @RequestBody @Valid PersonalNoFriendsCircle noFriendsCircle, BindingResult bindingResult, HttpServletRequest request
    ){
        try {
            log.info("添加朋友圈信息开始");
            if(!VerifyUtils.collectionIsEmpty(bindingResult.getAllErrors())){
                return R.error().message(bindingResult.getAllErrors().get(0).getDefaultMessage().toString());
            }
            String s = veryTask(noFriendsCircle);
            if(!"true".equals(s)){
                return R.error().message(s);
            }
            int result =  noFriendsCircleService.add(noFriendsCircle);
            if(result==0){
                log.info("添加朋友圈信息失败");
                return R.error().message("新增朋友圈失败");
            }
            log.info("添加朋友圈信息成功");
            return R.ok();
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(noFriendsCircle), "插入朋友圈异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "根据朋友圈id查询朋友圈")
    @PostMapping(value = "{id}")
    public R getById(
            @ApiParam(name = "id", value = "要查找的朋友圈id", required = true)
            @PathVariable("id") Integer id,HttpServletRequest request
    ){
        try {
            log.info("根据id查询朋友圈");
            PersonalNoFriendsCircle noFriendsCircle = noFriendsCircleService.getCircleById(id);
            String circleType = getCircleType(noFriendsCircle);
            noFriendsCircle.setCircleType(circleType);
            log.info("根据id查询朋友圈结束");
            return R.ok().data(noFriendsCircle);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(id), "根据朋友圈id查询朋友圈异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    //验证任务参数
    private String veryTask(PersonalNoFriendsCircle friendsCircle){
        if(VerifyUtils.isEmpty(friendsCircle.getFriendsCircleTheme())){
            return "朋友圈主题不能为空";
        }
        if(VerifyUtils.isEmpty(friendsCircle.getFriendsCircleOfficial())){
            return "朋友圈文案不能为空";
        }
        if(VerifyUtils.collectionIsEmpty(friendsCircle.getPersonalList())){
            return "个人号不能为空";
        }
        if(!VerifyUtils.collectionIsEmpty(friendsCircle.getPhotoList())){
            for (PersonalNoFriendsCirclePhoto personalNoFriendsCirclePhoto : friendsCircle.getPhotoList()) {
                if(VerifyUtils.isEmpty(personalNoFriendsCirclePhoto.getPhoto())){
                    return "选择照片上传失败请重新上传";
                }
            }
        }
        return "true";
    }
}

