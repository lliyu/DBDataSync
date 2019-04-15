package com.dataexchange.server.producer;

import com.alibaba.fastjson.JSONObject;
import com.dataexchange.server.dao.DBsDao;
import com.dataexchange.server.qo.DBsQO;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * ����mysql�е����ݵ�mq��
 *
 * @author ly
 * @create 2019-02-06 22:58
 **/
public class DataProducer {

    @Autowired
    private DBsDao dBsDao;

    private static String DATA_SYNC_EXCHANGE = "data_sync_exchange";
    private static String ROUTE_KEY = "data";

    public void syncData(DBsQO source, DBsQO to, String tableName) throws IOException, TimeoutException, SQLException, ClassNotFoundException {

        //todo �������������ͨ�����л��ķ�ʽ�޷�ת������
//        Object deserialize = SerializationUtils.deserialize(SerializationUtils.serialize(source));
//        if(deserialize instanceof DBsQO){
//            tempSource = (DBsQO) deserialize;
//            tempSource.setTableName(tableName);
//        }
        //���ƶ���֤�ڶ��̻߳�����ǰ��Ķ��󲻻ᱻ����
        DBsQO tempSource = JSONObject.parseObject(JSONObject.toJSONString(source), DBsQO.class);
        tempSource.setTableName(tableName);
        DBsQO tempTo = JSONObject.parseObject(JSONObject.toJSONString(to), DBsQO.class);
        tempTo.setTableName(tableName);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("39.105.108.154");
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setPort(5672);

        //��������
        Connection connection = factory.newConnection();
        //�����ŵ�
        Channel channel = connection.createChannel();
        //���������������������Ͷ���  ��Ϊһ�����ȴ�������
        channel.exchangeDeclare(DATA_SYNC_EXCHANGE, BuiltinExchangeType.DIRECT);
        String key = tempTo.getIp() + ":" + tempTo.getDbName() + ":" + tempTo.getTableName();
        channel.queueDeclare(key + ":data_queue", false, false, true, null);

        channel.queueBind(key + ":data_queue",
                DATA_SYNC_EXCHANGE, key + ":" + ROUTE_KEY);

        channel.confirmSelect();//����Ϊconfirmģʽ

        //�ж����ݱ��Ƿ���ڣ������������ݽṹ����ͬ��
        if(!DBsDao.isTableExist(tempTo)){
            channel.queueDeclare("data_structure_queue", false, false, true, null);

            channel.queueBind("data_structure_queue", DATA_SYNC_EXCHANGE, "structure");
            String tableSQL = DBsDao.getCreateTableSQL(tempSource);
            channel.basicPublish(DATA_SYNC_EXCHANGE, "structure", null, tableSQL.getBytes("utf-8"));
        }

        //��������
        List<Object> data = dBsDao.getRows(tempSource);
        data.stream().forEach(entity -> {
            try {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(stream);
                oos.writeObject(entity);
                byte[] bytes = stream.toByteArray();
                JSONObject jsonObject = (JSONObject) entity;
                channel.basicPublish(DATA_SYNC_EXCHANGE, key + ":" + ROUTE_KEY, null, bytes);
                if(channel.waitForConfirms()){
                    //�������ͳɹ� �޸ı�����Ϣ���е�״̬
                    System.out.println(tableName + ":" + jsonObject.get("id") + ":��Ϣ���ͳɹ�");
                    //DBData.updateStatus(entity,"confirm");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        channel.close();
        connection.close();

    }
}
