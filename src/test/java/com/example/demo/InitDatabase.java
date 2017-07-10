package com.example.demo;
import com.example.demo.dao.LoginTicketDao;
import com.example.demo.dao.UserDao;
import com.example.demo.model.LoginTicket;
import com.example.demo.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

/**
 * Created by think on 2017/6/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@Sql("/init-schema.sql")
    public class InitDatabase {
    @Autowired
    UserDao userDao;
    @Autowired
    LoginTicketDao loginTicketDao;

    @Test
    public void initData() {
        /*for (int i = 0; i < 11; ++i) {
            User user = new User();
            user.setName(String.format("USER%d", i+11));
            user.setPassword("newpassword");
            userDao.addUser(user);
            //userDao.updatePassword(user);

            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*5*i);

            LoginTicket ticket=new LoginTicket();
            ticket.setExpired(date);
            ticket.setStatus(0);
            ticket.setTicket(String.format("TICKET%d",i+1));
            ticket.setUserId(i);
            loginTicketDao.addTicket(ticket);

            //loginTicketDao.updateStatus(ticket.getTicket(),2);
        }

        Assert.assertEquals("newpassword", userDao.selectById(1).getPassword());
        userDao.deleteById(1);
        Assert.assertNull(userDao.selectById(1));

        //Assert.assertEquals(0,loginTicketDao.selectByTicket("TICKET1").getUserId());
        //Assert.assertEquals(2,loginTicketDao.selectByTicket("TICKET1").getStatus());
*/
    }
}
