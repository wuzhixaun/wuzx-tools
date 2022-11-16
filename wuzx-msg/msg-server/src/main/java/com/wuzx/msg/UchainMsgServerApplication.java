package com.yh.msg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author wuzhixuan
 * @version 1.0.0
 * @ClassName UchainMsgBizApplication.java
 * @Description 消息服务启动类
 * @createTime 2022年05月12日 16:08:00
 */
@SpringBootApplication(scanBasePackages = {"com.yh.msg"})
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.wuzx.msg.**.mapper"})
//@EnableFeignClients
public class UchainMsgServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UchainMsgServerApplication.class, args);
    }
}
