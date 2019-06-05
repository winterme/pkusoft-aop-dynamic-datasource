package com.zzq.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.LinkedHashMap;

@Configuration
@MapperScan(
        basePackages = {"com.zzq.licm.mapper"},
        sqlSessionFactoryRef = "licmSqlSession"
)
public class LicmDataSourceConfig {

    @Bean(name = "licmDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.licm")
    public DataSource setDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "licmDataSource2")
    @ConfigurationProperties(prefix = "spring.datasource.licm2")
    public DataSource setDatasource2(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource(){
        DynamicDataSource sourceRouting = new DynamicDataSource();

        LinkedHashMap<Object , Object> dataSources = new LinkedHashMap<>();
        dataSources.put("licmDataSource", setDataSource());
        dataSources.put("licmDataSource2", setDatasource2());

        sourceRouting.setTargetDataSources(dataSources);

        sourceRouting.setDefaultTargetDataSource(setDataSource());

        return sourceRouting;
    }

    @Bean(name = "licmTransation")
    @Primary
    public DataSourceTransactionManager setTransationManager(){
        return new DataSourceTransactionManager(dynamicDataSource());
    }

    @Bean(name = "licmSqlSession")
    @Primary
    public SqlSessionFactory setSqlSession() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource());
        bean.setMapperLocations( new PathMatchingResourcePatternResolver().getResources("classpath:com/zzq/licm/mapper/*.xml"));
        return bean.getObject();
    }

}
