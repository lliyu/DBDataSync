package com.dataexchange.server.controller;

import com.dataexchange.server.qo.DBConnectionQO;
import com.dataexchange.server.service.ConnectionService;
import com.dataexchange.server.util.result.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2019-04-10 17:05
 * @Description:
 */
@Controller
@RequestMapping("/conn")
public class ConnectionController {

    @Autowired
    private ConnectionService connectionService;

    @RequestMapping("/list")
    @ResponseBody
    public List<String> getConnList() throws SQLException {
        return connectionService.getDBConnList();
    }

    @ResponseBody
    @RequestMapping("/register")
    public ResponseData registerConn(DBConnectionQO qo) throws SQLException {
        connectionService.registerConn(qo);
        return new ResponseData(200, "success");
    }
}
