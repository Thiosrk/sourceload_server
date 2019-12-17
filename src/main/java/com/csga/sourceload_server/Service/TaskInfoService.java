package com.csga.sourceload_server.Service;

import com.csga.sourceload_server.Model.TaskInfo;

import java.util.List;

public interface TaskInfoService {

    List<TaskInfo> getAll();

    List<TaskInfo> getByCreator(Integer creator);

    TaskInfo addOrUpdateTaskInfo(TaskInfo taskInfo);

    void removeTaskInfo(TaskInfo taskInfo);

    void removeTaskInfoById(Integer id);

}
