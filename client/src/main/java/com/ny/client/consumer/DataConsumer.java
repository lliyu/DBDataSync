package com.ny.client.consumer;

import com.alibaba.fastjson.JSONObject;
import com.ny.client.dao.DBsDao;
import com.ny.client.entity.DBsQO;
import com.rabbitmq.client.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * 消费端
 *
 * @author ly
 * @create 2019-02-07 11:08
 **/
public class DataConsumer {
    private static String DATA_SYNC_EXCHANGE = "data_sync_exchange";
    private static String DATA_QUEUE = "data_queue";
    private static String DATA_STRUCTURE_QUEUE = "data_structure_queue";
    private static String DATA_ROUTE_KEY = "data";
    private static String STRUCTURE_ROUTE_KEY = "structure";

//    private static JedisClientPool jedisClientPool = new JedisClientPool();

    public void consumer(DBsQO qo) throws IOException, TimeoutException, SQLException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("39.105.108.154");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

        //创建连接
        Connection connection = factory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();
        //由消费者来创建交换机和队列  因为一般是先打开消费者
        //数据队列
        String key = qo.getIp() + ":" + qo.getDbName() + ":" + qo.getTableName();
        channel.exchangeDeclare(DATA_SYNC_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(key + ":" + DATA_QUEUE, false, false, true, null);

        channel.queueBind(key + ":" + DATA_QUEUE, DATA_SYNC_EXCHANGE, key + ":" + DATA_ROUTE_KEY);
        //结构队列
        channel.queueDeclare(DATA_STRUCTURE_QUEUE, false, false, true, null);

        channel.queueBind(DATA_STRUCTURE_QUEUE, DATA_SYNC_EXCHANGE, STRUCTURE_ROUTE_KEY);

        //数据结构消费者
        DefaultConsumer consumerStructure = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    String message = new String(body, "utf-8");
                    DBsDao.createTable(message, qo);
//                    System.out.println(consumerTag);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //数据消费者
        DefaultConsumer consumerData = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                ByteArrayInputStream stream = new ByteArrayInputStream(body);
                ObjectInputStream ois = new ObjectInputStream(stream);
                JSONObject object = null;
                try {
                    object = (JSONObject) ois.readObject();
                    //使用类名+id作为全局唯一ID 保证消息不会重复消费
                    Set<Map.Entry<String, Object>> entries = object.entrySet();
                    Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
                    StringBuilder sb = new StringBuilder();
                    sb.append("insert into "+ qo.getTableName() + "(");
                    StringBuilder fields = new StringBuilder();
                    StringBuilder values = new StringBuilder();
                    while (iterator.hasNext()){
                        Map.Entry<String, Object> next = iterator.next();
                        fields.append(next.getKey() + ",");
                        if(next.getValue() instanceof String){
                            values.append("'" + next.getValue() + "',");
                        }else {
                            values.append(next.getValue() + ",");
                        }
                    }

                    sb.append(fields.substring(0, fields.toString().length()-1) + ")");
                    sb.append(" values(" + values.substring(0, values.toString().length()-1) + ")");
                    DBsDao.insert(sb.toString(), qo);
//                    String key = object.getClass().getName() + ":" + object.getString("id");
//                    if(jedisClientPool.get(key)==null){
//                        RemoteDBOperation.insertData(academicWorksEntity);
////                        jedisClientPool.set(key, String.valueOf(academicWorksEntity.getId()));
//                    }
//                    System.out.println(sb.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    //数据插入失败 进行重试
//                    String retry = academicWorksEntity.getClass().getName() + ":retry:" + academicWorksEntity.getId();
//                    String retry = "";
//                    if(Integer.valueOf(jedisClientPool.get(retry)) < 3){
//                        //重试
//                        //todo
//                    }else {
//                        //重试次数过多 将该消息存入失败表中 后面重试或人工补偿
//                        //todo
//                    }
                }
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //判断数据表是否存在，不存在则将数据结构进行同步
        while(!DBsDao.isTableExist(qo)){
            channel.basicConsume(DATA_STRUCTURE_QUEUE, false, consumerStructure);
        }
        System.out.println("开始消费:" + key + ":" + DATA_QUEUE);
        channel.basicConsume(key + ":" + DATA_QUEUE, false, consumerData);
    }
}
