package com.csga.sourceload_server.Utils.DataBase;

import com.csga.sourceload_server.Model.TaskInfo;
import com.csga.sourceload_server.Utils.AsyncTask.AsyncTask;
import com.csga.sourceload_server.Utils.AsyncTask.AsyncTaskManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask .class);


    @Scheduled(cron = "0 0 23 * * ?")
    public void load_data()throws Exception{

        logger.info("开始执行每日任务：");

        List<TaskInfo> tasks = AsyncTaskManager.INSTANCE.getAllTasks();

        logger.info("任务总量："+tasks.size()+" 个");
        for (TaskInfo task : tasks){
            logger.info("任务{ "+task.getTableName()+" }开始执行");
            AsyncTaskManager.INSTANCE.submit(new AsyncTask(),task);
        }

    }


}
