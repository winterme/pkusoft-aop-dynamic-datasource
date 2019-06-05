package com.zzq.config.aspect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AopConfig {

    @Bean("licmAspect")
    public LicmDynamicDataSourceChange change(){
        return new LicmDynamicDataSourceChange();
    }

}
