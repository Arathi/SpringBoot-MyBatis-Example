# Spring Boot整合MyBatis的Gradle示例项目

## 1. build.gradle文件

添加buildscript，主要是在buildscript的dependencies中增加一个spring boot相关的gradle插件，

    buildscript {
        repositories {
            mavenCentral()
        }
        dependencies {
            classpath("org.springframework.boot:spring-boot-gradle-plugin:1.3.3.RELEASE")
        }
    }

在dependencies中添加Spring的包，MyBatis的包，特别注意的是要加一个DataSource相关的包：

    dependencies {
        //Spring
        compile 'org.springframework.boot:spring-boot-starter-web:1.3.3.RELEASE'
        compile 'org.springframework:spring-jdbc:4.2.5.RELEASE'
    
        //MyBatis
        compile 'org.mybatis:mybatis:3.3.1'
        compile 'org.mybatis:mybatis-spring:1.2.4'
        compile 'mysql:mysql-connector-java:5.1.38'
    
        //DataSourceBuilder dependency
        compile 'org.apache.commons:commons-dbcp2:2.1.1'
    
        //Gson
        compile 'com.google.code.gson:gson:2.6.2'
    
        testCompile 'junit:junit:4.12'
    }

DataSourceBuilder支持4种不同的DataSource实现：

* org.apache.tomcat.jdbc.pool.DataSource
* com.zaxxer.hikari.HikariDataSource
* org.apache.commons.dbcp.BasicDataSource
* org.apache.commons.dbcp2.BasicDataSource

这里我们选用了Apache的Commons DBCP 2。

## 2. 数据库配置

有个专门的数据库配置类`com.undsf.example.conf.DatabaseConfiguration`。

这个类用于整合MyBatis。程序启动后会将`application.properties`这个配置文件中以`spring.datasource`开头的配置项注入到dataSource方法（TODO 这个说法可能不太对），用于创建一个DataSource。

以后增加Mapper的时候不要忘了在这个类中增加对应的方法以及类的别名(alias)。

## 3. 结构与约定

* Controller放在`com.undsf.example.controller`包下
    * Controller类本身需要加上`@Controller`或者`@RestController`注解
    * Controller中如果有需要注入的Service成员属性，这个成员属性要加上`@Autowires`注解
* Service分离了接口和实现
    * 接口命名为`IXxxService`，放在`com.undsf.example.service`包下
    * 实现命名为`XxxServiceImpl`，放在`com.undsf.example.service.impl`包下
    * 实现需要加上`@Service`注解
    * Service实现中如果有用到需要注入的Mapper成员属性，这个成员同样要加上`@Autowires`注解
* 持久化对象目前放在`com.undsf.example.entity`包下
    * 主要是和Spring Boot风格统一，按作用命名的话，其实可以搞个`po`包
    * 如果成员属性的名称和数据库中的字段名对不上，需要在Mapper中做映射
* Mapper的接口和xml配置放在`com.undsf.example.mapper`下
    * 接口放在`src/main/java`目录下
    * xml配置文件放在`src/main/resources`目录下，实际上编译出来的xml文件要放在Mapper接口的同一个包中
    * 接口中的方法，要在xml配置文件中有所体现

## 4. 启动

启动前要将`application.properties.sample`改名为`application.properties`，并且按实际情况修改数据库连接信息。

示例中所用到的member表只有两个字段，DDL如下：

    CREATE TABLE member (
        member_id varchar(255) NOT NULL,
        nickname varchar(255) NOT NULL,
        PRIMARY KEY (member_id)
    );

目前是在IDEA中创建Spring Boot类型的Run/Debug Configuration来执行`com.undsf.example.Application`。

因为gradle添加了war插件，war包似乎可以直接用`java -jar`命令执行，也可以将生成war包放在Tomcat等容器中执行。
