package net.lz1998.pbbot.boot

import net.lz1998.pbbot.handler.WebSocketHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@ComponentScan(
        basePackages = ["net.lz1998.pbbot"]
)
@Configuration
@EnableWebSocket
@EnableConfigurationProperties(BotProperties::class, EventProperties::class)
@Import(BotBean::class)
class PbBotAutoConfiguration : WebSocketConfigurer {
    @Autowired
    lateinit var cqProperties: BotProperties

    @Autowired
    lateinit var webSocketHandler: WebSocketHandler

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(webSocketHandler, cqProperties.url).setAllowedOrigins("*");
    }
}