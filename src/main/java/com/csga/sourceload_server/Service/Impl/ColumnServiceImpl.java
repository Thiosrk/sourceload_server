package com.csga.sourceload_server.Service.Impl;

import com.csga.sourceload_server.Model.Column;
import com.csga.sourceload_server.Repository.ColumnRepository;
import com.csga.sourceload_server.Service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ColumnServiceImpl implements ColumnService {

    @Autowired
    ColumnRepository repository;


    @Override
    public List<Column> getColumns(String tableName) {
        return repository.findByTableName(tableName);
    }

    @Override
    public Column addOrUpdateColumn(Column column) {
        return repository.saveAndFlush(column);
    }

    @Override
    public void addOrUpdateColumns(List<Column> columns) {
        repository.saveAll(columns);
    }

    @Override
    public List<Column> getTimeStampColumns(String tableName) {
        List<Column> columnList = repository.findByTableName(tableName);
        List<Column> newList = new ArrayList<>();
        for (Column column:columnList){
            if (column.getColType().equalsIgnoreCase("date")
                    || column.getColType().equalsIgnoreCase("timestamp")
                    || column.getColType().equalsIgnoreCase("timestamp with local time zone")
                    || column.getColType().equalsIgnoreCase("timestamp with time zone"))
                newList.add(column);
        }
        return newList;
    }

}
