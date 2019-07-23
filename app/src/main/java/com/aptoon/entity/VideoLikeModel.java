package com.aptoon.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoLikeModel {


    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("status")
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
