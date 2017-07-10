package com.example.demo.model;

import org.springframework.stereotype.Component;

/**
 * Created by think on 2017/7/5.
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users=new ThreadLocal<User>();
    //一个线程本地变量，每个线程set进来，get的之后只能获得自己的线程
    public User getUser(){
        return users.get();
    }
    public void setUsers(User user){
        users.set(user);
    }
    public void clear(){
        users.remove();
    }

}
