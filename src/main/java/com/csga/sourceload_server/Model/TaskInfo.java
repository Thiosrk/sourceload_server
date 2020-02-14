package com.csga.sourceload_server.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class TaskInfo {

    @Id
    @GeneratedValue
    private Integer id;//主键

    private String tableName;//表名

    private Integer totalFromSource;//总数据量(数据源)

    private Integer totalInLocal;//总数据量(本地)

    private Date updateTime;//本地数据更新时间

    private Date createTime;//任务创建时间

    private Integer creatorId;//创建者ID

    private TaskType taskType;//任务形式 0：全量，1：定时，2：定量

    private String timestampField;//时间戳字段

    private Date updateTimestampValue;//最新时间戳

    private Integer timeInterval;//抽取时间间隔

    private String systemId;//任务调用id

    private String requestUrl;//请求地址

    private Integer startPage;//开始页数，默认为1

    private Integer pageSize;//每页数据量，默认1000条记录

//    private Integer tableSequence;//任务对应数据表主键增量

    private String dataSource;//任务对应数据源

    private TaskState taskState;//任务状态

    public static TaskInfo.TaskInfoBuilder builder() {
        return new TaskInfo.TaskInfoBuilder();
    }

    public TaskInfo(final Integer id, final String tableName, final Integer totalFromSource, final Integer totalInLocal, final Date updateTime, final Date createTime, final Integer creatorId, final TaskType taskType, final String timestampField, final Date updateTimestampValue, final Integer timeInterval, final String systemId, final String requestUrl, final Integer startPage, final Integer pageSize, final String dataSource, final TaskState taskState) {
        this.id = id;
        this.tableName = tableName;
        this.totalFromSource = totalFromSource;
        this.totalInLocal = totalInLocal;
        this.updateTime = updateTime;
        this.createTime = createTime;
        this.creatorId = creatorId;
        this.taskType = taskType;
        this.timestampField = timestampField;
        this.updateTimestampValue = updateTimestampValue;
        this.timeInterval = timeInterval;
        this.systemId = systemId;
        this.requestUrl = requestUrl;
        this.startPage = startPage;
        this.pageSize = pageSize;
//        this.tableSequence = tableSequence;
        this.dataSource = dataSource;
        this.taskState = taskState;
    }

    public TaskInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getTotalFromSource() {
        return totalFromSource;
    }

    public void setTotalFromSource(Integer totalFromSource) {
        this.totalFromSource = totalFromSource;
    }

    public Integer getTotalInLocal() {
        return totalInLocal;
    }

    public void setTotalInLocal(Integer totalInLocal) {
        this.totalInLocal = totalInLocal;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public String getTimestampField() {
        return timestampField;
    }

    public void setTimestampField(String timestampField) {
        this.timestampField = timestampField;
    }

    public Date getUpdateTimestampValue() {
        return updateTimestampValue;
    }

    public void setUpdateTimestampValue(Date updateTimestampValue) {
        this.updateTimestampValue = updateTimestampValue;
    }

    public Integer getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(Integer timeInterval) {
        this.timeInterval = timeInterval;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

//    public Integer getTableSequence() {
//        return tableSequence;
//    }

//    public void setTableSequence(Integer tableSequence) {
//        this.tableSequence = tableSequence;
//    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    @Override
    public String toString() {
        return "TaskInfo{" +
                "id=" + id +
                ", tableName='" + tableName + '\'' +
                ", totalFromSource=" + totalFromSource +
                ", totalInLocal=" + totalInLocal +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", creatorId=" + creatorId +
                ", taskType=" + taskType +
                ", timestampField='" + timestampField + '\'' +
                ", updateTimestampValue=" + updateTimestampValue +
                ", timeInterval=" + timeInterval +
                ", systemId='" + systemId + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                ", startPage=" + startPage +
                ", pageSize=" + pageSize +
//                ", tableSequence=" + tableSequence +
                ", dataSource='" + dataSource + '\'' +
                ", taskState=" + taskState +
                '}';
    }

    public static class TaskInfoBuilder {
        private Integer id;
        private String tableName;
        private Integer totalFromSource;
        private Integer totalInLocal;
        private Date updateTime;
        private Date createTime;
        private Integer creatorId;
        private TaskType taskType;
        private String timestampField;
        private Date updateTimestampValue;
        private Integer timeInterval;
        private String systemId;
        private String requestUrl;
        private Integer startPage;
        private Integer pageSize;
//        private Integer tableSequence;
        private String dataSource;
        private TaskState taskState;

        TaskInfoBuilder() {
        }

        public TaskInfo.TaskInfoBuilder id(final Integer id) {
            this.id = id;
            return this;
        }

        public TaskInfo.TaskInfoBuilder tableName(final String tableName) {
            this.tableName = tableName;
            return this;
        }

        public TaskInfo.TaskInfoBuilder totalFromSource(final Integer totalFromSource) {
            this.totalFromSource = totalFromSource;
            return this;
        }

        public TaskInfo.TaskInfoBuilder totalInLocal(final Integer totalInLocal) {
            this.totalInLocal = totalInLocal;
            return this;
        }

        public TaskInfo.TaskInfoBuilder updateTime(final Date updateTime) {
            this.updateTime = updateTime;
            return this;
        }

        public TaskInfo.TaskInfoBuilder createTime(final Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public TaskInfo.TaskInfoBuilder creatorId(final Integer creatorId) {
            this.creatorId = creatorId;
            return this;
        }

        public TaskInfo.TaskInfoBuilder taskType(final TaskType taskType) {
            this.taskType = taskType;
            return this;
        }

        public TaskInfo.TaskInfoBuilder timestampField(final String timestampField) {
            this.timestampField = timestampField;
            return this;
        }

        public TaskInfo.TaskInfoBuilder updateTimestampValue(final Date updateTimestampValue) {
            this.updateTimestampValue = updateTimestampValue;
            return this;
        }

        public TaskInfo.TaskInfoBuilder timeInterval(final Integer timeInterval) {
            this.timeInterval = timeInterval;
            return this;
        }

        public TaskInfo.TaskInfoBuilder systemId(final String systemId) {
            this.systemId = systemId;
            return this;
        }

        public TaskInfo.TaskInfoBuilder requestUrl(final String requestUrl) {
            this.requestUrl = requestUrl;
            return this;
        }

        public TaskInfo.TaskInfoBuilder startPage(final Integer startPage) {
            this.startPage = startPage;
            return this;
        }

        public TaskInfo.TaskInfoBuilder pageSize(final Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

//        public TaskInfo.TaskInfoBuilder tableSequence(final Integer tableSequence) {
//            this.tableSequence = tableSequence;
//            return this;
//        }

        public TaskInfo.TaskInfoBuilder dataSource(final String dataSource) {
            this.dataSource = dataSource;
            return this;
        }

        public TaskInfo.TaskInfoBuilder taskState(final TaskState taskState) {
            this.taskState = taskState;
            return this;
        }

        public TaskInfo build() {
            return new TaskInfo(this.id, this.tableName, this.totalFromSource, this.totalInLocal, this.updateTime, this.createTime, this.creatorId, this.taskType, this.timestampField, this.updateTimestampValue, this.timeInterval, this.systemId, this.requestUrl, this.startPage, this.pageSize, this.dataSource, this.taskState);
        }

        public String toString() {
            return "TaskInfo.TaskInfoBuilder(id=" + this.id +
                    ", tableName=" + this.tableName +
                    ", totalFromSource=" + this.totalFromSource +
                    ", totalInLocal=" + this.totalInLocal +
                    ", updateTime=" + this.updateTime +
                    ", createTime=" + this.createTime +
                    ", creatorId=" + this.creatorId +
                    ", taskType=" + this.taskType +
                    ", timestampField=" + this.timestampField +
                    ", updateTimestampValue=" + this.updateTimestampValue +
                    ", timeInterval=" + this.timeInterval +
                    ", systemId=" + this.systemId +
                    ", requestUrl=" + this.requestUrl +
                    ", startPage=" + this.startPage +
                    ", pageSize=" + this.pageSize +
//                    ", tableSequence=" + this.tableSequence +
                    ", dataSource=" + this.dataSource +
                    ", taskState=" + this.taskState + ")";
        }
    }

}
