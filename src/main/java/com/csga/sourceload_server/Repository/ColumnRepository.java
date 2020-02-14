package com.csga.sourceload_server.Repository;

import com.csga.sourceload_server.Model.Column;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColumnRepository extends JpaRepository<Column,Long> {

    List<Column> findByTableName(String tableName);

}
