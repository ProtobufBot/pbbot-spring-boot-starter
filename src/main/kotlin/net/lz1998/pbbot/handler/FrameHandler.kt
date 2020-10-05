package net.lz1998.pbbot.handler

import net.lz1998.pbbot.alias.Frame
import net.lz1998.pbbot.alias.FrameType
import net.lz1998.pbbot.bot.ApiSender
import net.lz1998.pbbot.bot.Bot
import net.lz1998.pbbot.bot.BotContainer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class FrameHandler {

    @Autowired
    lateinit var eventHandler: EventHandler

    @Autowired
    lateinit var apiSender: ApiSender


    @Autowired
    lateinit var botContainer: BotContainer

    fun handleFrame(frame: Frame) {
        val bot: Bot = botContainer.bots[frame.botId] ?: return
        when (frame.frameType) {
            FrameType.PrivateMessageEvent -> eventHandler.handlePrivateMessageEvent(bot, frame.privateMessageEvent)
            FrameType.GroupMessageEvent -> eventHandler.handleGroupMessageEvent(bot, frame.groupMessageEvent)
            FrameType.GroupUploadNoticeEvent -> eventHandler.handleUnknown(null)
            FrameType.GroupAdminNoticeEvent -> eventHandler.handleUnknown(null)
            FrameType.GroupDecreaseNoticeEvent -> eventHandler.handleGroupDecreaseNoticeEvent(
                bot,
                frame.groupDecreaseNoticeEvent
            )
            FrameType.GroupIncreaseNoticeEvent -> eventHandler.handleGroupIncreaseNoticeEvent(
                bot,
                frame.groupIncreaseNoticeEvent
            )
            FrameType.GroupBanNoticeEvent -> eventHandler.handleUnknown(null)
            FrameType.FriendAddNoticeEvent -> eventHandler.handleFriendAddNoticeEvent(bot, frame.friendAddNoticeEvent)
            FrameType.GroupRecallNoticeEvent -> eventHandler.handleUnknown(null)
            FrameType.FriendRecallNoticeEvent -> eventHandler.handleUnknown(null)
            FrameType.FriendRequestEvent -> eventHandler.handleFriendRequestEvent(bot, frame.friendRequestEvent)
            FrameType.GroupRequestEvent -> eventHandler.handleGroupRequestEvent(bot, frame.groupRequestEvent)

            // 如果不是 event，说明是 api 调用的响应结果
            else -> apiSender.echoFutureMap[frame.echo]?.complete(frame)
        }

    }
}