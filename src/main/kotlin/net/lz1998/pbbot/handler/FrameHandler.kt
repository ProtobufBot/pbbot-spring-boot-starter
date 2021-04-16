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
            FrameType.TPrivateMessageEvent -> eventHandler.handlePrivateMessageEvent(bot, frame.privateMessageEvent)
            FrameType.TGroupMessageEvent -> eventHandler.handleGroupMessageEvent(bot, frame.groupMessageEvent)
            FrameType.TGroupUploadNoticeEvent -> eventHandler.handleGroupUploadNoticeEvent(bot, frame.groupUploadNoticeEvent)
            FrameType.TGroupAdminNoticeEvent -> eventHandler.handleGroupAdminNoticeEvent(bot, frame.groupAdminNoticeEvent)
            FrameType.TGroupDecreaseNoticeEvent -> eventHandler.handleGroupDecreaseNoticeEvent(bot, frame.groupDecreaseNoticeEvent)
            FrameType.TGroupIncreaseNoticeEvent -> eventHandler.handleGroupIncreaseNoticeEvent(bot, frame.groupIncreaseNoticeEvent)
            FrameType.TGroupBanNoticeEvent -> eventHandler.handleGroupBanNoticeEvent(bot, frame.groupBanNoticeEvent)
            FrameType.TFriendAddNoticeEvent -> eventHandler.handleFriendAddNoticeEvent(bot, frame.friendAddNoticeEvent)
            FrameType.TGroupRecallNoticeEvent -> eventHandler.handleGroupRecallNoticeEvent(bot,frame.groupRecallNoticeEvent)
            FrameType.TFriendRecallNoticeEvent -> eventHandler.handleFriendRecallNoticeEvent(bot,frame.friendRecallNoticeEvent)
            FrameType.TFriendRequestEvent -> eventHandler.handleFriendRequestEvent(bot, frame.friendRequestEvent)
            FrameType.TGroupRequestEvent -> eventHandler.handleGroupRequestEvent(bot, frame.groupRequestEvent)

            // 如果不是 event，说明是 api 调用的响应结果
            else -> apiSender.echoFutureMap[frame.echo]?.complete(frame)
        }

    }
}