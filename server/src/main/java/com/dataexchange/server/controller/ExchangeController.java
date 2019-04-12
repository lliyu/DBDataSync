package com.dataexchange.server.controller;

import com.dataexchange.server.producer.DataProducer;
import com.dataexchange.server.qo.DBsQO;
import com.dataexchange.server.service.ConnectionService;
import com.dataexchange.server.util.httpclient.ConsumerRPC;
import com.dataexchange.server.util.result.ResponseData;
import com.dataexchange.server.vo.DataChangeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: Administrator
 * @Date: 2019-04-11 16:21
 * @Description:
 */
@RequestMapping("/exchange")
@Controller
public class ExchangeController {

    @Autowired
    private ConnectionService connectionService;

    @ResponseBody
    @RequestMapping("/sync")
    public ResponseData exchange(@RequestBody DataChangeEntity entity) throws SQLException {
        DBsQO source = new DBsQO();
        source.setIp(entity.getSourceIp());
        source = (DBsQO) connectionService.getConn(source);
        source.setDbName(entity.getSourceDb());

        DBsQO to = new DBsQO();
        to.setIp(entity.getToIp());
        to = (DBsQO) connectionService.getConn(to);
        to.setDbName(entity.getToDb());

        List<String> tables = entity.getTables();
        ExecutorService executorService = Executors.newFixedThreadPool(tables.size());

        for(String tableName : tables){
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            });
            source.setTableName(tableName);
            to.setTableName(tableName);
            DataProducer dataProducer = new DataProducer();
            try {
                dataProducer.syncData(source, to);
                ConsumerRPC.doPost(to);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ResponseData(200,"已经开始同步，请稍后查看结果");
    }
}
