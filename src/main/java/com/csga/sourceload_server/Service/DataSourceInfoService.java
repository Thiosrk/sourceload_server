package com.csga.sourceload_server.Service;

import com.csga.sourceload_server.Model.DataSourceInfo;

import java.util.List;

public interface DataSourceInfoService {

    public List<DataSourceInfo> getAllDataSource();

    public DataSourceInfo getDataSourceById(Integer id);

    public DataSourceInfo getDataSourceByName(String name);

    public List<DataSourceInfo> getDataSourcesByCreator(Integer creator);

    public List<DataSourceInfo> getDataSourcesByDriver(String driver);

    public DataSourceInfo addOrUpdateDataSource(DataSourceInfo dataSourceInfo);

    public void removeDataSourceById(Integer id);

    public void removeDataSourceByName(String name);

    public void removeDataSource(DataSourceInfo dataSourceInfo);








}
