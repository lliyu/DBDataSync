package com.dataexchange.server.service.impl;

import com.dataexchange.server.dao.DBsDao;
import com.dataexchange.server.entity.DBs;
import com.dataexchange.server.qo.DBsQO;
import com.dataexchange.server.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2019-04-08 15:50
 * @Description:
 */

@Service("dataService")
public class DataServiceImpl implements DataService {

    @Autowired
    private DBsDao dBsDao;

    @Override
    public List<Object> getRows(DBsQO qo) throws SQLException, InterruptedException {
        return dBsDao.getRows(qo);
    }

    @Override
    public List<DBs> selectList(DBsQO qo) throws SQLException {
        return dBsDao.getDBs(qo);
    }

    @Override
    public List<String> selectTableList(DBsQO qo) throws SQLException {
        return dBsDao.getTables(qo);
    }

    @Override
    public Long queryTableTotal(DBsQO qo) throws SQLException {
        return dBsDao.queryTableTotal(qo);
    }
}
