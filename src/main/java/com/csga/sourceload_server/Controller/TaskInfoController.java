package com.csga.sourceload_server.Controller;

import com.csga.sourceload_server.Model.Column;
import com.csga.sourceload_server.Model.TaskInfo;
import com.csga.sourceload_server.Model.TaskState;
import com.csga.sourceload_server.Model.TaskType;
import com.csga.sourceload_server.Service.ColumnService;
import com.csga.sourceload_server.Service.TaskInfoService;
import com.csga.sourceload_server.Utils.AsyncTask.AsyncTaskManager;
import com.csga.sourceload_server.Utils.Data.DataDownloadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/api/taskInfo")
@RestController
public class TaskInfoController {

    @Autowired
    TaskInfoService taskInfoService;
    @Autowired
    ColumnService columnService;

    @GetMapping("/list")
    public List<TaskInfo> list(){
        return taskInfoService.getAll();
    }

    @GetMapping("/timestampList/{tableName}")
    public List<Column> getColumnList(@PathVariable("tableName") String tableName){
        return columnService.getTimeStampColumns(tableName);
    }

    @GetMapping("/{id}")
    public TaskInfo getOne(@PathVariable("id") Integer taskInfoId){
        return taskInfoService.getTaskInfoById(taskInfoId);
    }

    @GetMapping("/create")
    public TaskInfo getTaskInfo(){
        return TaskInfo.builder()
                .totalFromSource(0)
                .totalInLocal(0)
                .creatorId(0)
//                .tableSequence(1)
                .startPage(1)
                .pageSize(1000)
                .createTime(new Date())
                .taskState(TaskState.Start)
                .taskType(TaskType.TOTAL)
                .dataSource("OracleInputSource")
                .build();
    }

    @GetMapping("/list/{creator}")
    public List<TaskInfo> listByCreator(@PathVariable("creator") Integer creatorId){
        if ( 0 != creatorId){
            return taskInfoService.getByCreator(creatorId);
        }else {
            return taskInfoService.getAll();
        }
    }

    @PostMapping("/delete")
    public void delete(@RequestBody TaskInfo taskInfo){
        taskInfoService.removeTaskInfo(taskInfo);
    }

    @PostMapping("/delete/{Id}")
    public void deleteById(@PathVariable("Id") Integer id){
        taskInfoService.removeTaskInfoById(id);
    }

    @PostMapping("update")
    public TaskInfo addOrUpdate(@RequestBody TaskInfo taskInfo){
        if (AsyncTaskManager.INSTANCE.getTaskInfo(taskInfo.getId())==null){
            AsyncTaskManager.INSTANCE.setTaskInfo(taskInfo);
        }
        return taskInfoService.addOrUpdateTaskInfo(taskInfo);
    }

    @GetMapping("testUrl")
    public Boolean testUrl(@RequestBody String requestUrl){
        return DataDownloadUtils.testUrl(requestUrl);
    }



}
