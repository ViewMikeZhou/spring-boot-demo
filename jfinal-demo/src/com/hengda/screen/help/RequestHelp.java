package com.hengda.screen.help;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.hengda.screen.model.ScreenRequest;

import java.util.ArrayList;
import java.util.List;

public class RequestHelp {
/*
    public static ScreenRequest creatRequestByForm(int ac,String action,String ip,String turnOnOrOff) {

        int actionInt;
        try {
            actionInt = Integer.parseInt(action);
        } catch (NumberFormatException e) {
            return null;
        }
        if (null == action || actionInt > 12 || actionInt < 3 || actionInt != ac) {
            return null;
        }
        String ip = getPara("ip");
        if (null == ip || !CheckUtil.isIP(ip)) {
            return null;
        }
        ScreenRequest request = new ScreenRequest();
        request.setaction(action);
        request.setIp(ip);

        if (actionInt == 3) {
            int turnOnOrOff = Integer.parseInt(getPara("turnOnOrOff"));
            request.setTurnOnOrOff(turnOnOrOff);
            return request;
        }
        if (actionInt == 4) {
            return request;
        }
        if (actionInt == 5) {
            return request;
        }

        if (actionInt == 6) {
            return request;
        }

        if (actionInt == 7) {
            byte brightness = Byte.valueOf(getPara("brightness"));
            request.setbrightness(brightness);
            return request;
        }

        if (actionInt == 8) {
            return request;
        }
        if (actionInt == 9) {
            return request;
        }

        if (actionInt == 10) {
            return request;
        }

        if (actionInt == 11) {
            return request;
        }

        return request;
    }

    public static List<ScreenRequest> creatRequestByJson(String json) {
        JsonElement parse;
        try {
            parse = new JsonParser().parse(json);
        } catch (JsonSyntaxException e) {
            return null;
        }
        List<ScreenRequest> screenRequestList = new ArrayList<>();
        String action = parse.getAsJsonObject().get("action").getAsString();
        int actionInt;
        try {
            actionInt = Integer.parseInt(action);
        } catch (NumberFormatException e) {
            return null;
        }

        if (null == action || "".equals(action) || actionInt != 12) {
            return null;
        }
        String ip = parse.getAsJsonObject().get("ip").getAsString();
        if (null == ip || !CheckUtil.isIP(ip)) {
            return null;
        }
        JsonArray areas = parse.getAsJsonObject().get("areas").getAsJsonArray();

        for (int i = 0; i < areas.size(); i++) {
            ScreenRequest request = new ScreenRequest();
            int x = areas.get(i).getAsJsonObject().get("x").getAsInt();
            int y = areas.get(i).getAsJsonObject().get("y").getAsInt();
            int displayType = areas.get(i).getAsJsonObject().get("displayType").getAsInt();
            int width = areas.get(i).getAsJsonObject().get("width").getAsInt();
            int height = areas.get(i).getAsJsonObject().get("height").getAsInt();
            int ruleId = areas.get(i).getAsJsonObject().get("ruleId").getAsInt();

            request.setaction(action);
            request.setIp(ip);
            request.setX(x);
            request.setY(y);
            request.setWidth(width);
            request.setHeight(height);
            request.setDisplayType(displayType);
            request.setRuleId(ruleId);
            screenRequestList.add(request);
        }
        return screenRequestList;
    }*/
}
