package com.dataexchange.server.service;

import com.dataexchange.server.qo.DBConnectionQO;

import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2019-04-10 17:06
 * @Description:
 */
public interface ConnectionService {
    void registerConn(DBConnectionQO qo) throws SQLException;

    DBConnectionQO getConn(DBConnectionQO qo) throws SQLException;

    List<String> getDBConnList() throws SQLException;
}
