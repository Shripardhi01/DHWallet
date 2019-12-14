package com.dynast.dhwallet.model;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    public Result(boolean error, String message) {
        this.error = error;
        this.message = message;
    }

    public Result(Boolean error, String message, User user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public boolean getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }


}
