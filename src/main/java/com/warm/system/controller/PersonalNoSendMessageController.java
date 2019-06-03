package com.warm.system.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.R;
import com.warm.entity.Sql;
import com.warm.entity.robot.G;
import com.warm.system.entity.PersonalNoOperationStockWechatAccount;
import com.warm.system.entity.PersonalNoSendMessage;
import com.warm.system.service.db1.PersonalNoRequestExceptionService;
import com.warm.system.service.db1.PersonalNoSendMessageService;
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
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author liwenjie123
 * @since 2019-05-06
 */
@CrossOrigin //跨域
@Api(description = "个人号消息回复管理")
@RestController
@RequestMapping("/personalNoSendMessage")
public class PersonalNoSendMessageController {

    private static Log log = LogFactory.getLog(PersonalNoSendMessageController.class);
    @Autowired
    private PersonalNoSendMessageService sendMessageService;
    @Autowired
    private PersonalNoOperationStockWechatAccountService wechatAccountService;
    @Autowired
    private PersonalNoRequestExceptionService requestExceptionService;

    private String DBRequestException = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_request_exception);
    private String DBWeChat = DB.DBAndTable(DB.OA,DB.operation_stock_wechat_account);
    private String DBSendMessage = DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_send_message);

    @ApiOperation("分页查询个人号对应消息")
    @GetMapping("/{nickName}/{pageNum}/{size}/")
    public R pageQuer(
            @ApiParam(name = "nickName", value = "个人号昵称", required = true)
            @PathVariable("nickName")String message,

            @ApiParam(name = "pageNum", value = "第几页", required = true)
            @PathVariable("pageNum")Long pageNum,

            @ApiParam(name = "size", value = "每页条数", required = true)
            @PathVariable("size")Long size,
            HttpServletRequest request
    ){
        try {
            Page<PersonalNoSendMessage> page = new Page<>(VerifyUtils.setPageNum(pageNum), VerifyUtils.setSize(size));
            page = sendMessageService.pageQuery(page, message);
            return R.ok().data(page);
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(message)+" "+pageNum+" "+size, "分页查询个人号对应消息异常", "", 1,e);
            return R.error().message("网页走丢了。。。请返回重试");
        }
    }

    @ApiOperation("添加个人号回复消息")
    @PostMapping("add")
    @Transactional
    public R add(
            @ApiParam(name = "sendMessage", value = "要添加的个人号回复消息", required = true)
            @RequestBody PersonalNoSendMessage sendMessage,HttpServletRequest request
    ){
        try {
            if(VerifyUtils.isEmpty(sendMessage) || VerifyUtils.collectionIsEmpty(sendMessage.getIds())){
                return R.error().message("未选中个人号");
            }
            if(sendMessage.getTiming()!=0 && VerifyUtils.isEmpty(sendMessage.getTimingTime())){
                return R.error().message("要选择时间的哦");
            }
            String ids = DaoGetSql.getIds(sendMessage.getIds());
            List<PersonalNoOperationStockWechatAccount> list = wechatAccountService.listBySql(new Sql("select * from " + DBWeChat + " where id in " + ids));
            String getSql = "";
            Sql sql = new Sql();
            for (PersonalNoOperationStockWechatAccount personalNo : list) {
                if(sendMessage.getTiming()==0){
                    getSql = DaoGetSql.getSql(" SELECT * FROM "+DBSendMessage+" WHERE  `personal_wx_id` = ? AND `timing` = 0 LIMIT 0,1",personalNo.getWxId());
                    sql.setSql(getSql);
                    PersonalNoSendMessage sendMessage1 = sendMessageService.getBySql(sql);
                    if(!VerifyUtils.isEmpty(sendMessage1)){
                        return R.error().message(personalNo.getNickName()+"已经有一条立即回复消息了。。。");
                    }
                }
                sendMessage.setId(null);
                sendMessage.setPersonalWxId(personalNo.getWxId());
                sendMessage.setNickName(personalNo.getNickName());
                sendMessage.setDb(DBSendMessage);
                sendMessageService.add(sendMessage);
            }
            return R.ok().data("");
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(sendMessage), "添加个人号回复消息异常", "", 1,e);
            return R.error().message("网页走丢了。。。请返回重试");
        }
    }

    @ApiOperation("根据id删除")
    @DeleteMapping("deleteById/{id}/")
    public R deleteById(
            @ApiParam(name = "id", value = "id", required = true)
            @PathVariable("id")Integer id,HttpServletRequest request
    ){
        try {
            String delSql = DaoGetSql.deleteById(DBSendMessage, id);
            sendMessageService.deleteOne(new Sql(delSql));
            return R.ok().data("");
        }catch (Exception e){
            G.requestException(DBRequestException, requestExceptionService, request, JsonObjectUtils.objectToJson(id), "根据id删除异常", "", 1,e);
            return R.error().message("网页走丢了。。。请返回重试");
        }
    }

}

