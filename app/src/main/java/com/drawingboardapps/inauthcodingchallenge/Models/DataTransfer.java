package com.drawingboardapps.inauthcodingchallenge.Models;

/**
 * Created by Zach on 4/10/2017.
 */
public class DataTransfer {
    protected final Object data;
    protected final int status;
    protected final String msg;

    protected DataTransfer(Object data, int status, String msg){
        this.data = data;
        this.status = status;
        this.msg = msg;
    }

    public final String getMsg() {
        return msg;
    }

    public final Object getData() {
        return data;
    }

    public final int getStatus() {
        return status;
    }
}
