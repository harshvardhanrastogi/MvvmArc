package com.kisan.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SmsRequest {

    @Expose
    @SerializedName("From")
    private String from;
    @Expose
    @SerializedName("To")
    private String to;
    @Expose
    @SerializedName("Body")
    private String body;

    public SmsRequest(String from, String to, String body) {
        this.from = from;
        this.to = to;
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
