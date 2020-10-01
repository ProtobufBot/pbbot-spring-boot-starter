package net.lz1998.pbbot.handler

import com.google.protobuf.MessageLite
import net.lz1998.pbbot.bot.BotPlugin
import net.lz1998.pbbot.boot.BotProperties
import net.lz1998.pbbot.bot.Bot
import net.lz1998.pbbot.alias.GroupDecreaseNoticeEvent
import net.lz1998.pbbot.alias.GroupIncreaseNoticeEvent
import net.lz1998.pbbot.alias.GroupMessageEvent
import net.lz1998.pbbot.alias.PrivateMessageEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext

open class EventHandler {

    @Autowired
    lateinit var applicationContext: ApplicationContext

    @Autowired
    lateinit var botProperties: BotProperties


    open fun handlePrivateMessageEvent(bot: Bot, event: PrivateMessageEvent) {
        botProperties.pluginList.forEach { pluginClass ->
            if (getPlugin(pluginClass)?.onPrivateMessage(bot, event) == BotPlugin.MESSAGE_BLOCK) {
                return
            }
        }
    }

    open fun handleGroupMessageEvent(bot: Bot, event: GroupMessageEvent) {
        botProperties.pluginList.forEach { pluginClass ->
            if (getPlugin(pluginClass)?.onGroupMessage(bot, event) == BotPlugin.MESSAGE_BLOCK) {
                return
            }
        }
    }

    open fun handleGroupDecreaseNoticeEvent(bot: Bot, event: GroupDecreaseNoticeEvent) {
        botProperties.pluginList.forEach { pluginClass ->
            if (getPlugin(pluginClass)?.onGroupDecreaseNotice(bot, event) == BotPlugin.MESSAGE_BLOCK) {
                return
            }
        }
    }

    open fun handleGroupIncreaseNoticeEvent(bot: Bot, event: GroupIncreaseNoticeEvent) {
        botProperties.pluginList.forEach { pluginClass ->
            if (getPlugin(pluginClass)?.onGroupIncreaseNotice(bot, event) == BotPlugin.MESSAGE_BLOCK) {
                return
            }
        }
    }

    open fun handleUnknown(event: MessageLite?) {

    }

    fun <T> getPlugin(cls: Class<T>): T? = try {
        applicationContext.getBean(cls)
    } catch (e: Exception) {
        null
    }
}