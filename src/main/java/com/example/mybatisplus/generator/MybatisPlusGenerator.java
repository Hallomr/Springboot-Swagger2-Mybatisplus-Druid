package com.example.mybatisplus.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MybatisPlusGenerator {
    private static String packageName="com.example.mybatisplus";
    private static String database="mp";
    private static String tableNames="user";

    public static void main(String[] args) {
        generator(packageName,database,tableNames);
    }
    private  static void generator(String packageName, String database,String... tableNames) {

        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(false) // 是否支持AR模式
                .setAuthor("generator") // 作者
                .setOutputDir(System.getProperty("user.dir")+"\\src\\main\\java") // 生成路径
                .setFileOverride(true)  // 文件覆盖
                .setIdType(IdType.AUTO) // 主键策略
                .setServiceName("%sService")  // 设置生成的service接口的名字的首字母是否为I
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController")
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                // IEmployeeService
                .setBaseResultMap(true)//生成基本的resultMap
                .setEnableCache(false)
                .setBaseColumnList(true)//生成基本的SQL片段
                .setSwagger2(true).setOpen(false);


        //2. 数据源配置
        DataSourceConfig dsConfig  = new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL)  // 设置数据库类型
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/"+database+"?serverTimezone=GMT")
                .setUsername("root")
                .setPassword("123");

        //3. 策略配置globalConfiguration中
        StrategyConfig stConfig = new StrategyConfig();
        stConfig.setCapitalMode(false) //全局大写命名
                //.setDbColumnUnderline(false)  // 指定表名 字段名是否使用下划线
                .setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                //.setTablePrefix("tb_")
                .setInclude(tableNames)// 生成的表
                .setRestControllerStyle(true)
                .setEntityLombokModel(true)
                //.setSuperMapperClass("cn.saytime.mapper.BaseMapper")
                ;


        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent(packageName)
                .setMapper("mapper")//dao
                .setService("service")//servcie
                .setController("controller")//controller
                .setServiceImpl("service.impl")
                .setEntity("entity")
                .setXml("mapper");//mapper.xml

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】  ${cfg.abc}
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {

            }
        };

        TemplateConfig tc = new TemplateConfig();
        tc.setController("/templates/controller.java.vm");
        //tc.setService("/templatesMybatis/service.java.vm");
        //tc.setServiceImpl("/templatesMybatis/serviceImpl.java.vm");
        //tc.setEntity("/templatesMybatis/entity.java.vm");
        //tc.setMapper("/templatesMybatis/mapper.java.vm");
        //tc.setXml("/templatesMybatis/mapper.xml.vm");

        //5. 整合配置
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig)
                .setCfg(cfg)
                .setTemplate(tc);

        //6. 执行
        ag.execute();
    }
}
