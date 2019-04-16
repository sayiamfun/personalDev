package com.warm.utils;

import io.swagger.models.auth.In;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Vector;

public class VerifyUtils {
    //设置起始页默认值
    public static Integer setPageNum(Long pageNum){
        if(null == pageNum || StringUtils.isEmpty(pageNum)){
            pageNum = 1L;
        }
        return pageNum.intValue();
    }
    //设置每页默认显示条数
    public static Integer setSize(Long size){
        if(null == size || StringUtils.isEmpty(size)){
            size = 10L;
        }
        return size.intValue();
    }
    //判断单个对象是否为空
    public static boolean isEmpty(Object object) {
        if(null == object || StringUtils.isEmpty(object)){
            return true;
        }
        return false;
    }
    //判断集合是否为空
    public static boolean collectionIsEmpty(Collection<?> collection){
        if(null == collection || collection.size() == 0){
            return true;
        }
        return false;
    }
    //消除集合内的空置
    public static void cleaNull(Collection collection){
        Collection nuCon = new Vector();
        nuCon.add(null);
        collection.removeAll(nuCon);
    }

}
