package com.csga.sourceload_server.Utils.AsyncTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步任务执行器
 */
@Component
public class AsyncTaskExecutor {

    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskExecutor.class);
    @Async
    public void executor(AsyncTask asyncTaskGenerator, Integer taskInfoId) throws Exception {
        logger.info("AsyncTaskExecutor is executing async task:{"+taskInfoId+"}");
        asyncTaskGenerator.async(taskInfoId);
    }

}
