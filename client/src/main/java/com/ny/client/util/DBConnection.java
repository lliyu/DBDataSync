package com.ny.client.util;

import com.ny.client.entity.DBConnectionQO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: Administrator
 * @Date: 2018-11-06 09:38
 * @Description:
 */
public class DBConnection {

    private static Connection connection = null;
    //缓存数据库连接
    private static Map<String, Connection> connectionMap = new ConcurrentHashMap<>(16);

    private static String dirver = "com.mysql.cj.jdbc.Driver";
    //    @Value("${spring.datasource.url}")
    private static String url = "jdbc:mysql://{0}:3306/{1}";

    private static String param = "?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT%2B8";
    //    @Value("${spring.datasource.username}")
    private static String username = "root";
    //    @Value("${spring.datasource.password}")
    private static String password = "root";

//    static {
//        //默认创建一个连接
//        try {
//            Class.forName(dirver);
//            connection = DriverManager.getConnection(url,username,password);
//            connectionMap.put("127.0.0.1:information_schema", connection);
//        }  catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static Connection getConnection(DBConnectionQO qo){
        String key = qo.getIp() + ":" + qo.getDbName();
        if(connectionMap.containsKey(key)){
            return connectionMap.get(key);
        }
        String currUrl = MessageFormat.format(url, qo.getIp(), qo.getDbName());
        try {
            Class.forName(dirver);
            connection = DriverManager.getConnection(currUrl + param,qo.getUsername(),qo.getPassword());
            connectionMap.put(key, connection);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }


}
