package com.undsf.example.conf;

import com.undsf.example.entity.Member;
import com.undsf.example.mapper.MemberMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {
    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setTypeAliases(new Class[]{
                Member.class
        });
        //sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }

    private <T> MapperFactoryBean getMapper(Class<T> mapperInterface) {
        MapperFactoryBean<T> mapperFactoryBean = new MapperFactoryBean<T>();
        try {
            mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory());
            mapperFactoryBean.setMapperInterface(mapperInterface);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return mapperFactoryBean;
    }

    @Bean
    public MapperFactoryBean memberMapper() {
        return getMapper(MemberMapper.class);
    }

}