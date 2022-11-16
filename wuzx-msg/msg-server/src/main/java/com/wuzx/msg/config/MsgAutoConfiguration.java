package com.wuzx.msg.config;

import com.wuzx.msg.handler.NotifyInfoDelegateHandler;
import com.wuzx.msg.listener.NotifyWebsocketEventListener;
import com.wuzx.msg.pojo.NotifyInfo;
import com.wuzx.msg.server.distribute.MessageDistributor;
import com.yh.mybatis.plus.config.MybatisPlusConfig;
import com.yh.swagger.SwaggerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/12 15:08
 **/
@RequiredArgsConstructor
@Configuration
@Import({MybatisPlusConfig.class, SwaggerConfig.class})
public class MsgAutoConfiguration {

    private final MessageDistributor messageDistributor;

    @Bean
    public NotifyWebsocketEventListener notifyWebsocketEventListener(
            NotifyInfoDelegateHandler<? super NotifyInfo> notifyInfoDelegateHandler) {
        return new NotifyWebsocketEventListener(messageDistributor, notifyInfoDelegateHandler);
    }
}
