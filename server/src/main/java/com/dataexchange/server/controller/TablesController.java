package com.dataexchange.server.controller;

import com.dataexchange.server.qo.DBConnectionQO;
import com.dataexchange.server.qo.DBsQO;
import com.dataexchange.server.service.ConnectionService;
import com.dataexchange.server.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2019-04-09 15:34
 * @Description:
 */
@Controller
@RequestMapping("/table")
public class TablesController {

    @Autowired
    private DataService dataService;

    @Autowired
    private ConnectionService connectionService;

    @ResponseBody
    @RequestMapping("/list")
    public List<String> getSelectList(DBsQO qo) throws SQLException {
        if(!StringUtils.isEmpty(qo.getIp())){
            DBConnectionQO conn = connectionService.getConn(qo);
            qo.setUsername(conn.getUsername());
            qo.setPassword(conn.getPassword());
            qo.setPort(conn.getPort());
            qo.setDbType(conn.getDbType());
        }
        if(StringUtils.isEmpty(qo.getDbName())){
            qo.setDbName("information_schema");
        }
        if(StringUtils.isEmpty(qo.getTableName())){
            qo.setTableName("character_sets");
        }
        List<String> dBs = dataService.selectTableList(qo);
        return dBs;
    }
}
