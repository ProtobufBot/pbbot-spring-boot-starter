package net.lz1998.pbbot.handler

import net.lz1998.pbbot.alias.Frame
import net.lz1998.pbbot.bot.BotFactory
import net.lz1998.pbbot.bot.MiraiBot
import net.lz1998.pbbot.boot.EventProperties
import org.springframework.web.socket.*
import org.springframework.web.socket.handler.BinaryWebSocketHandler
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

open class WebSocketHandler(
    eventProperties: EventProperties,
    var botFactory: BotFactory,
    var frameHandler: FrameHandler
) : BinaryWebSocketHandler() {
    val botMap = mutableMapOf<Long, MiraiBot>()

    val sessionMap = mutableMapOf<Long, WebSocketSession>()

    var executor: ExecutorService = ThreadPoolExecutor(
        eventProperties.corePoolSize,
        eventProperties.maxPoolSize,
        eventProperties.keepAliveTime,
        TimeUnit.MILLISECONDS,
        ArrayBlockingQueue(eventProperties.workQueueSize)
    );

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val xSelfId = session.handshakeHeaders["x-self-id"]?.get(0)?.toLong() ?: 0L
        if (xSelfId == 0L) {
            session.close()
            return
        }
        sessionMap[xSelfId] = session
        println("$xSelfId connected")
        botMap[xSelfId] = botFactory.createBot(xSelfId, session)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val xSelfId = session.handshakeHeaders["x-self-id"]?.get(0)?.toLong() ?: 0L
        if (xSelfId == 0L) {
            return
        }
        sessionMap.remove(xSelfId, session)
        println("$xSelfId disconnected")
        botMap.remove(xSelfId)
    }


    override fun handleBinaryMessage(session: WebSocketSession, message: BinaryMessage) {
        val frame = Frame.parseFrom(message.payload)
        session.sendMessage(PingMessage())
        executor.execute {
            frameHandler.handleFrame(frame)
        }
    }
}