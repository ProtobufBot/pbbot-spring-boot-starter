package net.lz1998.pbbot.bot

import org.springframework.web.socket.WebSocketSession

open class MiraiBot(
    override var selfId: Long,
    override var botSession: WebSocketSession,
    override var apiSender: ApiSender
) : Bot