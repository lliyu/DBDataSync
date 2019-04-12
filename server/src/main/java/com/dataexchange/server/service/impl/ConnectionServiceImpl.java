package com.dataexchange.server.service.impl;

import com.dataexchange.server.dao.ConnectionDao;
import com.dataexchange.server.qo.DBConnectionQO;
import com.dataexchange.server.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2019-04-10 17:45
 * @Description:
 */
@Service("connectionService")
public class ConnectionServiceImpl implements ConnectionService {

    @Autowired
    private ConnectionDao connectionDao;

    @Override
    public void registerConn(DBConnectionQO qo) throws SQLException {
        connectionDao.registerConn(qo);
    }

    @Override
    public DBConnectionQO getConn(DBConnectionQO qo) throws SQLException {
        return connectionDao.getDBConnFromIp(qo.getIp());
    }

    @Override
    public List<String> getDBConnList() throws SQLException {
        return connectionDao.getDBConnList();
    }
}
