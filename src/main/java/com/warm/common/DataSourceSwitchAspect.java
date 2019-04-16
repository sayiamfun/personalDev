package com.warm.common;

import com.warm.utils.VerifyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author DGD
 * @date 2018/2/7.
 */

@Component
@Aspect
@Order(-100)
@Slf4j
public class DataSourceSwitchAspect {

    @Pointcut("execution(* com.warm.system.service.db1..*.*(..))")
    private void db1Aspect() {
    }
    @Pointcut("execution(* com.warm.system.service.db2..*.*(..))")
    private void db2Aspect() {
    }
    @Pointcut("execution(* com.warm.system.service.db3..*.*(..))")
    private void db3Aspect() {
    }
    @Pointcut("execution(* com.warm.system.service.db4..*.*(..))")
    private void db4Aspect() {
    }

    @Before( "db1Aspect()" )
    public void db1(JoinPoint joinPoint) {
        log.info("切换到db1 数据源...");
        setDataSource(joinPoint,DBTypeEnum.db1);
    }

    @Before("db2Aspect()" )
    public void db2 (JoinPoint joinPoint) {
        log.info("切换到db2 数据源...");
        setDataSource(joinPoint,DBTypeEnum.db2);
    }
    @Before("db3Aspect()" )
    public void db3 (JoinPoint joinPoint) {
        log.info("切换到db3 数据源...");
        setDataSource(joinPoint,DBTypeEnum.db3);
    }
    @Before("db4Aspect()" )
    public void db4 (JoinPoint joinPoint) {
        log.info("切换到db3 数据源...");
        setDataSource(joinPoint,DBTypeEnum.db4);
    }

    /**
     * 添加注解方式,如果有注解优先注解,没有则按传过来的数据源配置
     * @param joinPoint
     * @param dbTypeEnum
     */
    private void setDataSource(JoinPoint joinPoint, DBTypeEnum dbTypeEnum) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DataSourceSwitch dataSourceSwitch = methodSignature.getMethod().getAnnotation(DataSourceSwitch.class);
        if (VerifyUtils.isEmpty(dataSourceSwitch) || VerifyUtils.isEmpty(dataSourceSwitch.value())) {
            DbContextHolder.setDbType(dbTypeEnum);
        }else{
            log.info("根据注解来切换数据源,注解值为:"+dataSourceSwitch.value());
            switch (dataSourceSwitch.value().getValue()) {
                case "db1":
                    DbContextHolder.setDbType(DBTypeEnum.db1);
                    break;
                case "db2":
                    DbContextHolder.setDbType(DBTypeEnum.db2);
                    break;
                case "db3":
                    DbContextHolder.setDbType(DBTypeEnum.db3);
                    break;
                case "db4":
                    DbContextHolder.setDbType(DBTypeEnum.db4);
                    break;
                default:
                    DbContextHolder.setDbType(dbTypeEnum);
            }
        }
    }
}
