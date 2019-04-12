package com.ny.client.controller;

import com.ny.client.consumer.DataConsumer;
import com.ny.client.entity.DBsQO;
import com.ny.client.util.result.ResponseData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @Auther: Administrator
 * @Date: 2019-04-12 11:11
 * @Description:
 */
@Controller
public class ConsumerController {

    @ResponseBody
    @RequestMapping("/consumer")
    public ResponseData consumer(DBsQO qo) throws TimeoutException, SQLException, IOException {
        DataConsumer consumer = new DataConsumer();
        consumer.consumer(qo);
        return new ResponseData(200, "success");
    }

    public ResponseData consumer(List<DBsQO> qos) throws TimeoutException, SQLException, IOException {
        for(DBsQO qo:qos){
            DataConsumer consumer = new DataConsumer();
            consumer.consumer(qo);
        }
        return new ResponseData(200, "success");
    }
}
