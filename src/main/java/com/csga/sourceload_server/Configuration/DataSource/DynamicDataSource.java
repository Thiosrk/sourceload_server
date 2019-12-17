//package com.csga.sourceload_server.Configuration.DataSource;
//
//import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
//
//public class DynamicDataSource extends AbstractRoutingDataSource {
//    @Override
//    protected Object determineCurrentLookupKey() {
//        String dataSourceName = DynamicDataSourceContextHolder.getDataSourceType();
//        logger.info("当前数据源是：{"+dataSourceName+"}" );
//        return DynamicDataSourceContextHolder.getDataSourceType();
//    }
//}
