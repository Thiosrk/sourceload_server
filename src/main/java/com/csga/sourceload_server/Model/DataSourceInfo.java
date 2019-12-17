package com.csga.sourceload_server.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class DataSourceInfo {

    @Id
    @GeneratedValue
    private Integer id;//主键

    private String name;//数据源名称

    private Integer creator;//创建者

    private DBType dbType;//数据库类型

    private String driver;//数据库驱动

    private String url;//数据库链接地址

    private String username;//数据库用户名

    private String password;//数据库密码

    private String connState;//连接状态

    private Date createTime;//创建时间


    public static DataSourceInfo.DataSourceInfoBuilder builder() {
        return new DataSourceInfo.DataSourceInfoBuilder();
    }

    public DataSourceInfo(final Integer id, final String name, final Integer creator, final DBType dbType, final String driver, final String url, final String username, final String password, final String connState, final Date createTime) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.dbType = dbType;
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        this.connState = connState;
        this.createTime = createTime;
    }

    public DataSourceInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConnState() {
        return connState;
    }

    public void setConnState(String connState) {
        this.connState = connState;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public DBType getDbType() {
        return dbType;
    }

    public void setDbType(DBType dbType) {
        this.dbType = dbType;
    }

    @Override
    public String toString() {
        return "DataSourceInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creator=" + creator +
                ", dbType=" + dbType +
                ", driver='" + driver + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", connState='" + connState + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public static class DataSourceInfoBuilder {
        private Integer id;
        private String name;
        private Integer creator;
        private DBType dbType;
        private String driver;
        private String url;
        private String username;
        private String password;
        private String connState;
        private Date createTime;

        DataSourceInfoBuilder() {
        }

        public DataSourceInfo.DataSourceInfoBuilder id(final Integer id) {
            this.id = id;
            return this;
        }

        public DataSourceInfo.DataSourceInfoBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public DataSourceInfo.DataSourceInfoBuilder creator(final Integer creator) {
            this.creator = creator;
            return this;
        }

        public DataSourceInfo.DataSourceInfoBuilder dbType(final DBType dbType) {
            this.dbType = dbType;
            return this;
        }

        public DataSourceInfo.DataSourceInfoBuilder driver(final String driver) {
            this.driver = driver;
            return this;
        }

        public DataSourceInfo.DataSourceInfoBuilder url(final String url) {
            this.url = url;
            return this;
        }

        public DataSourceInfo.DataSourceInfoBuilder username(final String username) {
            this.username = username;
            return this;
        }

        public DataSourceInfo.DataSourceInfoBuilder password(final String password) {
            this.password = password;
            return this;
        }

        public DataSourceInfo.DataSourceInfoBuilder connState(final String connState) {
            this.connState = connState;
            return this;
        }

        public DataSourceInfo.DataSourceInfoBuilder createTime(final Date createTime) {
            this.createTime = createTime;
            return this;
        }

        public DataSourceInfo build() {
            return new DataSourceInfo(this.id, this.name, this.creator, this.dbType, this.driver, this.url, this.username, this.password, this.connState, this.createTime);
        }

        public String toString() {
            return "DataSourceInfo.DataSourceInfoBuilder(id=" + this.id + ", name=" + this.name + ", creator=" + this.creator + ", dbType=" + this.dbType + ", driver=" + this.driver + ", url=" + this.url + ", username=" + this.username + ", password=" + this.password + ", connState=" + this.connState + ", createTime=" + this.createTime + ")";
        }
    }
}
