package com.csga.sourceload_server.Model;

public class Column {

    private String colName;//列名

    private String colType;//类型

    private String comment;//注释

    public Column(final String colName, final String colType, final String comment) {
        this.colName = colName;
        this.colType = colType;
        this.comment = comment;
    }

    public Column() {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Column{" +
                "colName='" + colName + '\'' +
                ", colType='" + colType + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
