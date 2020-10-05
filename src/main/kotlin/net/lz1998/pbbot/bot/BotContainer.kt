package net.lz1998.pbbot.bot

import org.springframework.stereotype.Component

@Component
class BotContainer {
    val bots = mutableMapOf<Long, Bot>()
}