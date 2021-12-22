package com.pomeloisland;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pomeloisland.mapper.UserMapper;
import com.pomeloisland.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class WrapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        // 查询name不为空的用户，并且邮箱不为空的用户，年龄大于等于12
        // map对比一下
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper
                .isNotNull("name")
                .isNotNull("email")
                .ge("age",12);
        userMapper.selectList(wrapper).forEach(System.out::println);
    }

    @Test
    void test2(){
        //查询名字柚屿
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name","柚屿");
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }

    @Test
    void test3(){
        // 查询年龄在20-30之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age",20,30);// 区间
        Long count = userMapper.selectCount(wrapper);// 查询结果数
        System.out.println(count);
    }

    //模糊查询
    @Test
    void test4(){
        // 查询年龄在20-30之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        //左和右  1% 右  %1  左   %1%   中间
        wrapper.notLike("name","1")
                .likeRight("email","t%");
        List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
        maps.forEach(System.out::println);
    }

    @Test
    void test5(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // id 在子查询中查询出来
        wrapper.inSql("id","select id from user where id < 3");
        List<Object> objects = userMapper.selectObjs(wrapper);
        objects.forEach(System.out::println);
    }

    @Test
    void test6(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 通过id进行排序
        wrapper.orderByDesc("id");

        List<User> list = userMapper.selectList(wrapper);

        list.forEach(System.out::println);
    }
}
