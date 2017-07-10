package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by think on 2017/6/27.
 */
//@Controller
public class DemoController {
    @RequestMapping(path={"/","/index"})
    @ResponseBody
    public  String index(){
        return "Hello world";
    }

    @RequestMapping(path={"/profile/{groupId}/{userId}"})
    @ResponseBody
    public  String profile(@PathVariable("groupId") String groupId,
                            @PathVariable("userId") int userId,
                            @RequestParam(value="type",defaultValue = "1") int type,
                            @RequestParam(value="key",defaultValue = "keys") String key){
        return String.format("{%s},{%d},{%d},{%s}",groupId,userId,type,key);
    }

    @RequestMapping(path={"/vm"})//在这里要一个页面，就直接返回一个html的页面
    public  String home(Model model){//model是一个后台数据的model。
        model.addAttribute("name","yonghuming");

        List<User> list=new ArrayList<User>();
        list.add(new User("mao"));
        model.addAttribute("user",list);//是把这里的数据送到html里！！从html里处理，显示！！
        return "home";
    }

    @RequestMapping(path={"/request"})
    @ResponseBody//这是一个标准的http请求，通用，可以获得各种属性，恩应该可以直接
    //获得了之后用于后面的编码，以及提交给html做出处理
    public  String request(HttpServletRequest request,
                           HttpServletResponse response,
                           HttpSession session){
        StringBuilder sb=new StringBuilder();
        Enumeration<String> headernames=request.getHeaderNames();
        while(headernames.hasMoreElements()){
            String name=headernames.nextElement();
            sb.append(name+":"+request.getHeader(name)+"<br>");
        }
        return sb.toString();
    }

    @RequestMapping("/redirect/{code}")//{}里的叫占位符
    public RedirectView redirect(@PathVariable("code")int code){
        //@那个标签可以把占位符里的东西绑定到后面紧跟着的那个变量里
        //这样就能读取一个网页路径里的东西了！！
        RedirectView red=new RedirectView("/",true);
        //重定位到url
        if(code==301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
            //设置一个状态，猜测red是重定位显示的网页的一些处理。
        }
        return red;
        //也可以什么都不写直接写一个return:"redirect:/";
    }
    //可以用session在不同的网页间传递
    @RequestMapping("/admin")
    @ResponseBody
    public  String admin(@RequestParam(value="key",required=false) String key){
        if("admin".equals(key)){
            return "Hello admin";
        }
        throw new IllegalArgumentException("key错误");
    }

    @RequestMapping("/setting/")
    @ResponseBody
    public  String setting(){
        return "Hello world";
    }
}
