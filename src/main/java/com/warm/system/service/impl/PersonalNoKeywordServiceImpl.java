package com.warm.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.entity.Sql;
import com.warm.system.entity.*;
import com.warm.system.mapper.PersonalNoKeywordMapper;
import com.warm.system.service.db1.PersonalNoAndKeywordService;
import com.warm.system.service.db1.PersonalNoKeywordContentService;
import com.warm.system.service.db1.PersonalNoKeywordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db2.PersonalNoOperationStockWechatAccountService;
import com.warm.system.service.db3.PersonalNoGroupCategoryService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import com.warm.utils.WebConst;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
@Service
public class PersonalNoKeywordServiceImpl extends ServiceImpl<PersonalNoKeywordMapper, PersonalNoKeyword> implements PersonalNoKeywordService {
    private static Log log = LogFactory.getLog(PersonalNoKeywordServiceImpl.class);
    @Autowired
    private PersonalNoKeywordContentService keywordContentService;
    @Autowired
    private PersonalNoKeywordMapper keywordMapper;
    @Autowired
    private PersonalNoAndKeywordService personalNoAndKeywordService;
    @Autowired
    private PersonalNoGroupCategoryService groupCategoryService;
    @Autowired
    private PersonalNoOperationStockWechatAccountService wechatAccountService;

    private String DBKeyWord = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_keyword);
    private String DBWeChat = DB.DBAndTable(DB.OA, DB.operation_stock_wechat_account);
    private String DBKeyWordContent = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_keyword_content);
    private String DBNoAndKeyword = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_and_keyword);

    /**
     * 根据关键字模糊查询
     * @param page
     * @param keyWord
     * @return
     */
    @Override
    public Page<PersonalNoKeyword> pageQuery(Page<PersonalNoKeyword> page, String keyWord) {
        List<PersonalNoKeyword> list = new ArrayList<>();
        StringBuffer temp = new StringBuffer();
        if(!"-1".equals(keyWord)){
            temp.append(" where keyword like '%"+keyWord+"%'");
        }
        String getSql = DaoGetSql.getSql("select count(*) from "+DBKeyWord + temp.toString());
        Long count = keywordMapper.getCount(getSql);
        page.setTotal(count.intValue());
        temp.append(" order by id desc limit "+page.getOffset()+","+page.getLimit());
        getSql = DaoGetSql.getSql("select * from "+DBKeyWord + temp.toString());
        List<PersonalNoKeyword> personalNoKeywords = keywordMapper.list(getSql);
        for (PersonalNoKeyword record : personalNoKeywords) {
            PersonalNoKeyword infoById = getInfoById(record);
            list.add(infoById);
        }
        page.setRecords(list);
        return page;
    }


    /**
     * 根据id获取关键词信息
     * @param personalNoKeyword
     * @return
     */
    @Override
    public PersonalNoKeyword getInfoById(PersonalNoKeyword personalNoKeyword) {
        String getSql = "select * from " + DBKeyWordContent + " where personal_no_keyword_id = " + personalNoKeyword.getId();
        List<PersonalNoKeywordContent> personalNoKeywordContents = keywordContentService.list(getSql);
        for (PersonalNoKeywordContent personalNoKeywordContent : personalNoKeywordContents) {
            if("邀请入群".equals(personalNoKeywordContent.getContentType())){
                PersonalNoGroupCategory personalNoGroupCategory = groupCategoryService.getPersonalNoGroupCategory(personalNoKeywordContent.getContent());
                personalNoKeyword.setGroupName(personalNoGroupCategory==null?"":personalNoGroupCategory.getCname());
            }
        }
        personalNoKeyword.setKeywordContentList(personalNoKeywordContents);
        personalNoKeyword.setKeywordContentShow(WebConst.getKeyWordContentShow(personalNoKeywordContents));
        getSql = "select * from "+DBNoAndKeyword+" where keyword_id = "+personalNoKeyword.getId();
        List<PersonalNoAndKeyword> personalNoAndKeywordList = personalNoAndKeywordService.list(getSql);
        StringBuffer temp = new StringBuffer();
        temp.append("(");
        List<PersonalNoOperationStockWechatAccount> personalNos = new ArrayList<>();
        Sql sql = new Sql();
        for (int i = 0; i < personalNoAndKeywordList.size(); i++) {
            if(i>0){
                temp.append(",");
            }
            temp.append(personalNoAndKeywordList.get(i).getPersonalNoNickName());
            getSql = DaoGetSql.getById(DBWeChat, personalNoAndKeywordList.get(i).getPersonalNoId());
            sql.setSql(getSql);
            PersonalNoOperationStockWechatAccount one = wechatAccountService.getBySql(sql);
            personalNos.add(one);
        }
        temp.append(")");
        List<Integer> weChatIdList = new ArrayList<>();
        for (PersonalNoOperationStockWechatAccount personalNo : personalNos) {
            weChatIdList.add(personalNo.getId());
        }
        personalNoKeyword.setPersonalIdList(weChatIdList);
        personalNoKeyword.setPersonalList(personalNos);
        personalNoKeyword.setNickNames(temp.toString());

        return personalNoKeyword;
    }

    /**
     * 根据id删除关键词消息
     * @param keyWordId
     */
    @Override
    public void deleteById(Integer keyWordId) {
        String getSql = DaoGetSql.getSql("delete from "+DBKeyWordContent+" where personal_no_keyword_id = ?",keyWordId);
        keywordContentService.delete(getSql);
        getSql = DaoGetSql.deleteById(DBKeyWord,keyWordId);
        baseMapper.delete(getSql);
    }


    @Override
    @Transactional
    public Integer add(PersonalNoKeyword keyword) {
        keyword.setDb(DBKeyWord);
        keyword.setDeleted(0);
        if(VerifyUtils.isEmpty(keyword.getId())){
            keywordMapper.add(keyword);
        }else {
            keywordMapper.updateOne(keyword);
        }
        log.info("删除原有的关键词内容");
        String sql = "delete from " + DBKeyWordContent + " where personal_no_keyword_id = " + keyword.getId();
        keywordContentService.delete(sql);
        log.info("添加新的的关键词内容");
        for (PersonalNoKeywordContent personalNoKeywordContent : keyword.getKeywordContentList()) {
            personalNoKeywordContent.setId(null);
            personalNoKeywordContent.setPersonalNoKeywordId(keyword.getId());
            personalNoKeywordContent.setDb(DBKeyWordContent);
            personalNoKeywordContent.setDeleted(0);
            keywordContentService.add(personalNoKeywordContent);
        }
        log.info("删除原有关键词的个人号对应关系");
        sql = "delete from " + DBNoAndKeyword + " where keyword_id = " + keyword.getId();
        personalNoAndKeywordService.deleteBySql(new Sql(sql));
        if(!VerifyUtils.collectionIsEmpty(keyword.getPersonalIdList())) {
            String ids = DaoGetSql.getIds(keyword.getPersonalIdList());
            String getSql = DaoGetSql.getSql("select * from " + DBWeChat + " where id in " + ids);
            List<PersonalNoOperationStockWechatAccount> list = wechatAccountService.listBySql(new Sql(getSql));
            PersonalNoAndKeyword noAndKeyword = null;
            for (PersonalNoOperationStockWechatAccount personalNo : list) {
                noAndKeyword = new PersonalNoAndKeyword();
                noAndKeyword.setPersonalNoId(personalNo.getId());
                noAndKeyword.setPersonalNoWxId(personalNo.getWxId());
                noAndKeyword.setPersonalNoNickName(personalNo.getNickName());
                noAndKeyword.setKeywordId(keyword.getId());
                noAndKeyword.setKeywordName(keyword.getKeyword());
                noAndKeyword.setDb(DBNoAndKeyword);
                personalNoAndKeywordService.add(noAndKeyword);
            }
        }
        return keyword.getId();
    }

    @Override
    public List<PersonalNoKeyword> list(String sql) {
        return keywordMapper.list(sql);
    }

    @Override
    public List<String> listString(String sql) {
        return keywordMapper.listString(sql);
    }

    @Override
    public PersonalNoKeyword getOne(String sql) {
        return keywordMapper.getOne(sql);
    }

    @Override
    public Long getCount(String sql) {
        return keywordMapper.getCount(sql);
    }

}
