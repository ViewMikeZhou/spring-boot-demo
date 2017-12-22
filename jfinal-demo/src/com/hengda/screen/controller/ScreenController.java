package com.hengda.screen.controller;

import com.google.gson.*;

import com.hengda.screen.EventType;
import com.hengda.screen.help.CheckUtil;

import com.hengda.screen.help.ParseHelp;
import com.hengda.screen.help.ProReadHelp;
import com.hengda.screen.inducation.ScreenHelper;
import com.hengda.screen.model.ScreenRequest;


import com.hengda.screen.model.response.ScreenResponse;

import com.jfinal.core.Controller;

import com.jfinal.kit.HttpKit;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;


public class ScreenController extends Controller {
    private static ScreenHelper control;


    public ScreenController() {
       /* List<User> users = User.dao.find( Db.getSql("user.findUser"));
        System.out.println(users.toString());
        System.out.println("screen构造");*/
    }

    static {
        try {
            ProReadHelp inStance = ProReadHelp.getInStance(EventType.PRO_NAME);
            String properties = inStance.getProperties(EventType.CONTROL_KET);
            control = (ScreenHelper) Class.forName(properties).newInstance();
            control.init();
        } catch (Exception e) {
            System.out.println("初始化出错" + e.getMessage());
        }
        System.out.println("初始化成功");
    }

    public void screenTurnOnOff() {
        renderJson(send(3));
    }


    public void screenIsBroken() {
        // if (getRequest().getMethod().equals("GET")) {
        renderJson(send(4));
        //  } else {
        //      renderText("");
        //   }

    }

    public void screenSettingBrighness() {
        renderJson(send(7));
    }

    public void getScreenBrightness() {
        renderJson(send(8));
    }

    public void getScreenStatus() {
        renderJson(send(9));
    }

    public void sycnTime() {
        renderJson(send(10));
    }

    public void isOpen() {
        renderJson(send(11));
    }

    public void sendScreenProgram() {
        String json = HttpKit.readData(getRequest());
        System.out.println(json);
        List<ScreenRequest> screenRequestList = creatRequestByJson(json);
        ScreenResponse response = control.send(screenRequestList);
        renderJson(response.toJson());
    }

    public void sendTextProgram() {
        String json = HttpKit.readData(getRequest());
        System.out.println(json);
        ScreenRequest request;
        try {
            request = ParseHelp.getInstance().getGson().fromJson(json, ScreenRequest.class);
            ScreenResponse send = control.send(request);
            renderJson(send.toJson());
        } catch (Exception e) {
            renderJson(new ScreenResponse(1, "JSON格式有误").toJson());
        }

    }


    private List<ScreenRequest> creatRequestByJson(String json) {
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
    }

    private String send(int action) {
        ScreenRequest screenRequest = creatRequestByForm(action);
        return control.send(screenRequest).toJson();
    }

    private ScreenRequest creatRequestByForm(int ac) {
        HttpServletRequest req = getRequest();
        String method = req.getMethod();
        if ("POST".equals(method)) {
            String json = HttpKit.readData(req);
            ScreenRequest request1 = null;
            try {
                request1 = ParseHelp.getInstance().getGson().fromJson(json, ScreenRequest.class);
            } catch (JsonSyntaxException e) {
                return null;
            }

            String action = request1.getaction();
            int actionInt;
            try {
                actionInt = Integer.parseInt(action);
            } catch (NumberFormatException e) {
                return null;
            }
            if (null == action || actionInt > 12 || actionInt < 3 || actionInt != ac) {
                return null;
            }

            return request1;
        }


        String action = getPara("action");

        System.out.println(action);
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
        return request;
    }

}
