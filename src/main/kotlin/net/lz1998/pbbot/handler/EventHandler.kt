package net.lz1998.pbbot.handler

import com.google.protobuf.MessageLite
import net.lz1998.pbbot.alias.*
import net.lz1998.pbbot.bot.BotPlugin
import net.lz1998.pbbot.boot.BotProperties
import net.lz1998.pbbot.bot.Bot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext

open class EventHandler {

    @Autowired
    open lateinit var applicationContext: ApplicationContext

    @Autowired
    open lateinit var botProperties: BotProperties


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

    open fun handleFriendAddNoticeEvent(bot: Bot, event: FriendAddNoticeEvent) {
        botProperties.pluginList.forEach { pluginClass ->
            if (getPlugin(pluginClass)?.onFriendAddNotice(bot, event) == BotPlugin.MESSAGE_BLOCK) {
                return
            }
        }
    }

    open fun handleGroupRecallNoticeEvent(bot: Bot, event: GroupRecallNoticeEvent) {
        botProperties.pluginList.forEach { pluginClass ->
            if (getPlugin(pluginClass)?.onGroupRecallNotice(bot, event) == BotPlugin.MESSAGE_BLOCK) {
                return
            }
        }
    }

    open fun handleFriendRecallNoticeEvent(bot: Bot, event: FriendRecallNoticeEvent) {
        botProperties.pluginList.forEach { pluginClass ->
            if (getPlugin(pluginClass)?.onFriendRecallNotice(bot, event) == BotPlugin.MESSAGE_BLOCK) {
                return
            }
        }
    }

    open fun handleFriendRequestEvent(bot: Bot, event: FriendRequestEvent) {
        botProperties.pluginList.forEach { pluginClass ->
            if (getPlugin(pluginClass)?.onFriendRequest(bot, event) == BotPlugin.MESSAGE_BLOCK) {
                return
            }
        }
    }

    open fun handleGroupRequestEvent(bot: Bot, event: GroupRequestEvent) {
        botProperties.pluginList.forEach { pluginClass ->
            if (getPlugin(pluginClass)?.onGroupRequest(bot, event) == BotPlugin.MESSAGE_BLOCK) {
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