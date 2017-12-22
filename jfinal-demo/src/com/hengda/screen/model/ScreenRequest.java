package com.hengda.screen.model;

/**
 * 请求封装类
 */
public class ScreenRequest {
    private String action;              //请求action
    private String ip;                  //请求连接控制器ip
    private int turnOnOrOff;           //0 关,1 开
    private byte brightness;      //屏幕亮度
    private int displayType;            //1:green 2:yellow 3:red
    private int x = 32;                 //显示区域的x坐标
    private int y = 4;                  //显示区域的y坐标
    private int width = 32;             //显示宽度
    private int height = 8;             //显示高度
    private int ruleId;                // 动态区id 根据id 改变动态区
    private int textSize;              //文字大小
    private int textAlign;              //文字显示位置 0 居左 ,1 居中, 2 居右
    private int textColor;              //1:green 2:yellow 3: red
    private String text;                //文字内容
    private int displayStyle;            //文字出场特效   1 无特效,2 从右往左
    public ScreenRequest() {
    }


    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getDisplayStyle() {
        return displayStyle;
    }

    public void setDisplayStyle(int displayStyle) {
        this.displayStyle = displayStyle;
    }

    public int getTurnOnOrOff() {
        return turnOnOrOff;
    }

    public void setTurnOnOrOff(int turnOnOrOff) {
        this.turnOnOrOff = turnOnOrOff;
    }


    public ScreenRequest(String action) {
        this.action = action;
    }

    public String getaction() {
        return action;
    }

    public byte getBrightness() {
        return brightness;
    }

    public void setBrightness(byte brightness) {
        this.brightness = brightness;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int getTextAlign() {
        return textAlign;
    }

    public void setTextAlign(int textAlign) {
        this.textAlign = textAlign;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setaction(String action) {
        this.action = action;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public byte getbrightness() {
        return brightness;
    }

    public void setbrightness(byte brightness) {
        this.brightness = brightness;
    }

    @Override
    public String toString() {
        return "ScreenRequest{" +
                "action='" + action + '\'' +
                ", ip='" + ip + '\'' +
                ", displayType=" + displayType +
                ", x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                ", ruleId=" + ruleId +
                '}';
    }
}
