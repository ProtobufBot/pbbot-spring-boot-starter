package net.lz1998.pbbot.bot

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession

@Component
class BotFactory {

    @Autowired
    lateinit var apiSender: ApiSender

    fun createBot(botId: Long, session: WebSocketSession): Bot {
        return MiraiBot(botId, session, apiSender)
    }
}