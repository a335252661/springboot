package com.example.bean.basic;


import org.apache.poi.ss.formula.functions.T;

public class MessageResult {
    private Boolean result;
    private String message;
    private String remarks;
    private Object data;


    public MessageResult() {
        //默认处理成功
        result = true;
    }

    public MessageResult(Boolean result, String message, String remarks , T data) {
        this.result = result;
        this.message = message;
        this.remarks = remarks;
        this.data = data;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
