package com.ny.client.dao;

import com.alibaba.fastjson.JSONObject;
import com.ny.client.entity.DBsQO;
import com.ny.client.util.DBConnection;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @Auther: liyu
 * @Date: 2018-11-06 09:37
 * @Description:
 */
@Component("dBsDao")
public class DBsDao {

    private static Log log = LogFactory.getLog(DBsDao.class);

    public static void main(String[] args) throws SQLException {

    }

    public static boolean isTableExist(DBsQO qo) throws SQLException {
        String sql = "show tables like '" + qo.getTableName() + "'";
        Connection connection = DBConnection.getConnection(qo);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return true;
        }
        return false;
    }

    public static void createTable(String sql, DBsQO qo) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection(qo);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.execute();
    }

    public static void insert(String sql, DBsQO qo) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getConnection(qo);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
    }
}
