package com.dataexchange.server.service;

import com.dataexchange.server.entity.DBs;
import com.dataexchange.server.qo.DBsQO;

import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2019-04-08 15:49
 * @Description:
 */
public interface DataService {

    List<Object> getRows(DBsQO qo) throws SQLException, InterruptedException;

    List<DBs> selectList(DBsQO qo) throws SQLException;

    List<String> selectTableList(DBsQO qo) throws SQLException;

    Long queryTableTotal(DBsQO qo) throws SQLException;
}
