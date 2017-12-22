package com.hengda.screen.model.response;

public class ScreenBrokenResponse extends ScreenResponse {
    int brokenStatus;  // 0  诱导屏损坏 1, 诱导屏正常

    public ScreenBrokenResponse(int code, String message, int brokenStatus) {
        super(code, message);
        this.brokenStatus = brokenStatus;
    }
}
