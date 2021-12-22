package com.pomeloisland;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pomeloisland.mapper.UserMapper;
import com.pomeloisland.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class MyBatisPlusDemoApplicationTests {

    //继承了BaseMapper, 所有的方法都来自己父类
    //我们也可以编写自己的扩展方法
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        //参数是一个Wrapper , 条件构造器,这里我们先不用 --null
        //查询全部用户
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }
    @Test
    public void testInsert(){
        User user = new User();
        user.setName("pomeloisland");
        user.setAge(4);
        user.setEmail("1005297142@qq.com");
        user.setUpdateTime(new Date());
        int result = userMapper.insert(user);  //自动生成id
        System.out.println(result); //受影响的行数
        System.out.println(user); //id会自动回填
    }

    @Test
    public void testUpdate(){
        User user = new User();
        user.setName("柚屿");
        user.setId(1473216732393328642L);
        user.setAge(19);
        int i = userMapper.updateById(user);
        System.out.println(i);
    }

    //测试查询
    @Test
    public void testSelectById(){
        User user = userMapper.selectById(1L);
        System.out.println(user);
    }

    //测试批量查询
    @Test
    public void testSelectBatchId(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3));
        users.forEach(System.out::println);
    }

    //按条件查询之--使用Map操作
    @Test
    public void testSelectBatchIds(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","柚屿");
        map.put("age","19");

        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    //测试乐观锁成功
    @Test
    public void testOptimisticLocker(){
        //1.查询用户信息
        User user = userMapper.selectById(1L);
        //2.修改用户信息
        user.setName("主角1号");
        user.setEmail("1656357263@qq.com");
        //3.执行更新操作
        userMapper.updateById(user);
    }

    //测试乐观锁失败！ 多线程下
    @Test
    public void testOptimisticLocker2(){
        //线程 1
        User user = userMapper.selectById(1L);
        user.setName("主角1号");
        user.setEmail("1656357263@qq.com");

        // 模拟另一个线程执行了插队操作
        User user1 = userMapper.selectById(1L);
        user1.setName("配角1号");
        user.setEmail("1656357264@qq.com");
        userMapper.updateById(user1);
        //自旋锁来多次尝试提交！
        userMapper.updateById(user); // 如果没有乐观锁就会覆盖插队线程的值！
    }

    //测试分页查询
    @Test
    public void testPage(){
        // 参数1 当前页
        // 参数2 页面的大小
        Page<User> page = new Page<>(1,5);
        userMapper.selectPage(page,null);

        page.getRecords().forEach(System.out::println);
        System.out.println(page.getTotal());
    }

    //测试删除
    @Test
    public void testDeleteById(){
        userMapper.deleteById(1L);
    }

    // 通过id批量删除
    @Test
    public void testDeleteBatchId(){
        userMapper.deleteBatchIds(Arrays.asList(1473216732393328642L,1473216732393328643L));
    }

    // 通过map删除
    @Test
    public void testDeleteMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","pomeloisland");
        userMapper.deleteByMap(map);
    }

}
