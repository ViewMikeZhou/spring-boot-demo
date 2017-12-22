package com.hengda.screen.model.response;

public class ScreenStatusResponse extends ScreenResponse {
    private  byte  screenOnOff;
    public ScreenStatusResponse(int code, String message,byte screenOnOff) {
        super(code, message);
        this.screenOnOff = screenOnOff;
    }
}
