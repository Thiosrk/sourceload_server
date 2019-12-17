//package com.csga.sourceload_server.Configuration.DataSource;
//
//import com.csga.sourceload_server.Service.DataSourceInfoService;
//import org.apache.log4j.Logger;
//import org.springframework.beans.MutablePropertyValues;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.support.BeanDefinitionRegistry;
//import org.springframework.beans.factory.support.GenericBeanDefinition;
//import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
//import org.springframework.core.env.Environment;
//import org.springframework.core.type.AnnotationMetadata;
//
//import javax.sql.DataSource;
//import java.util.HashMap;
//import java.util.Map;
//
//public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {
//
//    @Autowired
//    DataSourceInfoService service;
//
//    private final Logger logger = Logger.getLogger(DynamicDataSourceRegister.class);
//    //指定默认数据源(springboot2.0默认数据源是hikari如何想使用其他数据源可以自己配置)
//    private static final String DATASOURCE_TYPE_DEFAULT = "com.zaxxer.hikari.HikariDataSource";
//
//    /**
//     * 配置上下文（也可以理解为配置文件的获取工具）
//     */
//    private Environment evn;
//
//    /**
//     * 别名
//     */
//    private final static ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();
//
//    /**
//     * 由于部分数据源配置不同，所以在此处添加别名，避免切换数据源出现某些参数无法注入的情况
//     */
//    static {
//        aliases.addAliases("url", new String[]{"jdbc-url"});
//        aliases.addAliases("username", new String[]{"user"});
//    }
//
//    /**
//     * 默认数据源
//     */
//    private DataSource defaultDataSource;
//
//    /**
//     * 存储我们注册的数据源
//     */
//    private Map<String, DataSource> customDataSources = new HashMap<>();
//
//
//
//    @Override
//    public void setEnvironment(Environment environment) {
//        initDefaultDataSource(environment);
//        initslaveDataSources(environment);
//    }
//
//    private void initDefaultDataSource(Environment env) {
//        // 读取主数据源
//        Map<String, Object> dsMap = new HashMap<>();
//        dsMap.put("driver", env.getProperty("spring.datasource.driver-class-name"));
//        dsMap.put("url", env.getProperty("spring.datasource.url"));
//        dsMap.put("username", env.getProperty("spring.datasource.username"));
//        dsMap.put("password", env.getProperty("spring.datasource.password"));
//        defaultDataSource = buildDataSource(dsMap);
//    }
//
//    private void initslaveDataSources(Environment env) {
//
//        //读取oracle导入库数据源
//        Map<String, Object> dsMap = new HashMap<>();
//        String name = env.getProperty("target.spring.datasource.name");
//        dsMap.put("driver", env.getProperty("target.spring.datasource.driver-class-name"));
//        dsMap.put("url", env.getProperty("target.spring.datasource.url"));
//        dsMap.put("username", env.getProperty("target.spring.datasource.username"));
//        dsMap.put("password", env.getProperty("target.spring.datasource.password"));
//        DataSource ds = buildDataSource(dsMap);
//        customDataSources.put(name,ds);
//        // 读取配置文件获取更多数据源
////        String dsPrefixs = env.getProperty("slave.datasource.names");
////        for (String dsPrefix : dsPrefixs.split(",")) {
////            // 多个数据源
////            Map<String, Object> dsMap = new HashMap<>();
////            dsMap.put("driver", env.getProperty("slave.datasource." + dsPrefix + ".driver"));
////            dsMap.put("url", env.getProperty("slave.datasource." + dsPrefix + ".url"));
////            dsMap.put("username", env.getProperty("slave.datasource." + dsPrefix + ".username"));
////            dsMap.put("password", env.getProperty("slave.datasource." + dsPrefix + ".password"));
////            DataBase ds = buildDataSource(dsMap);
////            slaveDataSources.put(dsPrefix, ds);
////        }
//        //TODO:从数据库获取数据源配置
////        List<DataSourceInfo> dataSourceInfos = service.getAllDataSource();
////        for (DataSourceInfo info : dataSourceInfos) {
////            dsMap = new HashMap<>();
////            name = info.getName();
////            dsMap.put("driver", info.getDriver());
////            dsMap.put("url", info.getUrl());
////            dsMap.put("username", info.getUsername());
////            dsMap.put("password", info.getPassword());
////            ds = buildDataSource(dsMap);
////            customDataSources.put(name,ds);
////        }
//
//
//    }
//
//    /**
//     * ImportBeanDefinitionRegistrar接口的实现方法，通过该方法可以按照自己的方式注册bean
//     */
//    @Override
//    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
//        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
//        //添加默认数据源
//        targetDataSources.put("dataSource", this.defaultDataSource);
//        DynamicDataSourceContextHolder.dataSourceIds.add("dataSource");
//        DynamicDataSourceContextHolder.dataSources.put("dataSource", this.defaultDataSource);
//        //添加其他数据源
//        targetDataSources.putAll(customDataSources);
//        DynamicDataSourceContextHolder.dataSources.putAll(customDataSources);
//        for (String key : customDataSources.keySet()) {
//            DynamicDataSourceContextHolder.dataSourceIds.add(key);
//            logger.info("数据源{"+key+"}注册成功！");
//        }
//
//        //创建DynamicDataSource
//        // bean定义类
//        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
//        // 设置bean的类型，此处DynamicDataSource是继承AbstractRoutingDataSource的实现类
//        beanDefinition.setBeanClass(DynamicDataSource.class);
//        beanDefinition.setSynthetic(true);
//        // 需要注入的参数
//        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
//        // 添加默认数据源，避免key不存在的情况没有数据源可用
//        mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
//        // 添加其他数据源
//        mpv.addPropertyValue("targetDataSources", targetDataSources);
//        //注册 - BeanDefinitionRegistry,将该bean注册为datasource，不使用springboot自动生成的datasource
//        beanDefinitionRegistry.registerBeanDefinition("dataSource", beanDefinition);
//
//        logger.info("动态数据源注册完毕,共注册: "+targetDataSources.keySet().size()+" 个数据源");
//        //设置默认数据源
//        logger.info("启用默认数据源: {dataSource}");
//        DynamicDataSourceContextHolder.setDataSourceType("dataSource");
//
//    }
//
//    private DataSource buildDataSource(Map<String, Object> dataSourceMap) {
//        try {
//            Object type = dataSourceMap.get("type");
//            if (type == null) {
//                type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource
//            }
//            Class<? extends DataSource> dataSourceType;
//            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
//            String driverClassName = dataSourceMap.get("driver").toString();
//            String url = dataSourceMap.get("url").toString();
//            String username = dataSourceMap.get("username").toString();
//            String password = dataSourceMap.get("password").toString();
//            // 自定义DataSource配置
//            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
//                    .username(username).password(password).type(dataSourceType);
//            return factory.build();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//}
