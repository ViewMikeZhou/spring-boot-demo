package com.hd.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.enterprise.inject.Model;
import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/testSpring")
public class TestSpringController {

    class User {
        private String userName;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

    @RequestMapping(value = "/testSpring", method = {RequestMethod.POST, RequestMethod.GET})

    public @ResponseBody User test(String action) {
        User user = new User();
        user.setUserName(action);
        return user;
    }


    @RequestMapping(value = "/testSpring1", method = {RequestMethod.POST, RequestMethod.GET})
    public @ResponseBody List<String> test1(String action) {

        List<String> list = new ArrayList<>(1);
        list.add(action);
        return list;
    }


}
