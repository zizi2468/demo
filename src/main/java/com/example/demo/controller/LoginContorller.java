package com.example.demo.controller;
import com.example.demo.service.UserService;
import com.example.demo.util.DemoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by think on 2017/7/4.
 */
@Controller
public class LoginContorller {
//这边没什么问题，主要看怎么跳转到这边的三个对应网址，回去写主页
    private static final Logger logger = LoggerFactory.getLogger(LoginContorller.class);
    @Autowired
    UserService userService;

    @RequestMapping(path={"/reg/"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody//很多都是返回一个html页面，不是的话就用这个包好
    public String reg(Model model, @RequestParam("username") String username,
                       @RequestParam("password") String password,
                       @RequestParam(value="remember",defaultValue = "0") int remember,
                       HttpServletResponse response){
        try{
            Map<String,Object> map=userService.register(username, password);
            if(map.isEmpty()){
                return DemoUtil.getJSONString(0,"注册成功");
                //修改一下，如果注册成功了就跳转回主页好了，嗯…延迟几秒跳转主页，就是那种显示一个成功页面，五秒后跳转或者手动跳转。
            }
            else{
                return DemoUtil.getJSONString(1,map);
            }
        }catch (Exception e){
            logger.error("注册异常"+e.getMessage());
            return DemoUtil.getJSONString(1,"注册异常");
        }
    }

    @RequestMapping(path={"/login/"},method = {RequestMethod.GET,RequestMethod.POST})
    //@ResponseBody
    public String login(Model model, @RequestParam("username") String username,
                       @RequestParam("password") String password,
                       @RequestParam(value="remember",defaultValue = "0") int remember,
                       HttpServletResponse response){
        try{//如果调用登录服务成功，则会获得一个ticket，将其保存入cookie，通过拦截器查询cookie中的内容
            //与sql中的数据对比才能维持登录状态
            Map<String,Object> map=userService.login(username, password);
            if(map.containsKey("ticket")){
                Cookie cookie=new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");//全站有效
                if(remember>0){
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);//这样后端才能用这个cookie，在其他的网页里也能查到这个cookie
                //而且应该能维持页面不关闭就不解除
                //return DemoUtil.getJSONString(0,"登录成功");
                return "loginsuccess";
            }
            else{
                model.addAttribute(map);
                //return DemoUtil.getJSONString(1,map);
                return "loginerror";
            }
        }catch (Exception e){
            logger.error("登录异常"+e.getMessage());
            return DemoUtil.getJSONString(1,"登录异常");
        }
    }

    @RequestMapping(path={"/logout/"},method = {RequestMethod.GET,RequestMethod.POST})
    public String logout(@CookieValue("ticket")String ticket){
        //这边也肯定是某个地方给出这么一个信号，然后跳到logout页面了，再跳出去，登出本身没问题，所以怎么跳过来的……
        userService.logout(ticket);
        return "redirect:/";
    }
}
