package com.hengda.screen.help;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class ProReadHelp {
    private Properties pro;
    private static ProReadHelp proHelp;

    private ProReadHelp( String fileName) throws IOException {
        pro = new Properties();
        pro.load(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fileName), "UTF-8"));
    }

    public static ProReadHelp getInStance(String fileName) throws IOException {
        if (proHelp == null) {
            synchronized (ProReadHelp.class) {
                proHelp = new ProReadHelp( fileName);
            }
        }
        return proHelp;
    }

    public String getProperties(String key) throws Exception {
        String property = pro.getProperty(key);
        if (null == property) {
            throw new Exception("无数据");
        }
        return property;
    }

}
