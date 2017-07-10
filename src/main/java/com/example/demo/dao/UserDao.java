package com.example.demo.dao;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by think on 2017/6/29.
 */
@Mapper//代表和数据库是一一匹配的，不懂
public interface UserDao {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = " name, password ";
    String SELECT_FIELDS = " id, name, password";
    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,
            ") values(#{name},#{password})"})
    //#中的变量是我们自己用的变量，而直接写的那种变量是sql中用的，而且自己的可以深入到类里面用
    int addUser(User user);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    User selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where name=#{name}"})
    User selectByName(String name);

    @Update({"update ", TABLE_NAME, " set password = #{password} where id=#{id}"})
    void updatePassword(User user);

    @Delete({"delete from ", TABLE_NAME, " where id = #{id}"})
    void deleteById(int id);
}
