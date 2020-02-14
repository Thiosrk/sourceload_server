package com.csga.sourceload_server.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TaskTable")
public class Column {

    @Id
    @GeneratedValue
    private Long Id;//id

    private String tableName;//表名

    private String colName;//列名

    private String colType;//类型

    private String description;//注释

    public Column(final String tableName, final String colName, final String colType, final String description) {
        this.Id=null;
        this.tableName = tableName;
        this.colName = colName;
        this.colType = colType;
        this.description = description;
    }

    public Column() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColType() {
        return colType;
    }

    public void setColType(String colType) {
        this.colType = colType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Column{" +
                "Id='" + Id + '\'' +
                "tableName='" + tableName + '\'' +
                "colName='" + colName + '\'' +
                ", colType='" + colType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
