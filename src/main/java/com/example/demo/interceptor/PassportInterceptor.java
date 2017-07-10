package com.example.demo.interceptor;

import com.example.demo.dao.LoginTicketDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.HostHolder;
import com.example.demo.model.LoginTicket;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.apache.tomcat.util.buf.UDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by think on 2017/7/5.
 */
@Component
public class PassportInterceptor implements HandlerInterceptor{
    //这是一个专门存在的用来拦截的接口，是个面向切面的接口
    //拦截器这种东西只要写了就是一直会生效的，不用特地在哪里调用的吗？
    @Autowired
    private LoginTicketDao loginTicketDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             Object o) throws Exception {
        //先查找cookie，用户数据是存在cookie里的，可以查看是否已经有ticket
        //对httpServletRequest遍历，找找有没有自己添加的ticket就行
        String ticket=null;
        if(httpServletRequest.getCookies() != null){
            for(Cookie cookie : httpServletRequest.getCookies()){
                if(cookie.getName().equals("ticket")){
                    ticket=cookie.getValue();
                    break;
                }
            }
        }
        if(ticket!=null){//查一下票是否有效，以及有效期
            LoginTicket loginTicket = loginTicketDao.selectByTicket(ticket);
            if(loginTicket==null || loginTicket.getExpired().before(new Date())
                    || loginTicket.getStatus()!=0){
                return true;
            }
            //现在知道ticket是有效的，把这是谁记下来，之后在controller中用
            User user=userDao.selectById(loginTicket.getUserId());
            //重新写了一个类，注入进来，用来保存这个用户
            hostHolder.setUsers(user);//保存下来，至少在这一条里面的前中后
            //代码里就都能用了，吧。
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {
        if(modelAndView != null && hostHolder.getUser()!=null){
            //modelAndView这是渲染相关的，下面的函数是后端代码和前端交互的一个地方
            //68'
            modelAndView.addObject("user",hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
        hostHolder.clear();//用完了，清理掉
    }
}
