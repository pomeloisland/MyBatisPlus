package com.pomeloisland;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

//代码自动生成器
public class Code {
    public static void main(String[] args) {
        //需要构建一个代码生成器对象
        String property = System.getProperty("user.dir");
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/mybatis-plus?useSSL=false&useUnicode=true&characterEncoding=utf-8","zhwroot","zhwroot")
                .globalConfig(builder -> {
                    builder.author("pomeloisland") //设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已经生成的文件
                            .outputDir(property+"src/main/pomeloisland"); //指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("mapper")// 设置父包名
                            .moduleName("MyBatisPlusDemo")// 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,"src/main/pomeloisland"));// 设置mapper生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user"); // 设置需要生成的表名
                            // .addTablePrefix("t_", "c_"); 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
