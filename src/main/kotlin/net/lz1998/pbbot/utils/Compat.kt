package net.lz1998.pbbot.utils

import onebot.OnebotBase

fun String.toMessageList(): List<OnebotBase.Message> {
    var interpreting = false
    val sb = StringBuilder()
    var index = 0
    val messageList = mutableListOf<OnebotBase.Message>()
    this.forEach { c: Char ->
        if (c == '[') {
            if (interpreting) {
                println("CQ消息解析失败：$this，索引：$index")
                return@forEach
            } else {
                interpreting = true
                if (sb.isNotEmpty()) {
                    val lastMsg = sb.toString()
                    sb.delete(0, sb.length)
                    messageList.add(cqTextToMessageInternal(lastMsg))
                }
                sb.append(c)
            }
        } else if (c == ']') {
            if (!interpreting) {
                println("CQ消息解析失败：$this，索引：$index")
                return@forEach
            } else {
                interpreting = false
                sb.append(c)
                if (sb.isNotEmpty()) {
                    val lastMsg = sb.toString()
                    sb.delete(0, sb.length)
                    messageList.add(cqTextToMessageInternal(lastMsg))
                }
            }
        } else {
            sb.append(c)
        }
        index++
    }
    if (sb.isNotEmpty()) {
        messageList.add(cqTextToMessageInternal(sb.toString()))
    }
    return messageList
}

private fun cqTextToMessageInternal(message: String): OnebotBase.Message {
    if (message.startsWith("[CQ:") && message.endsWith("]")) {
        val parts = message.substring(4, message.length - 1).split(delimiters = arrayOf(","), limit = 2)

        lateinit var args: HashMap<String, String>
        args = if (parts.size == 2) {
            parts[1].toMap()
        } else {
            HashMap()
        }
        return OnebotBase.Message.newBuilder().setType(parts[0]).putAllData(args).build()
    }

    return OnebotBase.Message.newBuilder().setType("text").putAllData(mapOf("text" to message.unescape())).build()
}

private fun String.toMap(): HashMap<String, String> {
    val map = HashMap<String, String>()
    split(",").forEach {
        val parts = it.split(delimiters = arrayOf("="), limit = 2)
        map[parts[0]] = parts[1].unescape()
    }
    return map
}

private fun String.unescape(): String {
    return replace("&amp;", "&")
            .replace("&#91;", "[")
            .replace("&#93;", "]")
            .replace("&#44;", ",")
}