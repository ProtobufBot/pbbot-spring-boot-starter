package net.lz1998.pbbot.bot

import net.lz1998.pbbot.alias.*


abstract class BotPlugin {
    /**
     * 收到私聊消息时调用此方法
     *
     * @param bot    机器人对象
     * @param event 事件内容
     * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
     */
    open fun onPrivateMessage(bot: Bot, event: PrivateMessageEvent): Int {
        return MESSAGE_IGNORE
    }

    /**
     * 收到群消息时调用此方法
     *
     * @param bot    机器人对象
     * @param event 事件内容
     * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
     */
    open fun onGroupMessage(bot: Bot, event: GroupMessageEvent): Int {
        return MESSAGE_IGNORE
    }

    /**
     * 群内有文件上传时调用此方法
     * 仅群文件上传表现为事件，好友发送文件在 酷Q 中没有独立的事件，而是直接表现为好友消息，请注意在编写业务逻辑时进行判断。
     *
     * @param bot    机器人对象
     * @param event 事件内容
     * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
     */
    open fun onGroupUploadNotice(bot: Bot, event: GroupUploadNoticeEvent): Int {
        return MESSAGE_IGNORE
    }

    /**
     * 群管理员变动时调用此方法
     *
     * @param bot    机器人对象
     * @param event 事件内容
     * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
     */
    open fun onGroupAdminNotice(bot: Bot, event: GroupAdminNoticeEvent): Int {
        return MESSAGE_IGNORE
    }

    /**
     * 群成员减少时调用此方法
     *
     * @param bot    机器人对象
     * @param event 事件内容
     * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
     */
    open fun onGroupDecreaseNotice(bot: Bot, event: GroupDecreaseNoticeEvent): Int {
        return MESSAGE_IGNORE
    }

    /**
     * 群成员增加时调用此方法
     *
     * @param bot    机器人对象
     * @param event 事件内容
     * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
     */
    open fun onGroupIncreaseNotice(bot: Bot, event: GroupIncreaseNoticeEvent): Int {
        return MESSAGE_IGNORE
    }

    /**
     * 群禁言时调用此方法
     *
     * @param bot    机器人对象
     * @param event 事件内容
     * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
     */
    open fun onGroupBanNotice(bot: Bot, event: GroupBanNoticeEvent): Int {
        return MESSAGE_IGNORE
    }

    /**
     * 好友添加时调用此方法
     *
     * @param bot    机器人对象
     * @param event 事件内容
     * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
     */
    open fun onFriendAddNotice(bot: Bot, event: FriendAddNoticeEvent): Int {
        return MESSAGE_IGNORE
    }

    /**
     * 加好友请求时调用此方法
     *
     * @param bot    机器人对象
     * @param event 事件内容
     * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
     */
    open fun onFriendRequest(bot: Bot, event: FriendRequestEvent): Int {
        return MESSAGE_IGNORE
    }

    /**
     * 加群请求/邀请时调用此方法
     *
     * @param bot    机器人对象
     * @param event 事件内容
     * @return 是否继续处理下一个插件, MESSAGE_BLOCK表示不继续，MESSAGE_IGNORE表示继续
     */
    open fun onGroupRequest(bot: Bot, event: GroupRequestEvent): Int {
        return MESSAGE_IGNORE
    }

    companion object {
        const val MESSAGE_BLOCK = 1
        const val MESSAGE_IGNORE = 0
    }
}