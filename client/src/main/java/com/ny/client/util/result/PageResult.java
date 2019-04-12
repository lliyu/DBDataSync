package com.ny.client.util.result;

/**
 * @Auther: Administrator
 * @Date: 2019-04-09 18:48
 * @Description:
 */
public class PageResult {
    private int totalPage;
    private long totalCount;
    private int pageCount = 10;

    private Object data;

    public long getTotalPage() {
        return totalCount%pageCount==0?totalCount/pageCount:totalCount/pageCount+1;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
