package com.dataexchange.server.controller;

import com.dataexchange.server.qo.DBsQO;

import com.dataexchange.server.service.DataService;
import com.dataexchange.server.util.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2019-04-08 15:35
 * @Description:
 */
@Controller
public class HelloController {

    @Autowired
    private DataService dataService;

    @RequestMapping("/")
    public String hello(){
        return "/index";
    }

    @RequestMapping("/page")
    public String page(){
        return "/page";
    }

    @RequestMapping("/sync")
    public String sync(){
        return "/sync";
    }

    @ResponseBody
    @RequestMapping("/data")
    public PageResult data(DBsQO qo) throws SQLException, InterruptedException {
        if(StringUtils.isEmpty(qo.getIp())){
            qo.setIp("127.0.0.1");
        }
        if(StringUtils.isEmpty(qo.getDbName())){
            qo.setDbName("information_schema");
        }
        if(StringUtils.isEmpty(qo.getTableName())){
            qo.setTableName("character_sets");
        }
        PageResult pageResult = new PageResult();
        pageResult.setData(dataService.getRows(qo));
        pageResult.setTotalCount(dataService.queryTableTotal(qo));
        return pageResult;
    }

}
