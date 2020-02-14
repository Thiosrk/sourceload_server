package com.csga.sourceload_server.Service;

import com.csga.sourceload_server.Model.Column;

import java.util.List;

public interface ColumnService {

   List<Column> getColumns(String tableName);

   Column addOrUpdateColumn(Column column);

   void addOrUpdateColumns(List<Column> column);

    List<Column> getTimeStampColumns(String tableName);

}
