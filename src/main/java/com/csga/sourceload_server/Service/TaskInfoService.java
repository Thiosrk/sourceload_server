package com.csga.sourceload_server.Service;

import com.csga.sourceload_server.Model.TaskInfo;
import com.csga.sourceload_server.Model.TaskState;

import java.util.List;

public interface TaskInfoService {

    List<TaskInfo> getAll();

    TaskState getTaskState(Integer taskInfoId);

    List<TaskInfo> getByCreator(Integer creator);

    TaskInfo addOrUpdateTaskInfo(TaskInfo taskInfo);

    TaskInfo getTaskInfoById(Integer taskInfoId);

    TaskInfo getTaskInfoByTableName(String tableName);

    void removeTaskInfo(TaskInfo taskInfo);

    void removeTaskInfoById(Integer id);

}
