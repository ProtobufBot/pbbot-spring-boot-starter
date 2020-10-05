package net.lz1998.pbbot.boot

import net.lz1998.pbbot.bot.BotPlugin
import org.springframework.boot.context.properties.ConfigurationProperties
import java.util.*

@ConfigurationProperties(prefix = "spring.bot")
data class BotProperties(
    var url: String = "/ws/*/",
    var maxTextMessageBufferSize: Int = 512000,
    var maxBinaryMessageBufferSize: Int = 512000,
    var maxSessionIdleTimeout: Long = 15 * 60000L,
    var apiTimeout: Long = 60000L,
    var pluginList: List<Class<out BotPlugin>> = ArrayList()
)