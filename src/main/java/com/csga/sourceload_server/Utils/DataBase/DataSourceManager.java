package com.csga.sourceload_server.Utils.DataBase;

import com.csga.sourceload_server.Model.DataSourceInfo;
import com.csga.sourceload_server.Repository.DataSourceInfoRepository;
import com.csga.sourceload_server.Utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public enum DataSourceManager {

    INSTANCE;

    DataSourceInfoRepository repository;

    private final Logger logger = LoggerFactory.getLogger(DataSourceManager.class);

    //指定默认数据源(springboot2.0默认数据源是hikari如何想使用其他数据源可以自己配置)
    private final static String DATASOURCE_TYPE_DEFAULT = "com.zaxxer.hikari.HikariDataSource";

    private final static ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();

    /**
     * 由于部分数据源配置不同，所以在此处添加别名，避免切换数据源出现某些参数无法注入的情况
     */
    static {
        aliases.addAliases("url", new String[]{"jdbc-url"});
        aliases.addAliases("username", new String[]{"user"});
    }

    /**
     * 存储我们注册的数据源
     */
    private Map<String, DataSource> dataSourceContainer = new HashMap<>();

    //存放当前线程使用的数据源类型信息
    private final ThreadLocal<String> dsHolder = new ThreadLocal<String>();

    /**
     * 初始化任务
     *
     * @return taskInfo
     */
    public void init() {

        this.repository = (DataSourceInfoRepository) SpringContextUtil.getBean(DataSourceInfoRepository.class);

        List<DataSourceInfo> list = repository.findAll();

        for (DataSourceInfo info : list) {
            this.setDataSource(info);
        }
    }

    /**
     * 新增数据源
     * @param info
     */
    public void setDataSource(DataSourceInfo info){
        logger.info("新增{"+info.getName()+"}数据源");
        dataSourceContainer.put(info.getName(),buildDataSource(info));
    }

    /**
     * 设置当前数据源
     * @param dataSourceName
     */
    public void setCurrentDataSource(String dataSourceName){
        logger.info("切换至{"+dataSourceName+"}数据源");
        dsHolder.set(dataSourceName);
    }

    /**
     * 获取数据源
     */
    public DataSource getCurrentDataSource() {
        return dataSourceContainer.get(dsHolder.get());
    }

    /**
     * 清除数据源
     */
    public void clearDataSource() {
        logger.info("清除{"+dsHolder.get()+"}数据源");
        dsHolder.remove();
    }

    /**
     * 判断当前数据源是否存在
     */
    public boolean isContainsDataSource(String dataSourceId) {
        return dataSourceContainer.containsKey(dataSourceId);
    }

    private DataSource buildDataSource(DataSourceInfo info) {
        try {
            Object type = DATASOURCE_TYPE_DEFAULT;// 默认DataSource
            Class<? extends DataSource> dataSourceType;
            dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
            String driverClassName = info.getDriver();
            String url = info.getUrl();
            String username = info.getUsername();
            String password = info.getPassword();
            // 自定义DataSource配置
            DataSourceBuilder factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
                    .username(username).password(password).type(dataSourceType);
            return factory.build();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
