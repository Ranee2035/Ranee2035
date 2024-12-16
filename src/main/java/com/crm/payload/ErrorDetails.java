package com.crm.payload;

import java.util.Date;

public class ErrorDetails {
    private Date date;
    private String Message;
    private String request;

    public ErrorDetails(Date date, String message, String request) {
        this.date = date;
        this.Message = message;
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return Message;
    }
}
