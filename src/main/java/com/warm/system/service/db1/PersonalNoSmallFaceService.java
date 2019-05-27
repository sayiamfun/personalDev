package com.warm.system.service.db1;

import com.warm.entity.Sql;
import com.warm.system.entity.PersonalNoSmallFace;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 表情库 服务类
 * </p>
 *
 * @author dgd123
 * @since 2019-03-29
 */
public interface PersonalNoSmallFaceService extends IService<PersonalNoSmallFace> {

    Integer add(PersonalNoSmallFace personalNoSmallFace);

    List<PersonalNoSmallFace> listBySql(Sql sql);

    void deleteBySql(Sql sql);
}
