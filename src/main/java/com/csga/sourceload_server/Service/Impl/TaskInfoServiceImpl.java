package com.csga.sourceload_server.Service.Impl;

import com.csga.sourceload_server.Model.TaskInfo;
import com.csga.sourceload_server.Repository.TaskInfoRepository;
import com.csga.sourceload_server.Service.TaskInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskInfoServiceImpl implements TaskInfoService {

    @Autowired
    TaskInfoRepository repository;

    @Override
    public List<TaskInfo> getAll() {
        return repository.findAll();
    }

    @Override
    public List<TaskInfo> getByCreator(Integer creator) {
        return repository.findByCreatorId(creator);
    }

    @Override
    public TaskInfo addOrUpdateTaskInfo(TaskInfo taskInfo) {
        return repository.saveAndFlush(taskInfo);
    }

    @Override
    public void removeTaskInfo(TaskInfo taskInfo) {
        repository.delete(taskInfo);
    }

    @Override
    public void removeTaskInfoById(Integer id) {
        repository.deleteById(id);
    }
}
