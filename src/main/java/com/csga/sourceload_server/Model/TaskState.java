package com.csga.sourceload_server.Model;

public enum TaskState {

    Finish("正常结束",0),Running("正在运行",1),Error("异常",2),Stop("手动停止",3),Start("未运行",4);

    // 成员变量
    private String name;
    private int index;
    // 构造方法
    TaskState(String name, int index) {
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
