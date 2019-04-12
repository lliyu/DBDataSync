package com.dataexchange.server.vo;

import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2019-04-11 16:16
 * @Description: 用于数据交换的entity
 */
public class DataChangeEntity {

    private String sourceIp;

    private String toIp;

    private String sourceDb;

    private String toDb;

    private List<String> tables;

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getToIp() {
        return toIp;
    }

    public void setToIp(String toIp) {
        this.toIp = toIp;
    }

    public String getSourceDb() {
        return sourceDb;
    }

    public void setSourceDb(String sourceDb) {
        this.sourceDb = sourceDb;
    }

    public String getToDb() {
        return toDb;
    }

    public void setToDb(String toDb) {
        this.toDb = toDb;
    }

    public List<String> getTables() {
        return tables;
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }
}
