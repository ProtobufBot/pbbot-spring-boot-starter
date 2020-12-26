package net.lz1998.pbbot.handler

import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession

@Component
class BotSessionInterceptor {
    fun checkSession(session: WebSocketSession): Boolean {
        return true
    }
}