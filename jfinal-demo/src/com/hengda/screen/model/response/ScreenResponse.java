package com.hengda.screen.model.response;

import com.google.gson.Gson;

public class ScreenResponse {

    private int code;  // 0 成功 1 失败
    private String message;   //返回错误信息,成功消息为空

    public ScreenResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String toJson() {

        Gson gson = new Gson();
        return gson.toJson(this);
    }

}
