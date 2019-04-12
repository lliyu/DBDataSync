package com.dataexchange.server.util.httpclient;

import com.dataexchange.server.qo.DBsQO;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Administrator
 * @Date: 2019-04-12 11:09
 * @Description:
 */
public class ConsumerRPC {

    public static String doPost(DBsQO qo) {

        Map<String, String> param = new HashMap<>();
        param.put("ip", qo.getIp());
        param.put("dbName", qo.getDbName());
        param.put("username", qo.getUsername());
        param.put("password", qo.getPassword());
        param.put("port", qo.getPort());
        param.put("tableName", qo.getTableName());

        // 创建Httpclient对象
        String url = "http://localhost:8081/consumer";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return resultString;
    }

    public static void main(String[] args) {
        DBsQO qo = new DBsQO();
        qo.setIp("39.105.108.154");
        qo.setDbName("tesedb");
        qo.setUsername("root");
        qo.setPort("3306");
        qo.setPassword("123456");
        qo.setTableName("tb_address");
        Map<String, String> param = new HashMap<>();
        param.put("ip", "39.105.108.154");
        param.put("dbName", "testdb");
        param.put("username", "root");
        param.put("password", "123456");
        param.put("port", "3306");
        param.put("tableName", "tb_address");
        doPost(qo);
    }
}
