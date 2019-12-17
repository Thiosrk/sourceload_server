package com.csga.sourceload_server.Model;

public enum TaskType {

    TOTAL("全量任务",0),TIMING("定时任务",1),QUANTITY("定量任务",2);

    // 成员变量
    private String name;
    private int index;
    // 构造方法
    TaskType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
