package net.lz1998.pbbot.handler

import net.lz1998.pbbot.alias.Frame
import net.lz1998.pbbot.bot.BotFactory
import net.lz1998.pbbot.bot.BotContainer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.*
import org.springframework.web.socket.handler.BinaryWebSocketHandler
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator
import java.util.concurrent.ExecutorService

@Component
class BotWebSocketHandler : BinaryWebSocketHandler() {
    @Autowired
    lateinit var sessionInterceptor: BotSessionInterceptor

    @Autowired
    lateinit var botFactory: BotFactory

    @Autowired
    lateinit var frameHandler: FrameHandler

    @Autowired
    lateinit var botContainer: BotContainer

    @Autowired
    lateinit var executor: ExecutorService


    @Synchronized
    override fun afterConnectionEstablished(session: WebSocketSession) {
        if (!sessionInterceptor.checkSession(session)) {
            return
        }
        val xSelfId = session.handshakeHeaders["x-self-id"]?.get(0)?.toLong() ?: 0L
        if (xSelfId == 0L) {
            session.close()
            return
        }
        botContainer.bots[xSelfId] = botFactory.createBot(xSelfId, ConcurrentWebSocketSessionDecorator(session, 3000, 4096))
        println("$xSelfId connected")
    }

    @Synchronized
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val xSelfId = session.handshakeHeaders["x-self-id"]?.get(0)?.toLong() ?: 0L
        if (xSelfId == 0L) {
            return
        }
        botContainer.bots.remove(xSelfId)
        println("$xSelfId disconnected")
    }


    override fun handleBinaryMessage(session: WebSocketSession, message: BinaryMessage) {
        val xSelfId = session.handshakeHeaders["x-self-id"]?.get(0)?.toLong() ?: 0L
        if (xSelfId == 0L) {
            return
        }
        if (!botContainer.bots.containsKey(xSelfId)) {
            botContainer.bots[xSelfId] = botFactory.createBot(xSelfId, ConcurrentWebSocketSessionDecorator(session, 3000, 4096))
        }

        val frame = Frame.parseFrom(message.payload)
        session.sendMessage(PingMessage())
        executor.execute {
            frameHandler.handleFrame(frame)
        }
    }
}