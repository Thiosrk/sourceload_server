package com.csga.sourceload_server.Utils.DataBase;

import com.csga.sourceload_server.Model.DataSourceInfo;

import java.sql.DriverManager;
import java.util.Map;

public class DataSourceConnectionTest {

    public static Boolean testConnection(Map<String,String> dsmap) {
        try {
            Class.forName(dsmap.get("driver"));
            DriverManager.getConnection(dsmap.get("url"), dsmap.get("username"), dsmap.get("password"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Boolean testConnection(DataSourceInfo dsmap) {
        try {
            Class.forName(dsmap.getDriver());
            DriverManager.getConnection(dsmap.getUrl(), dsmap.getUsername(), dsmap.getPassword());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
