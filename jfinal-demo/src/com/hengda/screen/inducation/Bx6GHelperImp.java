package com.hengda.screen.inducation;

import com.hengda.screen.EventType;
import com.hengda.screen.help.ProReadHelp;
import com.hengda.screen.model.ScreenRequest;
import com.hengda.screen.model.response.*;
import onbon.bx06.*;
import onbon.bx06.area.DynamicBxArea;
import onbon.bx06.area.page.BxPage;
import onbon.bx06.area.page.ImageFileBxPage;
import onbon.bx06.area.page.TextBxPage;
import onbon.bx06.cmd.dyn.DynamicBxAreaRule;
import onbon.bx06.file.ProgramBxFile;
import onbon.bx06.message.global.ACK;
import onbon.bx06.message.led.ReturnControllerStatus;
import onbon.bx06.utils.DisplayStyleFactory;
import onbon.bx06.utils.TextBinary;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * BX6g控制实体类
 */
public class Bx6GHelperImp implements ScreenHelper {
    private DisplayStyleFactory.DisplayStyle[] styles;             //进场出场特技
    private Bx6GScreenClient screen;
    private List<BxPage> dyPages;         //动态资源
    private String port;
    private Map<String, Bx6GScreenClient> screens;

    @Override
    public void init() throws Exception {
        try {
            Bx6GEnv.initial("log.properties", 30000);
        } catch (Exception e) {
            System.out.println(e.toString());
            throw new Exception(e.getMessage());
        }
        //String ip = ProReadHelp.getInStance(EventType.PRO_NAME).getProperties(EventType.IP);
        port = ProReadHelp.getInStance(EventType.PRO_NAME).getProperties(EventType.PORT);
        screens = new HashMap<>();
        styles = DisplayStyleFactory.getStyles().toArray(new DisplayStyleFactory.DisplayStyle[0]);

        initDyResouce();

    }


    /**
     * 初始化动态显示规则
     */
    private DynamicBxAreaRule creatDyRule(int id) {

        DynamicBxAreaRule rule = new DynamicBxAreaRule();
        rule.setId(id);
        rule.setRunMode((byte) 0);          // 循环播放;
        rule.setImmediatePlay((byte) 0);    // 与异步节目一起播放
        return rule;

    }


    /**
     * 初始化动态资源
     */
    private void initDyResouce() {
        dyPages = new ArrayList<>();
        ImageFileBxPage imageFileBxPage1 = new ImageFileBxPage(getClass().getClassLoader().getResource("green.png").getPath());
        ImageFileBxPage imageFileBxPage2 = new ImageFileBxPage(getClass().getClassLoader().getResource("yellow.png").getPath());
        ImageFileBxPage imageFileBxPage3 = new ImageFileBxPage(getClass().getClassLoader().getResource("red.png").getPath());
        TextBxPage textBxPage = new TextBxPage();

        dyPages.add(imageFileBxPage1);
        dyPages.add(imageFileBxPage2);
        dyPages.add(imageFileBxPage3);
        dyPages.add(textBxPage);
    }


    public ScreenResponse send(ScreenRequest request) {

        if (request == null) {
            return new ScreenResponse(1, "action或ip无法识别");
        }
        verifyScreen(request);
        String eventType = null;
        try {
            eventType = ProReadHelp.getInStance(EventType.PRO_NAME).getProperties(request.getaction());
        } catch (Exception e) {
            return new ScreenResponse(1, "action不存在");
        }

        Bx6GScreen.Result<ACK> ackResult = null;
        switch (eventType) {
            case EventType.CANCLE:
                screen.disconnect();
                break;
            case EventType.CONNECT:
                if (!screen.isConnected()) {
                    try {
                        //   String ip = ProReadHelp.getInStance(EventType.PRO_NAME).getProperties(EventType.IP);
                        String port = ProReadHelp.getInStance(EventType.PRO_NAME).getProperties(EventType.PORT);
                        screen.connect(request.getIp(), Integer.parseInt(port));
                    } catch (Exception e) {
                        return new ScreenResponse(1, "连接失败");
                    }
                }
                break;
            case EventType.TURN_ON_OFF:
                int turnOnOrOff = request.getTurnOnOrOff();
                if (turnOnOrOff == 0) {
                    ackResult = screen.turnOff();
                } else {
                    ackResult = screen.turnOn();
                }
                break;
            case EventType.IS_BROKEN:
                if (screen.isConnected()) {
                    return new ScreenBrokenResponse(0, "NORMAL", 1);
                } else {
                    return new ScreenBrokenResponse(0, "BROKEN", 0);
                }
            case EventType.CLEAR_PROGRAM:
                ackResult = screen.deletePrograms();
                break;
            case EventType.CLEAR_DY:
                ackResult = screen.deleteAllDynamic();
                break;
            case EventType.GET_SCEEN_BRIGH:
                Bx6GScreen.Result<ReturnControllerStatus> statusResult = screen.checkControllerStatus();
                byte brightness1 = statusResult.reply.getBrightness();
                return new ScreenBrightnessReponse(0, "SUCCESS", brightness1);
            case EventType.SET_SCEEN_BRIGH:
                ackResult = screen.manualBrightness(request.getbrightness());
                break;
            case EventType.GET_TIME:
                return new ScreenTimeResponse(0, "SUCCESS", getContorTime());
            case EventType.SYNC_TIME:
                ackResult = screen.syncTime();
                break;
            case EventType.IS_SCREEN_ON:
                return new ScreenStatusResponse(0, "SUCCESS", screen.checkControllerStatus().reply.getScreenOnOff());
            case EventType.SETTING_PROGRAM:
              /*  if (request.getDisplayType() > 3 || request.getDisplayType() == 0) {
                    return new ScreenResponse(1, "传入的displayType有误");
                }
                    ackResult = settingProgram(request);*/
                break;
            case EventType.SETTING_TEXT_PROGRAM:
                if (!(request.getHeight() > 0) || !(request.getWidth() > 0)) {
                    return new ScreenResponse(1000, "参数异常,宽高必须大于0");
                }
                ackResult = settingTextProgram(request);
                break;
            case EventType.SETTING_STOPPOING_PROGRAM:
                if (!(request.getHeight() > 0) || !(request.getWidth() > 0)) {
                    return new ScreenResponse(1000, "参数异常,宽高必须大于0");
                }
                ackResult = settingStoppingProgram(request);
                break;
        }
        if (null != ackResult && ackResult.isOK()) {
            return new ScreenResponse(0, "SUCCESS");
        }
        return new ScreenResponse(1, "命令发送出错");
    }

    /**
     * 光带设置
     *
     * @param requestList
     * @return
     */
    @Override
    public ScreenResponse send(List<ScreenRequest> requestList) {
        if (null == requestList) {
            return new ScreenResponse(1, "JSON格式有误");
        }
        if (requestList.size() == 0) {
            return new ScreenResponse(1, "您还没有设置方案");
        }


        verifyScreen(requestList.get(0));
        Bx6GScreenProfile profile = screen.getProfile();
        for (ScreenRequest request : requestList) {
            if (!(request.getHeight() > 0) || !(request.getWidth() > 0)) {
                return new ScreenResponse(1000, "参数异常,宽高必须大于0");
            }
            DynamicBxAreaRule rule = creatDyRule(request.getRuleId());
            DynamicBxArea dArea1 = new DynamicBxArea(request.getX(), request.getY(), request.getWidth(), request.getHeight(), profile);
            ImageFileBxPage imageFileBxPage = (ImageFileBxPage) dyPages.get(request.getDisplayType() - 1);
            dArea1.addPage(imageFileBxPage);
            screen.writeDynamic(rule, dArea1);
        }
        return new ScreenResponse(0, "SUCCESS");
    }


    /**
     * 验证屏幕
     *
     * @param request
     */
    private void verifyScreen(ScreenRequest request) {
        screen = screens.get(request.getIp());
        if (screen == null) {
            screen = new Bx6GScreenClient("MyScreen");
            screens.put(request.getIp(), screen);
            screen.connect(request.getIp(), Integer.parseInt(port));
            if ("12".equals(request.getaction()) || "13".equals(request.getaction())) ;
            initDyProgram();
        }
        if (!screen.isConnected()) {
            screen.connect(request.getIp(), Integer.parseInt(port));
            System.out.println("建立连接");
        }

    }

    /**
     * 第一次连接初始化一个界面装载动态区清楚以前所有的节目
     */
    private void initDyProgram() {
        screen.deletePrograms();
        screen.deleteAllDynamic();
        Bx6GScreenProfile profile = screen.getProfile();
        ProgramBxFile program0 = new ProgramBxFile("P111", profile);
        DynamicBxArea area = new DynamicBxArea(0, 0, 0, 0, profile);
        program0.addArea(area);
        try {
            screen.writeProgram(program0);
        } catch (Bx6GException e) {
            System.out.println("初始化失败");
        }
    }


    /**
     * 创建文字显示区
     *
     * @param request
     * @return
     */
    private Bx6GScreen.Result<ACK> settingTextProgram(ScreenRequest request) {

        return screen.writeDynamic(creatDyRule(request.getRuleId()), creatTextDy(request));
    }

    public Bx6GScreen.Result<ACK> settingStoppingProgram(ScreenRequest request) {
        return screen.writeDynamic(creatDyRule(request.getRuleId()), creatTextDy(request));
    }

    /**
     * 设置动态文本区域
     *
     * @param request
     * @return
     */
    public DynamicBxArea creatTextDy(ScreenRequest request) {
        DynamicBxArea textArea = new DynamicBxArea(request.getX(), request.getY(), request.getWidth(), request.getHeight(), screen.getProfile());
        TextBxPage bxPage = (TextBxPage) dyPages.get(3);
        Color color = null;
        if (request.getTextColor() == 1 || request.getTextColor() == 0) {
            color = Color.GREEN;
        } else if (request.getTextColor() == 2) {
            color = Color.YELLOW;
        } else if (request.getTextColor() == 3) {
            color = Color.RED;
        }
        bxPage.setForeground(color);

        if (request.getaction().equals("13")) {
            TextBinary.Alignment alignment = getTextAlign(request.getTextAlign());
            bxPage.setHorizontalAlignment(alignment);
            //    bxPage.setVerticalAlignment(alignment);
            //    bxPage.setHeadTailInterval(0);      //设置间隔 -2 会切换页面
            bxPage.setSpeed(2);
            bxPage.setStayTime(200);
            int displayStyle = request.getDisplayStyle();
            if (displayStyle == 2) {
                displayStyle = 3;
            } else {
                displayStyle = 1;
            }
            bxPage.setFont(new Font("宋体", Font.PLAIN, request.getTextSize()));
            bxPage.setDisplayStyle(styles[displayStyle]);
        } else if (request.getaction().equals("14")) {
            bxPage.setSpeed(2);
            bxPage.setStayTime(200);
            String test = null;
            try {
                test = ProReadHelp.getInStance(EventType.PRO_NAME).getProperties(EventType.TEST);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if ("0".equals(test)) {
                // 测试时不加粗
                bxPage.setFont(new Font("宋体", Font.PLAIN, request.getTextSize()));
            } else {
                bxPage.setFont(new Font("宋体", Font.BOLD, request.getTextSize()));
            }
            bxPage.setHorizontalAlignment(TextBinary.Alignment.CENTER);
            bxPage.setVerticalAlignment(TextBinary.Alignment.CENTER);
            bxPage.setDisplayStyle(styles[1]);
        }

        bxPage.setText(request.getText());
        textArea.addPage(bxPage);
        return textArea;
    }

    /**
     * 通过textAling获取文字居左或居右或居中
     *
     * @param textAlign
     * @return
     */
    private TextBinary.Alignment getTextAlign(int textAlign) {
        if (textAlign == 0) {
            return TextBinary.Alignment.NEAR;
        } else if (textAlign == 1) {
            return TextBinary.Alignment.CENTER;
        } else if (textAlign == 2) {
            return TextBinary.Alignment.FAR;
        }
        return null;
    }

    /**
     * 设置光带区
     *
     * @param request
     * @return
     */
    private Bx6GScreen.Result<ACK> settingProgram(ScreenRequest request) {
        DynamicBxArea area = new DynamicBxArea(request.getX(), request.getY(), request.getWidth(), request.getHeight(), screen.getProfile());
        ImageFileBxPage bxPage = (ImageFileBxPage) dyPages.get(request.getDisplayType() - 1);
        area.addPage(bxPage);
        return screen.writeDynamic(creatDyRule(request.getRuleId()), area);
    }

    /**
     * 获取控制器时间
     *
     * @return
     */
    private String getContorTime() {
        ReturnControllerStatus status = screen.checkControllerStatus().reply;
        return status.getRtcYear() + "-" + status.getRtcMonth() + "-" + status.getRtcDay() + " " + status.getRtcHour() + ":" + status.getRtcMinute() + ":" + status.getRtcSecond();
    }


}
