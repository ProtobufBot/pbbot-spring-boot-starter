package net.lz1998.pbbot.boot

import net.lz1998.pbbot.bot.BotPlugin
import org.springframework.boot.context.properties.ConfigurationProperties
import java.util.*

@ConfigurationProperties(prefix = "spring.bot")
object BotProperties {
    var url = "/ws/*/"
    var maxTextMessageBufferSize = 512000
    var maxBinaryMessageBufferSize = 512000
    var maxSessionIdleTimeout = 15 * 60000L
    var pluginList: List<Class<out BotPlugin>> = ArrayList()
}