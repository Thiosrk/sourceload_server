package com.csga.sourceload_server.Service.Impl;

import com.csga.sourceload_server.Model.TaskInfo;
import com.csga.sourceload_server.Model.TaskState;
import com.csga.sourceload_server.Service.DataSourceInfoService;
import com.csga.sourceload_server.Service.TaskInfoService;
import com.csga.sourceload_server.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskInfoService taskInfoService;

    @Autowired
    DataSourceInfoService dataSourceInfoService;

    @Override
    public TaskInfo stopTask(TaskInfo taskInfo) throws Exception {

        taskInfo.setTaskState(TaskState.Stop);

        return null;
    }

}
