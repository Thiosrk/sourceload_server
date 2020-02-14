package com.csga.sourceload_server.Utils.AsyncTask;

import com.csga.sourceload_server.Model.TaskInfo;
import com.csga.sourceload_server.Model.TaskState;
import com.csga.sourceload_server.Repository.TaskInfoRepository;
import com.csga.sourceload_server.Utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 异步任务管理器
 */
public enum  AsyncTaskManager {

    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskManager.class);

    private Map<Integer, TaskInfo> taskContainer = new HashMap<>(16);

    AsyncTaskExecutor asyncTaskExecutor;

    TaskInfoRepository taskInfoRepository;


    /**
     * 初始化任务
     *
     * @return taskInfo
     */
    public void initTaskContainer() {

        this.taskInfoRepository = (TaskInfoRepository) SpringContextUtil.getBean(TaskInfoRepository.class);
        this.asyncTaskExecutor = (AsyncTaskExecutor) SpringContextUtil.getBean(AsyncTaskExecutor.class);

        List<TaskInfo> list = taskInfoRepository.findAll();
        for (TaskInfo task : list) {
            this.setTaskInfo(task);
        }
    }

    /**
     * 初始化任务
     * @param asyncTaskConstructor 异步任务构造器
     * @return taskInfo
     */
    public TaskInfo submit(AsyncTask asyncTaskConstructor,TaskInfo taskInfo) throws Exception {
        System.out.println(taskInfo);
        asyncTaskExecutor.executor(asyncTaskConstructor,taskInfo.getId());
        return taskInfo;
    }

    /**
     * 保存任务信息
     *
     * @param taskInfo 任务信息
     */
    public void setTaskInfo(TaskInfo taskInfo) {
        taskContainer.put(taskInfo.getId(), taskInfo);
        logger.info("新增任务，ID："+taskInfo.getId());
    }

    public List<TaskInfo> getAllTasks(){
        return new ArrayList<>(taskContainer.values());
    }

    /**
     * 获取任务信息
     *
     * @param taskId 任务ID
     * @return
     */
    public TaskInfo getTaskInfo(Integer taskId) {
        return taskContainer.get(taskId);
    }

    /**
     * 获取任务状态
     *
     * @param taskId 任务ID
     * @return
     */
    public TaskState getTaskStatus(Integer taskId) {
        return getTaskInfo(taskId).getTaskState();
    }

    public void stopTask(Integer taskId){
        taskContainer.get(taskId);
    }

}
