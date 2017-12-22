package com.zhou.springbootdemo;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.zhou.springbootdemo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 *  @RestController注解表示返回的内容都是HTTP Content不会被模版引擎处理的
    它默认为该类中的所有的方法都添加了@ResponseBody
 */
@Controller
public class HelloController {
    Logger logger = LoggerFactory.getLogger(HelloController.class);
    @RequestMapping(value = "/hello", method = RequestMethod.GET )
    public @ResponseBody  String hello() {
        logger.info("daying ");
        return "daying";
    }

    @RequestMapping(value = "/error4")
    public String getHtml(ModelMap map){

        map.addAttribute("host","showme");
        return "index";
    }

    @RequestMapping(value = "/test")
    public @ResponseBody String getHtml(){
        System.out.println("fff");
        return "index";
    }
}
