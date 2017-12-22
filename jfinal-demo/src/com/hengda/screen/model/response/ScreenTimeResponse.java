package com.hengda.screen.model.response;

public class ScreenTimeResponse extends ScreenResponse{
    private String time;
    public ScreenTimeResponse(int code, String message,String time) {
        super(code, message);
        this.time = time;
    }
}
