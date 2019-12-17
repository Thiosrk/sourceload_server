package com.csga.sourceload_server.Repository;

import com.csga.sourceload_server.Model.DataSourceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataSourceInfoRepository extends JpaRepository<DataSourceInfo, Integer> {

    public DataSourceInfo findByName(String name);

    public List<DataSourceInfo> findAllByCreator(Integer creator);

    public List<DataSourceInfo> findAllByDriver(String driver);

    public void deleteByName(String name);

}
