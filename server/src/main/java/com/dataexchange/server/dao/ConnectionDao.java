package com.dataexchange.server.dao;

import com.dataexchange.server.qo.DBConnectionQO;
import com.dataexchange.server.qo.DBsQO;
import com.dataexchange.server.util.conn.DBConnection;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2019-04-10 17:07
 * @Description:
 */
@Component("connectionDao")
public class ConnectionDao {

    public void registerConn(DBConnectionQO qo) throws SQLException {
        String sql = "insert into sqlconninfo(ip,username,password,port,dbtype) values(?,?,?,?,?)";

        Connection connection = DBConnection.getConnection(qo);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, qo.getIp());
        statement.setString(2, qo.getUsername());
        statement.setString(3, qo.getPassword());
        statement.setString(4, qo.getPort());
        if(StringUtils.isEmpty(qo.getDbType())){
            statement.setString(5, "mysql");
        }
        statement.executeUpdate();
    }

    public List<String> getDBConnList() throws SQLException {
        String sql = "select ip from sqlconninfo";
        DBConnectionQO connectionQO = new DBConnectionQO();
        connectionQO.setIp("127.0.0.1");
        connectionQO.setUsername("root");
        connectionQO.setPassword("root");
        connectionQO.setPort("3306");
        connectionQO.setDbName("onlinesql");
        Connection connection = DBConnection.getConnection(connectionQO);
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<String> ipList = new ArrayList<>();
        while (resultSet.next()){
            ipList.add(resultSet.getString("ip"));
        }
        return ipList;
    }
    public DBConnectionQO getDBConnFromIp(String ip) throws SQLException {
        String sql = "select ip,username,port,password,dbtype from sqlconninfo where ip = ? limit 1";
        DBsQO connectionQO = new DBsQO();
        connectionQO.setIp("127.0.0.1");
        connectionQO.setUsername("root");
        connectionQO.setPassword("root");
        connectionQO.setPort("3306");
        connectionQO.setDbName("onlinesql");
        Connection connection = DBConnection.getConnection(connectionQO);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, ip);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            connectionQO.setIp(resultSet.getString("ip"));
            connectionQO.setPort(resultSet.getString("port"));
            connectionQO.setUsername(resultSet.getString("username"));
            connectionQO.setPassword(resultSet.getString("password"));
            connectionQO.setDbType(resultSet.getString("dbtype"));

        }
        return connectionQO;
    }
}
