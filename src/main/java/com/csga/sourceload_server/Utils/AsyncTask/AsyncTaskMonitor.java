package com.csga.sourceload_server.Utils.AsyncTask;

import com.csga.sourceload_server.Model.TaskInfo;
import com.csga.sourceload_server.Model.TaskState;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 异步任务监控
 */
@Component
@Aspect
public class AsyncTaskMonitor {


    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskMonitor.class);

    @Around("execution(* com.csga.sourceload_server.Utils.AsyncTask.AsyncTaskExecutor.*(..))")
    public void taskHandle(ProceedingJoinPoint pjp) {
        //获取taskId
        Integer taskId = (Integer) pjp.getArgs()[1];
        //获取任务信息
        TaskInfo taskInfo = AsyncTaskManager.INSTANCE.getTaskInfo(taskId);
        logger.info("AsyncTaskMonitor is monitoring async task：{"+taskId+"}");
        taskInfo.setTaskState(TaskState.Running);
        AsyncTaskManager.INSTANCE.setTaskInfo(taskInfo);
        TaskState status = null;
        try {
            pjp.proceed();
            status = TaskState.Finish;
        } catch (Throwable throwable) {
            status = TaskState.Error;
            throwable.printStackTrace();
            logger.error("AsyncTaskMonitor:async task {"+taskId+"} is failed.Error info:{"+throwable.getMessage()+"}");
        }
        taskInfo.setUpdateTime(new Date());
        taskInfo.setTaskState(status);
        AsyncTaskManager.INSTANCE.setTaskInfo(taskInfo);
    }
}