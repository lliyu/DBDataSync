package com.ny.client.util.result;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2018-11-07 14:11
 * @Description:
 */
public class ListResponse extends ResponseData {

    private List data;

    private long count;

    public ListResponse(int code, String message) {
        super(code, message);
    }

    public ListResponse(int code, String message, List data, long count) {
        super(code, message);
        this.data = data;
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
