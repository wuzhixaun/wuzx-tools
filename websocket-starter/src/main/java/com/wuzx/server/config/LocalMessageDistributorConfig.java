package com.wuzx.server.config;

import com.wuzx.msg.server.constant.MessageDistributorTypeConstants;
import com.wuzx.msg.server.distribute.LocalMessageDistributor;
import com.wuzx.msg.server.distribute.MessageDistributor;
import com.wuzx.msg.server.properties.WebSocketProperties;
import com.wuzx.msg.server.session.WebSocketSessionStore;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * 本地消息分发器配置类
 *
 * @author wuzhixuan
 * @version 1.0
 * @date 2022/10/09 16:00
 **/
@ConditionalOnProperty(prefix = WebSocketProperties.PREFIX, name = "message-distributor", havingValue = MessageDistributorTypeConstants.LOCAL, matchIfMissing = true)
@RequiredArgsConstructor
public class LocalMessageDistributorConfig {

    private final WebSocketSessionStore webSocketSessionStore;

    /**
     * 本地基于内存的消息分发，不支持集群
     * @return LocalMessageDistributor
     */
    @Bean
    @ConditionalOnMissingBean(MessageDistributor.class)
    public LocalMessageDistributor messageDistributor() {
        return new LocalMessageDistributor(webSocketSessionStore);
    }
}
