package com.csga.sourceload_server.Controller;

import com.csga.sourceload_server.Model.DataSourceInfo;
import com.csga.sourceload_server.Service.DataSourceInfoService;
import com.csga.sourceload_server.Utils.DataBase.DataSourceConnectionTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/api/dataSource")
@RestController
public class DataSourceInfoController {

    @Autowired
    DataSourceInfoService service;

    @GetMapping("/list")
    public List<DataSourceInfo> list(){
        return service.getAllDataSource();
    }

    @PostMapping("/{id}")
    public DataSourceInfo getOne(@PathVariable("id") Integer creatorId){
        return DataSourceInfo.builder()
                .creator(creatorId)
                .createTime(new Date())
                .build();
    }

    @PostMapping("update")
    public DataSourceInfo addOrUpdate(@RequestBody DataSourceInfo dataSourceInfo){
        return service.addOrUpdateDataSource(dataSourceInfo);
    }

    @PostMapping("testConnection")
    public String testDataSource(@RequestBody DataSourceInfo dsmap){
        if (DataSourceConnectionTest.testConnection(dsmap))
            return "数据源连接成功！";
        else
            return "数据源连接失败，数据源未开启或连接配置出错！";
    }

    @PostMapping("testConnect")
    public String testDataSource(@RequestBody Map<String,String> dsmap){
        if (DataSourceConnectionTest.testConnection(dsmap))
            return "数据源连接成功！";
        else
            return "数据源连接失败，数据源未开启或连接配置出错！";
    }

}
