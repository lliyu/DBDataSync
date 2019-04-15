package com.dataexchange.server.qo;

import java.io.Serializable;

/**
 * @Auther: Administrator
 * @Date: 2018-12-20 19:14
 * @Description: 主要用于接收数据库连接参数
 */
public class DBConnectionQO extends BaseQO {
    private String dbType;
    private String port;
    private String ip;
    private String username;
    private String password;
    private String dbName;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
}
