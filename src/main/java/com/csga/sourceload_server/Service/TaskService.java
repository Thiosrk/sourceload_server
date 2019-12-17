package com.csga.sourceload_server.Service;

import com.csga.sourceload_server.Model.TaskInfo;

public interface TaskService {

    TaskInfo stopTask(TaskInfo taskInfo) throws Exception;

}
