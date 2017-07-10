package com.example.demo.controller;

import com.example.demo.model.HostHolder;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2017/7/3.
 */
@Controller
public class HomeContorller {
    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path={"/"},method = {RequestMethod.GET,RequestMethod.POST})
    public String home(Model model){
        Boolean flag=false;
        User user=hostHolder.getUser();
        if(user!=null)
            flag=true;
        model.addAttribute("flag",flag);
        return "home";
    }

    @RequestMapping(path={"/reg"},method = {RequestMethod.GET,RequestMethod.POST})
    public String reghome(Model model) {
        return "reghome";
    }

    @RequestMapping(path={"/login"},method = {RequestMethod.GET,RequestMethod.POST})
    public String loginhome(Model model){
        return "loginhome";
    }
}
