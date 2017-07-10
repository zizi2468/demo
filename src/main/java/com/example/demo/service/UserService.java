package com.example.demo.service;

import com.example.demo.dao.LoginTicketDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.LoginTicket;
import com.example.demo.model.User;
import groovyjarjarantlr.MakeGrammar;
import groovyjarjarantlr.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static groovyjarjarantlr.StringUtils.*;

/**
 * Created by think on 2017/6/30.
 */
@Service
public class UserService {
    @Autowired
    private UserDao userdao;
    @Autowired
    private LoginTicketDao loginTicketDao;

    public Map<String,Object> register(String username, String password){
        Map<String,Object> map=new HashMap<String,Object>();
        if(username.isEmpty()){
            map.put("msgusername","用户名不能为空");
            return map;
        }
        if(password.isEmpty()){
            map.put("msgpassword","密码不能为空");
            return map;
        }
        User user= userdao.selectByName(username);
        if(user != null){
            map.put("msgusername","用户名已经被注册");
            return map;
        }

        user=new User();
        user.setName(username);
        //可以扩展密码强度，密码加密没做，在16'
        user.setPassword(password);
        userdao.addUser(user);

        return map;
    }

    public Map<String,Object> login(String username, String password){
        Map<String,Object> map=new HashMap<String,Object>();
        if(username.isEmpty()){
            map.put("msgusername","用户名不能为空");
            return map;
        }
        if(password.isEmpty()){
            map.put("msgpassword","密码不能为空");
            return map;
        }
        User user= userdao.selectByName(username);
        if(user == null){
            map.put("msgusername","用户不存在");
            return map;
        }

        //密码加密的话这里也要改，现在没做！
        if(!password.equals(user.getPassword())){
            map.put("msgusername","密码错误");
            return map;
        }

        //此时可以登录，下面通过ticket记住登录状态。
        String ticket=addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    private String addLoginTicket(int userId){
        //在后台为这个userid添加一个ticket，登出时删除这个ticket，或者等待ticket超时。
        //在上层控制登录的函数中只负责给出user，控制器调用service中的登录函数时，低层使用userid获得ticket。
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date=new Date();
        date.setTime(date.getTime()+1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        //ticket里面是一个随机数
        loginTicketDao.addTicket(ticket);
        return ticket.getTicket();
    }

    public void logout(String ticket){
        //登出时在底层删除ticket，控制层实现跳转到登出页面。
        loginTicketDao.updateStatus(ticket,1);
    }


    public void adduser(User user){
        userdao.addUser(user);
    }

    public User getuser(int id){
        return userdao.selectById(id);
    }

}

