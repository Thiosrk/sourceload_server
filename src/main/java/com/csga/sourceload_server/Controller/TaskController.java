package com.csga.sourceload_server.Controller;

import com.csga.sourceload_server.Model.TaskInfo;
import com.csga.sourceload_server.Model.TaskState;
import com.csga.sourceload_server.Service.TaskInfoService;
import com.csga.sourceload_server.Service.TaskService;
import com.csga.sourceload_server.Utils.AsyncTask.AsyncTask;
import com.csga.sourceload_server.Utils.AsyncTask.AsyncTaskManager;
import com.csga.sourceload_server.Utils.Data.FileUtils;
import com.csga.sourceload_server.Utils.Data.ModelGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequestMapping("/api/task")
@RestController
public class TaskController {

    //注入异步任务管理器

    @Autowired
    ModelGenerator modelGenerator;

    @Autowired
    TaskService taskService;

    @Autowired
    TaskInfoService taskInfoService;

    @GetMapping("/state/{id}")
    public TaskState getState(@PathVariable("id")Integer taskId){
        return AsyncTaskManager.INSTANCE.getTaskStatus(taskId);
    }

    @PostMapping("/generateModel")
    public String generateModel(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        File excelFile = FileUtils.MultipartFileToFile(multipartFile);
        modelGenerator.generateByExcel(excelFile);
        FileUtils.deleteFile(excelFile);
        return "success";
    }

    @PostMapping("/start")
    public TaskInfo start(@RequestBody TaskInfo taskInfo) throws Exception {

        taskInfo.setTaskState(TaskState.Running);
        taskInfo = taskInfoService.addOrUpdateTaskInfo(taskInfo);
        System.out.println(taskInfo);
        taskInfo = AsyncTaskManager.INSTANCE.submit(new AsyncTask(),taskInfo);
        return taskInfo;
    }

    @PostMapping("/stop")
    public TaskInfo stop(@RequestBody TaskInfo taskInfo) throws Exception {
        return taskService.stopTask(taskInfo);
    }

}
