package com.dataexchange.server.qo;

/**
 * @Auther: Administrator
 * @Date: 2018-12-21 10:38
 * @Description:
 */
public class SelectQO extends DBConnectionQO {

    private String sql;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
