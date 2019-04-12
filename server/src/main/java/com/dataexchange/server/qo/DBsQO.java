package com.dataexchange.server.qo;

/**
 * @Auther: Administrator
 * @Date: 2018-12-18 15:39
 * @Description:
 */
public class DBsQO extends DBConnectionQO {

    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
