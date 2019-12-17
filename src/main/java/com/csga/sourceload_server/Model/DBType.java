package com.csga.sourceload_server.Model;

public enum DBType {

    ORACLE("ORACLE"), MYSQL("MYSQL");

    private String name;

    DBType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
