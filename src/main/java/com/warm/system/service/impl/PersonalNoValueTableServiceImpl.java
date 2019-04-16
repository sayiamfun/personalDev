package com.warm.system.service.impl;

import com.warm.system.entity.PersonalNoValueTable;
import com.warm.system.mapper.PersonalNoValueTableMapper;
import com.warm.system.service.db1.PersonalNoValueTableService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.warm.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class PersonalNoValueTableServiceImpl extends ServiceImpl<PersonalNoValueTableMapper, PersonalNoValueTable> implements PersonalNoValueTableService {
    @Autowired
    private PersonalNoValueTableMapper valueTableMapper;
    /**
     * 取得所有的技术任务人员微信id
     * @return
     */
    @Override
    public List<String> listWxIdByType(Integer type) {
        return valueTableMapper.listWxIdByType(type);
    }

    /**
     * 取得检测手机请求任务时间的个人号列表
     * @param type
     * @return
     */
    @Override
    public List<PersonalNoValueTable> listSuperUser(Integer type) {
        return valueTableMapper.listSuperUser(type);
    }

    /**
     * 添加或者修改
     * @param valueTable
     * @return
     */
    @Override
    public boolean insert(PersonalNoValueTable valueTable) {
        if(VerifyUtils.isEmpty(valueTable.getId())) {
            return baseMapper.insert(valueTable) > 0;
        }
        return baseMapper.updateById(valueTable)>0;
    }
}
