package com.csga.sourceload_server.Repository;

import com.csga.sourceload_server.Model.TaskInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskInfoRepository extends JpaRepository<TaskInfo,Integer> {

    List<TaskInfo> findByCreatorId(Integer creatorid);

    List<TaskInfo> findByTableName(String tableName);

}
