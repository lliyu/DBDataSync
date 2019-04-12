package com.dataexchange.server.qo;

/**
 * @author ly
 * @create 2018-11-10 23:30
 **/
public class BaseQO {

    private Integer page;
    private Integer limit;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
