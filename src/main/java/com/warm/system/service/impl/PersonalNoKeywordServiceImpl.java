package com.warm.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.warm.system.entity.PersonalNoKeyword;
import com.warm.system.entity.PersonalNoKeywordContent;
import com.warm.system.mapper.PersonalNoKeywordMapper;
import com.warm.system.service.db1.PersonalNoKeywordContentService;
import com.warm.system.service.db1.PersonalNoKeywordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.VerifyUtils;
import com.warm.utils.WebConst;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
    /**
     * 根据关键字模糊查询
     * @param page
     * @param keyWord
     * @return
     */
    @Override
    public Page<PersonalNoKeyword> pageQuery(Page<PersonalNoKeyword> page, String keyWord) {
        EntityWrapper<PersonalNoKeyword> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        List<PersonalNoKeyword> list = new ArrayList<>();
        entityWrapper.notIn("id", Arrays.asList(new String[]{"1","2","3"}));
        if(!"-1".equals(keyWord)){
            entityWrapper.like("keyword", keyWord);
        }
        List<PersonalNoKeyword> personalNoKeywords = baseMapper.selectPage(page, entityWrapper);
        for (PersonalNoKeyword record : personalNoKeywords) {
            PersonalNoKeyword infoById = getInfoById(record.getId());
            list.add(infoById);
        }
        page.setRecords(list);
        return page;
    }

    /**
     * 添加或修改关键词
     * @param keyword
     * @return
     */
    @Override
    public boolean insertInfo(PersonalNoKeyword keyword) {
        log.info("删除原有的关键词内容");
        PersonalNoKeyword byKeyWord1 = getByKeyWord(keyword.getKeyword());
        if(!VerifyUtils.isEmpty(byKeyWord1)) {
            keywordContentService.deleteByKeyWordId(byKeyWord1.getId());
            baseMapper.updateById(keyword);
        }else {
            baseMapper.insert(keyword);
        }

        for (PersonalNoKeywordContent personalNoKeywordContent : keyword.getKeywordContentList()) {
            personalNoKeywordContent.setPersonalNoKeywordId(keyword.getId());
            boolean save = keywordContentService.insert(personalNoKeywordContent);
            if(!save){
                log.info("插入关键词内容出错了");
                throw new RuntimeException("插入关键词内容出错了");
            }
        }
        return true;
    }

    /**
     * 根据id获取关键词信息
     * @param keyWordId
     * @return
     */
    @Override
    public PersonalNoKeyword getInfoById(Integer keyWordId) {
        PersonalNoKeyword personalNoKeyword = baseMapper.selectById(keyWordId);
        List<PersonalNoKeywordContent> personalNoKeywordContents = keywordContentService.listByKeywordId(keyWordId);
        personalNoKeyword.setKeywordContentList(personalNoKeywordContents);
        personalNoKeyword.setKeywordContentShow(WebConst.getKeyWordContentShow(personalNoKeywordContents));
        return personalNoKeyword;
    }

    /**
     * 根据id删除关键词消息
     * @param keyWordId
     */
    @Override
    public void deleteById(Integer keyWordId) {
        keywordContentService.deleteByKeyWordId(keyWordId);
        baseMapper.deleteById(keyWordId);
    }

    /**
     * 根据关键词查询关键词
     * @param keyword
     * @return
     */
    @Override
    public PersonalNoKeyword getByKeyWord(String keyword) {
        EntityWrapper<PersonalNoKeyword> entityWrapper = new EntityWrapper<>();
        entityWrapper.orderDesc(Arrays.asList(new String[]{"id"}));
        entityWrapper.eq("keyword", keyword);
        List<PersonalNoKeyword> keywords = baseMapper.selectList(entityWrapper);
        if(!VerifyUtils.collectionIsEmpty(keywords)) {
            return keywords.get(0);
        }
        return null;
    }

    /**
     * 开启关闭关键词
     * @param keyWordId
     */
    @Override
    public void onById(Integer keyWordId) {
        PersonalNoKeyword personalNoKeyword = keywordMapper.getById(keyWordId);
        if(personalNoKeyword.getDeleted()==0){
            keywordMapper.onById(keyWordId, 1);
        }else {
            keywordMapper.onById(keyWordId, 0);
        }

    }

    @Override
    public List<PersonalNoKeyword> getStart() {
        List<PersonalNoKeyword> returnList = new ArrayList<>();
        List<PersonalNoKeyword> start = keywordMapper.getStart();
        for (PersonalNoKeyword personalNoKeyword : start) {
            PersonalNoKeyword infoById = getInfoById(personalNoKeyword.getId());
            returnList.add(infoById);
        }
        return returnList;
    }
}
