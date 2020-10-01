package net.lz1998.pbbot.boot

import net.lz1998.pbbot.bot.ApiSender
import net.lz1998.pbbot.bot.BotFactory
import net.lz1998.pbbot.handler.EventHandler
import net.lz1998.pbbot.handler.FrameHandler
import net.lz1998.pbbot.handler.WebSocketHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean


@Component
class BotBean {
    @Autowired
    lateinit var miraiProperties: BotProperties

    @Autowired
    lateinit var eventProperties: EventProperties

    @Autowired
    lateinit var botFactory: BotFactory

    @Autowired
    lateinit var frameHandler: FrameHandler

    @Bean
    @ConditionalOnMissingBean // 如果用户自定义eventHandler则不创建
    fun createEventHandler(): EventHandler {
        return EventHandler()
    }

    @Bean
    @ConditionalOnMissingBean // 如果用户自定义apiSender则不创建
    fun createApiSender(): ApiSender {
        return ApiSender()
    }

    @Bean
    @ConditionalOnMissingBean // 如果用户自定义webSocketHandler则不创建
    fun createWebSocketHandler(): WebSocketHandler {
        return WebSocketHandler(eventProperties, botFactory, frameHandler)
    }

    @Bean
    @ConditionalOnMissingBean
    fun createWebSocketContainer(): ServletServerContainerFactoryBean? {
        val container = ServletServerContainerFactoryBean()
        // ws 传输数据的时候，数据过大有时候会接收不到，所以在此处设置bufferSize
        container.maxTextMessageBufferSize = miraiProperties.maxTextMessageBufferSize
        container.maxBinaryMessageBufferSize = miraiProperties.maxBinaryMessageBufferSize
        container.maxSessionIdleTimeout = miraiProperties.maxSessionIdleTimeout
        return container
    }
}