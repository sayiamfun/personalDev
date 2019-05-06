package com.warm.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.warm.entity.DB;
import com.warm.system.entity.*;
import com.warm.system.mapper.PersonalNoKeywordMapper;
import com.warm.system.service.db1.PersonalNoAndKeywordService;
import com.warm.system.service.db1.PersonalNoKeywordContentService;
import com.warm.system.service.db1.PersonalNoKeywordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.system.service.db1.PersonalNoService;
import com.warm.system.service.db3.PersonalNoGroupCategoryService;
import com.warm.utils.DaoGetSql;
import com.warm.utils.VerifyUtils;
import com.warm.utils.WebConst;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private PersonalNoService personalNoService;
    @Autowired
    private PersonalNoGroupCategoryService groupCategoryService;
    @Autowired
    private PersonalNoService noService;

    private String ZCDBKeyWord = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_keyword);
    private String ZCDBPersonalNo = DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no);



    /**
     * 根据关键字模糊查询
     * @param page
     * @param keyWord
     * @return
     */
    @Override
    public Page<PersonalNoKeyword> pageQuery(Page<PersonalNoKeyword> page, String keyWord) {
        List<PersonalNoKeyword> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select * from "+ZCDBKeyWord + " where id not in (1,2,3)");
        if(!"-1".equals(keyWord)){
            sql.append(" and keyword = '"+keyWord+"'");
        }
        sql.append(" order by id desc limit "+page.getOffset()+","+page.getLimit());
        List<PersonalNoKeyword> personalNoKeywords = keywordMapper.list(sql.toString());
        for (PersonalNoKeyword record : personalNoKeywords) {
            PersonalNoKeyword infoById = getInfoById(record);
            list.add(infoById);
        }
        String sql1 = "select count(*) from " + ZCDBKeyWord;
        Long count = keywordMapper.getCount(sql1);
        page.setTotal(count.intValue());
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
        String sql = "select * from " + DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_keyword_content) + " where personal_no_keyword_id = " + personalNoKeyword.getId();
        List<PersonalNoKeywordContent> personalNoKeywordContents = keywordContentService.list(sql);
        for (PersonalNoKeywordContent personalNoKeywordContent : personalNoKeywordContents) {
            if("邀请入群".equals(personalNoKeywordContent.getContentType())){
                String[] split = personalNoKeywordContent.getContent().split("/");
                PersonalNoGroupCategory personalNoGroupCategory = groupCategoryService.getPersonalNoGroupCategory(split);
                personalNoKeyword.setGroupName(personalNoGroupCategory==null?"":personalNoGroupCategory.getCname());
            }
        }
        personalNoKeyword.setKeywordContentList(personalNoKeywordContents);
        personalNoKeyword.setKeywordContentShow(WebConst.getKeyWordContentShow(personalNoKeywordContents));
        sql = "select id,personal_no_id,personal_no_wx_id,personal_no_nick_name,keyword_id from "+DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_and_keyword)+" where keyword_id = "+personalNoKeyword.getId();
        List<PersonalNoAndKeyword> personalNoAndKeywordList = personalNoAndKeywordService.list(sql);
        StringBuffer temp = new StringBuffer();
        temp.append("(");
        List<PersonalNo> personalNos = new ArrayList<>();
        String sql1 = "";
        for (int i = 0; i < personalNoAndKeywordList.size(); i++) {
            if(i>0){
                temp.append(",");
            }
            temp.append(personalNoAndKeywordList.get(i).getPersonalNoNickName());
            sql1 = DaoGetSql.getById(ZCDBPersonalNo, personalNoAndKeywordList.get(i).getPersonalNoId());
            PersonalNo one = noService.getOne(sql1);
            personalNos.add(one);
        }
        temp.append(")");
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
        String s = DaoGetSql.deleteById(DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_keyword_content), keyWordId);
        keywordContentService.delete(s);
        s = DaoGetSql.deleteById(ZCDBKeyWord,keyWordId);
        baseMapper.deleteById(keyWordId);
    }

    /**
     * 根据关键词查询关键词
     * @param keyword
     * @return
     */
    @Override
    public PersonalNoKeyword getByKeyWord(String keyword) {
        String sql = DaoGetSql.getSql("select * from " + ZCDBKeyWord + " where keyword = ? limit 0,1" , keyword);
        return keywordMapper.getOne(sql);
    }

    /**
     * 开启关闭关键词
     * @param keyWordId
     */
    @Override
    public void onById(Integer keyWordId) {
        String sql = DaoGetSql.getById(ZCDBKeyWord, keyWordId);
        PersonalNoKeyword personalNoKeyword = keywordMapper.getOne(sql);
        personalNoKeyword.setDb(ZCDBKeyWord);
        if(personalNoKeyword.getDeleted()==0){
            personalNoKeyword.setDeleted(1);
            keywordMapper.updateOne(personalNoKeyword);
        }else {
            personalNoKeyword.setDeleted(0);
            keywordMapper.updateOne(personalNoKeyword);
        }

    }

    @Override
    public List<PersonalNoKeyword> getStart() {
        String sql = DaoGetSql.getSql("select * from " + ZCDBKeyWord + " where id in(1,2,3)");
        List<PersonalNoKeyword> start = keywordMapper.list(sql);
        List<PersonalNoKeyword> returnList = new ArrayList<>();
        for (PersonalNoKeyword personalNoKeyword : start) {
            PersonalNoKeyword infoById = getInfoById(personalNoKeyword);
            returnList.add(infoById);
        }
        return returnList;
    }

    @Override
    public Integer add(PersonalNoKeyword keyword) {
        log.info("删除原有的关键词内容");
        PersonalNoKeyword byKeyWord1 = getByKeyWord(keyword.getKeyword());
        keyword.setDb(ZCDBKeyWord);
        if(!VerifyUtils.isEmpty(byKeyWord1)) {
            String sql = "delete from " + DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_keyword_content) + " where personal_no_keyword_id = " + byKeyWord1.getId();
            keywordContentService.delete(sql);
            sql ="DELETE from " + DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_and_keyword) + " WHERE keyword_id = " + byKeyWord1.getId();
            personalNoAndKeywordService.delete(sql);
            baseMapper.updateOne(keyword);
        }else {
            baseMapper.add(keyword);
        }
        for (PersonalNoKeywordContent personalNoKeywordContent : keyword.getKeywordContentList()) {
            personalNoKeywordContent.setPersonalNoKeywordId(keyword.getId());
            personalNoKeywordContent.setDb(DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no_keyword_content));
            Integer save = keywordContentService.add(personalNoKeywordContent);
            if(save==0){
                log.info("插入关键词内容出错了");
                throw new RuntimeException("插入关键词内容出错了");
            }
        }
        String ids = DaoGetSql.getIds(keyword.getPersonalIdList());
        String sql = DaoGetSql.getSql("select * from " + DB.DBAndTable(DB.PERSONAL_ZC_01, DB.personal_no) + " where id in " + ids);
        List<PersonalNo> list = personalNoService.list(sql);
        for (PersonalNo personalNo : list) {
            PersonalNoAndKeyword noAndKeyword = new PersonalNoAndKeyword();
            noAndKeyword.setPersonalNoId(personalNo.getId());
            noAndKeyword.setPersonalNoWxId(personalNo.getWxId());
            noAndKeyword.setPersonalNoNickName(personalNo.getNickname());
            noAndKeyword.setKeywordId(keyword.getId());
            noAndKeyword.setDb(DB.DBAndTable(DB.PERSONAL_ZC_01,DB.personal_no_and_keyword));
            personalNoAndKeywordService.add(noAndKeyword);
        }
        return 1;
    }

    @Override
    public Integer delete(String sql) {
        return keywordMapper.delete(sql);
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
