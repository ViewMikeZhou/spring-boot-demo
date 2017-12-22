package com.hengda.screen.model.response;

public class ScreenBrightnessReponse extends  ScreenResponse {
    private byte brightness;
    public ScreenBrightnessReponse(int code, String message, byte brightness) {
        super(code, message);
        this.brightness = brightness;
    }

}
