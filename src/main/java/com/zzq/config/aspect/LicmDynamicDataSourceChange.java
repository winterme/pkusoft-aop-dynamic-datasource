package com.zzq.config.aspect;

import com.zzq.config.datasource.DataSourceRouting;
import com.zzq.config.datasource.DynamicDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
public class LicmDynamicDataSourceChange {

    private AtomicInteger integer = new AtomicInteger();

    private static final Logger logger = LoggerFactory.getLogger(LicmDynamicDataSourceChange.class);

//    @Pointcut(" execution( * com.zzq.licm.service.impl.MsgServiceImpl.selectMsgById(..) ) ")
//    public void licmPointCut(){}

    //@After(" execution( * com.zzq.licm.service.impl.MsgServiceImpl.selectMsgById(..) ) ")
    @After(" execution( * com.zzq.licm.controller.MsgController.getMsgById(..) ) ")
    public void clear(JoinPoint joinPoint ){
        DataSourceRouting.remove();
        logger.info(" data sourece has bean removed... ");
    }

    //@Before(" execution( * com.zzq.licm.service.impl.MsgServiceImpl.selectMsgById(..) ) ")
    @Before(" execution( * com.zzq.licm.controller.MsgController.getMsgById(..) ) ")
    public void set(JoinPoint joinPoint){

        int i = integer.getAndIncrement();

        if( i % 2 == 0  ){
            DataSourceRouting.set( DataSourceRouting.LICM_DATASOURCE_1 );
        }else{
            DataSourceRouting.set( DataSourceRouting.LICM_DATASOURCE_2 );
        }
        logger.info("i is "+ i +" , data source has bean changed ... now data source is " + DataSourceRouting.get());
    }

}
