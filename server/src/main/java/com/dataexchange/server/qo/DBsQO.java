package com.dataexchange.server.qo;

import java.io.Serializable;

/**
 * @Auther: Administrator
 * @Date: 2018-12-18 15:39
 * @Description:
 */
public class DBsQO extends DBConnectionQO implements Serializable {

    private static final long serialVersionUID = 2623710580809771457L;

    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

}
