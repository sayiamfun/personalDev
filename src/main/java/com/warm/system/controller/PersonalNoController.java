package com.warm.system.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.query.QueryPersonal;
import com.warm.entity.requre.BatchUpdateObject;
import com.warm.entity.result.Num;
import com.warm.entity.robot.G;
import com.warm.system.entity.*;
import com.warm.system.service.db1.*;
import com.warm.system.service.db2.PersonalNoOperationStockWechatAccountService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.JsonObjectUtils;
import com.warm.utils.VerifyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@CrossOrigin //跨域
@Api(description = "个人号管理")
@RestController
@RequestMapping("/personalManager")
public class PersonalNoController {
    private static Log log = LogFactory.getLog(PersonalNoController.class);
    @Autowired
    private PersonalNoOperationStockWechatAccountService wechatAccountService;
    @Autowired
    private PersonalNoTaskService taskService;
    @Autowired
    private PersonalNoFriendsService friendsService;
    @Autowired
    private PersonalNoUserService userService;
    @Autowired
    private PersonalNoCategoryAndGroupService categoryAndGroupService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;
    @Autowired
    private PersonalNoCategoryService noCategoryService;
    @Autowired
    private PersonalNoLableService lableService;
    @Autowired
    private PersonalNoPeopleService peopleService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private String DBWeChat = DB.DBAndTable(DB.OA, DB.operation_stock_wechat_account);
    private String DBFriends = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_friends);
    private String DBCategoryAndGroup = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_category_and_group);
    private String DBUser = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_user);
    private String DBNoCategory = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_category);
    private String DBLable = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_lable);
    private String DBPeople = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_people);


    @ApiOperation(value = "查询所有个人号列表")
    @GetMapping
    public R list(HttpServletRequest request){
        try {
            log.info("开始查询所有个人号");
            String getSql = DaoGetSql.getSql("SELECT * FROM "+DBWeChat+" where operation_project_instance_id = ? order by id desc", G.ms_OPERATION_PROJECT_INSTANCE_ID);
            Sql sql = new Sql(getSql);
            List<PersonalNoOperationStockWechatAccount> list = wechatAccountService.listBySql(sql);
            for (PersonalNoOperationStockWechatAccount wechatAccount : list) {
                wechatAccount.setNickName(wechatAccount.getNickName()+"("+wechatAccount.getStatus()+")");
                getSql = DaoGetSql.getSql("SELECT count(*) FROM "+DBFriends+" where personal_no_wx_id = ? and deleted = 0",wechatAccount.getWxId());
                Long count = friendsService.getCount(getSql);
                wechatAccount.setFriendsNum(count.intValue());
            }
            log.info("查询成功返回数据列表");
            return R.ok().data(list);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, "", "查询所有个人号列表异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "根据昵称查询所有个人号列表")
    @GetMapping("getByNickName/{nickName}/")
    public R listByBickName(@PathVariable("nickName") String nickName,HttpServletRequest request){
        try {
            log.info("开始查询所有个人号");
            String getSql = DaoGetSql.getSql("SELECT * FROM "+DBWeChat+" where nick_name like ? operation_project_instance_id = ? order by id desc", "%"+nickName+"%", G.ms_OPERATION_PROJECT_INSTANCE_ID);
            List<PersonalNoOperationStockWechatAccount> list = wechatAccountService.listBySql(new Sql(getSql));
            for (PersonalNoOperationStockWechatAccount wechatAccount : list) {
                wechatAccount.setNickName(wechatAccount.getNickName()+"("+wechatAccount.getStatus()+")");
            }
            log.info("查询成功返回数据列表");
            return R.ok().data(list);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(nickName), "根据昵称查询所有个人号列表异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "根据类别查询所有个人号列表")
    @GetMapping("getByCategory/{category}/")
    public R listByCategory(@PathVariable("category") String category,HttpServletRequest request){
        try {
            List<PersonalNoOperationStockWechatAccount> list = getByCategory(category);
            for (PersonalNoOperationStockWechatAccount wechatAccount : list) {
                wechatAccount.setNickName(wechatAccount.getNickName()+"("+wechatAccount.getStatus()+")");
            }
            log.info("查询成功返回数据列表");
            return R.ok().data(list);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(category), "根据类别查询所有个人号列表异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }
//    根据类别查询个人号
    private List<PersonalNoOperationStockWechatAccount> getByCategory(String category){
        log.info("开始查询所有个人号");
        String getSql = "";
        Sql sql = new Sql();
        List<PersonalNoOperationStockWechatAccount> list = new ArrayList<>();
        if("默认类别".equals(category)){
            getSql = "SELECT * FROM "+DBWeChat+" where operation_project_instance_id = "+G.ms_OPERATION_PROJECT_INSTANCE_ID+" order by id desc";
            sql.setSql(getSql);
            list = wechatAccountService.listBySql(sql);
        }else {
            getSql = DaoGetSql.getSql("SELECT personal_no_wx_id from "+DBCategoryAndGroup+" where category = ?",category);
            sql.setSql(getSql);
            List<String> wxIds = categoryAndGroupService.listStringBySql(sql);
            String ids = DaoGetSql.getIds(wxIds);
            getSql = "SELECT * FROM "+DBWeChat+" where wx_id in "+ids+" and operation_project_instance_id = "+G.ms_OPERATION_PROJECT_INSTANCE_ID+" order by id desc";
            sql.setSql(getSql);
            list = wechatAccountService.listBySql(sql);
        }
        return list;
    }

    @ApiOperation(value = "根据条件查询所有个人号列表（flag为1，根据类别。flag为2，根据昵称。否则查询所有）(type为1 ，个人号。type为2，朋友圈。type为3，返回个人号列表。)")
    @GetMapping(value = "getByCategory/{category}/{flag}/{type}/")
    public R list(
            @ApiParam(name = "flag", value = "根据什么参数查询标志", required = true)
            @PathVariable("flag") String flag,

            @ApiParam(name = "type", value = "根据什么参数查询标志", required = true)
            @PathVariable("type") String type,

            @ApiParam(name = "category", value = "查询类别", required = true)
            @PathVariable("category") String category,
            HttpServletRequest request
    ){
        try {
            List<PersonalNoOperationStockWechatAccount> personalList = new ArrayList<>();
            if("1".equals(flag)){
                personalList = getByCategory(category);
            }else if("2".equals(flag)){
                log.info("开始根据昵称查询个人号");
                StringBuffer temp = new StringBuffer("select * from "+DBWeChat+" where operation_project_instance_id = "+G.ms_OPERATION_PROJECT_INSTANCE_ID+" and nickname like '%"+category+"%' order by id desc");
                personalList = wechatAccountService.listBySql(new Sql(temp.toString()));
            }else {
                StringBuffer temp = new StringBuffer("select * from "+DBWeChat+" where operation_project_instance_id = "+G.ms_OPERATION_PROJECT_INSTANCE_ID+" order by id desc");
                personalList = wechatAccountService.listBySql(new Sql(temp.toString()));
            }
            if (!VerifyUtils.collectionIsEmpty(personalList)) {
                if("1".equals(type)) {
                    log.info("将个人号类型  转换成任务添加的  任务个人号类型");
                    List<PersonalNoTaskPersonal> personalListResult = new ArrayList<>();
                    for (PersonalNoOperationStockWechatAccount no : personalList) {
                        PersonalNoTaskPersonal personalNoTaskPersonal = new PersonalNoTaskPersonal();
                        personalNoTaskPersonal.setPersonalNoWxId(no.getWxId());
                        personalNoTaskPersonal.setPersonalNoName(no.getNickName()+"("+no.getStatus()+")");
                        personalNoTaskPersonal.setPersonalNoWxId(no.getWxId());
                        personalListResult.add(personalNoTaskPersonal);
                    }
                    log.info("装换为任务个人号成功");
                    return R.ok().data(personalListResult);
                }else if("2".equals(type)){
                    log.info("将个人号类型  转换成朋友圈添加的  朋友圈个人号类型");
                    List<PersonalNoFriendsCirclePersonal> personalListResult = new ArrayList<>();
                    for (PersonalNoOperationStockWechatAccount no : personalList) {
                        PersonalNoFriendsCirclePersonal circlePersonal = new PersonalNoFriendsCirclePersonal();
                        circlePersonal.setPersonalNoId(no.getId());
                        circlePersonal.setPersonalNoName(no.getNickName()+"("+no.getStatus()+")");
                        circlePersonal.setPersonalNoWxId(no.getWxId());
                        personalListResult.add(circlePersonal);
                    }
                    log.info("转换为朋友圈个人号成功");
                    return R.ok().data(personalListResult);
                }else {
                    return R.ok().data(personalList);
                }
            }else {
                return R.ok();
            }
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(flag)+" "+type+" "+category, "根据条件查询所有个人号列表异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "分页个人号列表")
    @PostMapping(value = "{pageNum}/{size}/")
    public R pageQuery(
            @ApiParam(name = "pageNum", value = "当前页码", required = true)
            @PathVariable Long pageNum,

            @ApiParam(name = "size", value = "每页记录数", required = true)
            @PathVariable Long size,

            @ApiParam(name = "searchObj", value = "查询条件", required = true)
            @RequestBody QueryPersonal searchObj,
            HttpServletRequest request
    ){
        try {
            log.info("开始条件查询个人号");
            Page<PersonalNoOperationStockWechatAccount> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            page = wechatAccountService.pageQuery(page, searchObj);
            log.info("分页条件查找个人号成功,返回数据");
            return R.ok().data(page);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(searchObj)+" "+pageNum+" "+size, "分页个人号列表异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "根据id编辑个人号")
    @PutMapping(value = "updatePersonal/{personalId}/")
    @Transactional
    public R updatePersonal(
            @ApiParam(name = "personalId", value = "要修改的个人号id", required = true)
            @PathVariable("personalId") Integer personalId,

            @ApiParam(name = "no", value = "要修改的个人号信息", required = true)
            @RequestBody PersonalNoOperationStockWechatAccount no,
            HttpServletRequest request
    ){
        try {
            log.info("开始根据id修改个人号信息");
            if (VerifyUtils.isEmpty(personalId)){
                log.info("要修改的个人号id为空");
                return R.error().message("要修改的个人号id为空");
            }
            if(VerifyUtils.isEmpty(no)){
                log.info("要修改的个人号信息为空");
                return R.error().message("要修改的个人号信息为空");
            }
            String getSql = DaoGetSql.getById(DBWeChat,personalId);
            Sql sql = new Sql(getSql);
            PersonalNoOperationStockWechatAccount byId = wechatAccountService.getBySql(sql);
            if(!VerifyUtils.isEmpty(byId)){
                getSql = DaoGetSql.getSql("SELECT * FROM "+DBCategoryAndGroup+" where personal_no_wx_id = ? limit 0,1",byId.getWxId());
                sql.setSql(getSql);
                PersonalNoCategoryAndGroup personalNoCategoryAndGroup = categoryAndGroupService.getBySql(sql);
                if(VerifyUtils.isEmpty(personalNoCategoryAndGroup)){
                    personalNoCategoryAndGroup = new PersonalNoCategoryAndGroup();
                }
                getSql = DaoGetSql.getSql("SELECT * FROM "+DBNoCategory+" WHERE `personal_no_category` = ? LIMIT 0,1",no.getCategory());
                PersonalNoCategory one = noCategoryService.getOne(getSql);
                personalNoCategoryAndGroup.setPersonalNoWxId(byId.getWxId());
                personalNoCategoryAndGroup.setNickName(byId.getNickName());
                personalNoCategoryAndGroup.setCategoryId(VerifyUtils.isEmpty(one)?-1:one.getId());
                personalNoCategoryAndGroup.setCategory(no.getCategory());
                personalNoCategoryAndGroup.setGroup(no.getGroup());
                personalNoCategoryAndGroup.setDeleted(0);
                personalNoCategoryAndGroup.setDb(DBCategoryAndGroup);
                int b = categoryAndGroupService.add(personalNoCategoryAndGroup);
                if(b <= 0){
                    log.info("根据id修改个人号信息失败");
                    return R.error().message("根据id修改个人号信息失败");
                }
            }
            log.info("根据id修改个人号信息成功");
            return R.ok();
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(no)+" "+personalId, "根据id编辑个人号异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }
//
    @ApiOperation(value = "查询首页三个总数数据")
    @GetMapping(value = "Num")
    public R Num(HttpServletRequest request){
        try {
            log.info("开始查询首页显示的三个总数据");
            Num num = new Num();
            String getSql = DaoGetSql.getSql("select * from "+DBWeChat+" where operation_project_instance_id = ?",G.ms_OPERATION_PROJECT_INSTANCE_ID);
            Sql sql = new Sql(getSql);
            List<PersonalNoOperationStockWechatAccount> personalList = wechatAccountService.listBySql(sql);
            if(!VerifyUtils.collectionIsEmpty(personalList)){
                Integer friendsNum = 0;
                log.info("个人号集合查询成功,开始计算好友数量");
                for (PersonalNoOperationStockWechatAccount no : personalList) {
                    getSql = DaoGetSql.getSql("SELECT count(*) FROM "+DBFriends+" where personal_no_wx_id = ? and deleted = 0",no.getWxId());
                    friendsNum += friendsService.getCount(getSql).intValue();
                }
                log.info("计算完成将数据返回");
                num.setTotal(personalList.size());
                num.setFriendsNum(friendsNum);
            }else {
                num.setTotal(0);
                num.setFriendsNum(0);
            }
            //查询承担的活动总数量(正在进行的活动并且参与个人号数量不为0)
            int taskNum = taskService.getOnGoingTaskNum();
            num.setActivityNum(taskNum);
            log.info("开始查询首页显示的三个总数据结束");
            return R.ok().data(num);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, "", "查询首页三个总数数据异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "根据个人号id查询个人号下的所有好友")
    @PostMapping(value = "getUserBuPersonal/{personalWxId}/")
    public R getUserByPersonal(
            @ApiParam(name = "personalWxId", value = "个人号WxId", required = true)
            @PathVariable("personalWxId") Integer personalWxId,HttpServletRequest request
    ){
        try {
            log.info("查询此个人号下的所有好友列表");
            String getSql = DaoGetSql.getSql("select * from " + DBFriends + " where personal_no_wx_id = ? and deleted = 0", personalWxId);
            Sql sql = new Sql();
            List<PersonalNoFriends> personalNoFriends = friendsService.list(getSql);
            Set<PersonalNoUser> userSet = new HashSet<>();
            log.info("根据好友表的好友id查询好友详细信息");
            for (PersonalNoFriends personalNoFriend : personalNoFriends) {
                getSql = DaoGetSql.getSql("SELECT * FROM "+DBUser+" where wx_id = ? limit 0,1",personalNoFriend.getUserWxId());
                sql.setSql(getSql);
                PersonalNoUser byId = userService.getBySql(sql);
                if(VerifyUtils.isEmpty(byId)){
                    continue;
                }
                userSet.add(byId);
            }
            log.info("查询结束");
            return R.ok().data(userSet);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(personalWxId), "根据个人号id查询个人号下的所有好友异常", "", 1,e);
            return R.error().message("网页走丢了，请刷新。。。");
        }
    }
//
    @ApiOperation(value = "批量修改个人号类别或者个人号组")
    @PutMapping(value = "batchUpdatePersonalCategory")
    @Transactional
    public R batchUpdatePersonalCategory(
            @ApiParam(name = "batchUpdateObject", value = "批量修改个人号类别参数", required = true)
            @RequestBody BatchUpdateObject batchUpdateObject,HttpServletRequest request
    ){
        try {
            if(VerifyUtils.isEmpty(batchUpdateObject.getObject()) || VerifyUtils.collectionIsEmpty(batchUpdateObject.getPersonalList()) || VerifyUtils.isEmpty(batchUpdateObject.getFlag())){
                return R.error().message("参数不能为空");
            }
            log.info("开始批量修改个人号类别");
            String getSql = "";
            Sql sql = new Sql();
            for (PersonalNoOperationStockWechatAccount wechatAccount : batchUpdateObject.getPersonalList()) {
                getSql = DaoGetSql.getSql("SELECT * FROM "+DBCategoryAndGroup+" where personal_no_wx_id = ? limit 0,1",wechatAccount.getWxId());
                sql.setSql(getSql);
                PersonalNoCategoryAndGroup bySql = categoryAndGroupService.getBySql(sql);
                if(VerifyUtils.isEmpty(bySql)){
                    bySql = new PersonalNoCategoryAndGroup();
                }
                if("0".equals(batchUpdateObject.getFlag())){
                    getSql = DaoGetSql.getSql("SELECT * FROM "+DBNoCategory+" WHERE `personal_no_category` = ? LIMIT 0,1",batchUpdateObject.getObject());
                    PersonalNoCategory one = noCategoryService.getOne(getSql);
                    bySql.setCategoryId(VerifyUtils.isEmpty(one)?-1:one.getId());
                    bySql.setCategory(batchUpdateObject.getObject());
                }else {
                    bySql.setGroup(batchUpdateObject.getObject());
                }
                bySql.setDb(DBCategoryAndGroup);
                Integer add = categoryAndGroupService.add(bySql);
                if(add<0){
                    log.info("批量修改个人号类别失败");
                    return R.error().message("批量修改个人号类别失败");
                }
            }
            log.info("批量修改个人号数量成功");
            return R.ok();
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(batchUpdateObject), "批量修改个人号类别或者个人号组异常", "", 1,e);
            return R.error().message("网页走丢了，请返回重试。。。");
        }
    }

    @ApiOperation(value = "根据标签列表查询所有的个人号")
    @PostMapping(value = "listByLable")
    public R listByLable(
            @ApiParam(name = "batchUpdateObject", value = "批量修改个人号类别参数", required = true)
            @RequestBody QueryPersonal queryPersonal,HttpServletRequest request
    ){
        try {
            if(VerifyUtils.collectionIsEmpty(queryPersonal.getLableList())){
                return R.error().message("标签列表为空");
            }
            String lableIds = DaoGetSql.getIds(queryPersonal.getLableList());
            String getSql = "SELECT DISTINCT `lable_name` FROM "+DBLable+" WHERE id IN "+lableIds+" and deleted = 0";
            List<String> lableNameList = lableService.listString(getSql);
            String lableQuery = "";
            if(!VerifyUtils.collectionIsEmpty(lableNameList)){
                StringBuffer temp = new StringBuffer();
                for (int i =0;i<lableNameList.size() ; i++) {
                    if(i==0){
                        temp.append(" WHERE ( `lable` LIKE '%|"+lableNameList.get(i)+"|%'");
                    }else {
                        temp.append(" or `lable` LIKE '%|"+lableNameList.get(i)+"|%'");
                    }
                }
                temp.append(" )");
                lableQuery = temp.toString();
            }
            getSql= "SELECT `personal_no_wx_id` FROM "+DBPeople+lableQuery+" AND deleted = 0 AND flag = 2 GROUP BY `personal_no_wx_id`";
            List<String> personalWxIdList = peopleService.listString(getSql);
            String wxIds = DaoGetSql.getIds(personalWxIdList);
            log.info("根据个人号微信id查询所有的个人号");
            getSql = "SELECT * FROM "+DBWeChat+" where wx_id in "+wxIds+" and operation_project_instance_id = "+G.ms_OPERATION_PROJECT_INSTANCE_ID;
            Sql sql = new Sql(getSql);
            List<PersonalNoOperationStockWechatAccount> list = wechatAccountService.listBySql(sql);
            for (PersonalNoOperationStockWechatAccount wechatAccount : list) {
                wechatAccount.setNickName(wechatAccount.getNickName()+"("+wechatAccount.getStatus()+")");
            }
            return R.ok().data(list);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(queryPersonal), "根据标签列表查询所有的个人号异常", "", 1,e);
            return R.error().message("网页走丢了。。。请返回重试");
        }
    }

}

