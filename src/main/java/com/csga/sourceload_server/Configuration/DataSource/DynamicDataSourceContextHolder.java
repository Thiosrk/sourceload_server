//package com.csga.sourceload_server.Configuration.DataSource;
//
//import org.apache.log4j.Logger;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class DynamicDataSourceContextHolder {
//
//    private static final Logger logger = Logger.getLogger(DynamicDataSourceContextHolder.class);
//    //存放当前线程使用的数据源类型信息
//    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
//    //存放数据源id
//    public static List<String> dataSourceIds = new ArrayList<String>();
//    //存放数据源
//    public static Map<String, DataSource> dataSources= new HashMap<>();
//
//
//    //设置数据源
//    public static void setDataSourceType(String dataSourceType) {
//        logger.info("切换至{"+dataSourceType+"}数据源");
//        contextHolder.set(dataSourceType);
//    }
//
//    //获取数据源
//    public static String getDataSourceType() {
//        return contextHolder.get();
//    }
//
//    //清除数据源
//    public static void clearDataSourceType() {
//        logger.info("清除{"+contextHolder.get()+"}数据源");
//        contextHolder.remove();
//    }
//
//    //判断当前数据源是否存在
//    public static boolean isContainsDataSource(String dataSourceId) {
//        return dataSourceIds.contains(dataSourceId);
//    }
//
//    public static Map<String, DataSource> getDataSources() {
//        return dataSources;
//    }
//
//    public static DataSource getCurrentDataSource() {
//        return dataSources.get(contextHolder.get());
//    }
//
//
//}
