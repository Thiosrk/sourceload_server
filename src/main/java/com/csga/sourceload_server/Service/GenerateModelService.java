package com.csga.sourceload_server.Service;

import java.io.File;

public interface GenerateModelService {

    /**
     * 1、根据excel生成model类,并编译生成文件
     * 2、装载model类，注入bean
     */
    Boolean genarateModel(File file);




}
