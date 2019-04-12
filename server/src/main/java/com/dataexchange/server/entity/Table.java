package com.dataexchange.server.entity;

/**
 * @Auther: liyu
 * @Date: 2018-11-06 10:50
 * @Description:
 */
public class Table {

    private String tableName;

    private String tableType;

    private String engine;

    private int rows;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
