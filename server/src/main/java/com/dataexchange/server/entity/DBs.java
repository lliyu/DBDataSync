package com.dataexchange.server.entity;

import java.util.List;

/**
 * @Auther: liyu
 * @Date: 2018-11-06 09:34
 * @Description:
 */
public class DBs {

    private String dbName;

    private List<Table> tables;

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }
}
