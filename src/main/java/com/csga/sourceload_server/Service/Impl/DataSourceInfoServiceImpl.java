package com.csga.sourceload_server.Service.Impl;

import com.csga.sourceload_server.Model.DataSourceInfo;
import com.csga.sourceload_server.Repository.DataSourceInfoRepository;
import com.csga.sourceload_server.Service.DataSourceInfoService;
import com.csga.sourceload_server.Utils.DataBase.DataSourceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataSourceInfoServiceImpl implements DataSourceInfoService {

    @Autowired
    DataSourceInfoRepository repository;

    @Override
    public List<DataSourceInfo> getAllDataSource() {
        return repository.findAll();
    }

    @Override
    public DataSourceInfo getDataSourceById(Integer id) {
        return repository.getOne(id);
    }

    @Override
    public DataSourceInfo getDataSourceByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<DataSourceInfo> getDataSourcesByCreator(Integer creator) {
        return repository.findAllByCreator(creator);
    }

    @Override
    public List<DataSourceInfo> getDataSourcesByDriver(String driver) {
        return repository.findAllByDriver(driver);
    }

    @Override
    public DataSourceInfo addOrUpdateDataSource(DataSourceInfo dataSourceInfo) {
        DataSourceManager.INSTANCE.setDataSource(dataSourceInfo);
        return repository.saveAndFlush(dataSourceInfo);
    }

    @Override
    public void removeDataSourceById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void removeDataSourceByName(String name) {
        repository.deleteByName(name);
    }

    @Override
    public void removeDataSource(DataSourceInfo dataSourceInfo) {
        repository.delete(dataSourceInfo);
    }
}
