package net.lz1998.pbbot.bot

import net.lz1998.pbbot.alias.*
import net.lz1998.pbbot.utils.Msg
import net.lz1998.pbbot.utils.toMessageList
import org.springframework.web.socket.WebSocketSession


interface Bot {
    var selfId: Long
    var botSession: WebSocketSession
    var apiSender: ApiSender

    /**
     * 发送私聊消息
     *
     * @param user_id     对方 QQ 号
     * @param message     要发送的内容
     * @param auto_escape 消息内容是否作为纯文本发送（即不解析 CQ 码），只在 message 字段是字符串时有效
     * @return 结果
     */
    fun sendPrivateMsg(user_id: Long, message: String, auto_escape: Boolean = false): SendPrivateMsgResp? {
        val reqBuilder = SendPrivateMsgReq.newBuilder()
        reqBuilder.userId = user_id
        reqBuilder.addAllMessage(message.toMessageList())
        reqBuilder.autoEscape = auto_escape
        return apiSender.sendPrivateMsg(botSession, selfId, reqBuilder.build())
    }

    /**
     * 发送私聊消息
     *
     * @param user_id          对方 QQ 号
     * @param messageChain     消息链
     * @param auto_escape      消息内容是否作为纯文本发送（即不解析 CQ 码），只在 message 字段是字符串时有效
     * @return 结果
     */
    fun sendPrivateMsg(user_id: Long, messageChain: MessageChain, auto_escape: Boolean = false): SendPrivateMsgResp? {
        val reqBuilder = SendPrivateMsgReq.newBuilder()
        reqBuilder.userId = user_id
        reqBuilder.addAllMessage(messageChain)
        reqBuilder.autoEscape = auto_escape
        return apiSender.sendPrivateMsg(botSession, selfId, reqBuilder.build())
    }

    /**
     * 发送私聊消息
     *
     * @param user_id          对方 QQ 号
     * @param msg              消息
     * @param auto_escape      消息内容是否作为纯文本发送（即不解析 CQ 码），只在 message 字段是字符串时有效
     * @return 结果
     */
    fun sendPrivateMsg(user_id: Long, msg: Msg, auto_escape: Boolean = false): SendPrivateMsgResp? {
        val reqBuilder = SendPrivateMsgReq.newBuilder()
        reqBuilder.userId = user_id
        reqBuilder.addAllMessage(msg.build())
        reqBuilder.autoEscape = auto_escape
        return apiSender.sendPrivateMsg(botSession, selfId, reqBuilder.build())
    }

    /**
     * 发送群消息
     *
     * @param group_id    群号
     * @param message     要发送的内容
     * @param auto_escape 消息内容是否作为纯文本发送（即不解析 CQ 码），只在 message 字段是字符串时有效
     * @return 结果
     */
    fun sendGroupMsg(group_id: Long, message: String, auto_escape: Boolean = false): SendGroupMsgResp? {
        val reqBuilder = SendGroupMsgReq.newBuilder()
        reqBuilder.groupId = group_id
        reqBuilder.addAllMessage(message.toMessageList())
        reqBuilder.autoEscape = auto_escape
        return apiSender.sendGroupMsg(botSession, selfId, reqBuilder.build())
    }

    /**
     * 发送群消息
     *
     * @param group_id         群号
     * @param messageChain     消息链
     * @param auto_escape      消息内容是否作为纯文本发送（即不解析 CQ 码），只在 message 字段是字符串时有效
     * @return 结果
     */
    fun sendGroupMsg(group_id: Long, messageChain: MessageChain, auto_escape: Boolean = false): SendGroupMsgResp? {
        val reqBuilder = SendGroupMsgReq.newBuilder()
        reqBuilder.groupId = group_id
        reqBuilder.addAllMessage(messageChain)
        reqBuilder.autoEscape = auto_escape
        return apiSender.sendGroupMsg(botSession, selfId, reqBuilder.build())
    }

    /**
     * 发送群消息
     *
     * @param group_id         群号
     * @param msg              消息
     * @param auto_escape      消息内容是否作为纯文本发送（即不解析 CQ 码），只在 message 字段是字符串时有效
     * @return 结果
     */
    fun sendGroupMsg(group_id: Long, msg: Msg, auto_escape: Boolean = false): SendGroupMsgResp? {
        val reqBuilder = SendGroupMsgReq.newBuilder()
        reqBuilder.groupId = group_id
        reqBuilder.addAllMessage(msg.build())
        reqBuilder.autoEscape = auto_escape
        return apiSender.sendGroupMsg(botSession, selfId, reqBuilder.build())
    }


    /**
     * 撤回消息
     *
     * @param message_id 消息 ID
     * @return 结果
     */
    fun deleteMsg(message_id: MessageReceipt): DeleteMsgResp? {
        val reqBuilder = DeleteMsgReq.newBuilder()
        reqBuilder.messageId = message_id
        return apiSender.deleteMsg(botSession, selfId, reqBuilder.build())
    }

    /**
     * 获取消息
     *
     * @param message_id 消息 ID
     * @return 结果
     */
    fun getMsg(message_id: Int): GetMsgResp? {
        val reqBuilder = GetMsgReq.newBuilder()
        reqBuilder.messageId = message_id
        return apiSender.getMsg(botSession, selfId, reqBuilder.build())
    }

    /**
     * 点赞
     *
     * @param user_id 对方 QQ 号
     * @param user_id 次数
     * @return 结果
     */
    fun sendLike(user_id: Long, times: Int): SendLikeResp? {
        val reqBuilder = SendLikeReq.newBuilder()
        reqBuilder.userId = user_id
        reqBuilder.times = times
        return apiSender.sendLike(botSession, selfId, reqBuilder.build())
    }

    /**
     * 群组踢人
     *
     * @param group_id           群号
     * @param user_id            要踢的 QQ 号
     * @param reject_add_request 拒绝此人的加群请求
     * @return 结果
     */
    fun setGroupKick(group_id: Long, user_id: Long, reject_add_request: Boolean): SetGroupKickResp? {
        val reqBuilder = SetGroupKickReq.newBuilder()
        reqBuilder.groupId = group_id
        reqBuilder.userId = user_id
        reqBuilder.rejectAddRequest = reject_add_request
        return apiSender.setGroupKick(botSession, selfId, reqBuilder.build())
    }

    /**
     * 群组批量踢人
     *
     * @param group_id           群号
     * @param user_ids           要踢的 QQ 号
     * @param reject_add_request 拒绝此人的加群请求
     * @return 结果
     */
    fun setGroupKickBatch(group_id: Long, user_ids: List<Long>, reject_add_request: Boolean): SetGroupKickResp? {
        val reqBuilder = SetGroupKickReq.newBuilder()
        reqBuilder.groupId = group_id
        reqBuilder.addAllUserIds(user_ids);
        reqBuilder.rejectAddRequest = reject_add_request
        return apiSender.setGroupKick(botSession, selfId, reqBuilder.build())
    }

    /**
     * 群组单人禁言
     *
     * @param group_id 群号
     * @param user_id  要禁言的 QQ 号
     * @param duration 禁言时长，单位秒，0 表示取消禁言
     * @return 结果
     */
    fun setGroupBan(group_id: Long, user_id: Long, duration: Int): SetGroupBanResp? {
        val reqBuilder = SetGroupBanReq.newBuilder()
        reqBuilder.groupId = group_id
        reqBuilder.userId = user_id
        reqBuilder.duration = duration
        return apiSender.setGroupBan(botSession, selfId, reqBuilder.build())
    }

    /**
     * 群组全员禁言
     *
     * @param group_id 群号
     * @param enable   是否禁言
     * @return 结果
     */
    fun setGroupWholeBan(group_id: Long, enable: Boolean): SetGroupWholeBanResp? {
        val reqBuilder = SetGroupWholeBanReq.newBuilder()
        reqBuilder.groupId = group_id
        reqBuilder.enable = enable
        return apiSender.setGroupWholeBan(botSession, selfId, reqBuilder.build())
    }

    /**
     * 设置群名片（群备注）
     *
     * @param group_id 群号
     * @param user_id  要设置的 QQ 号
     * @param card     群名片内容，不填或空字符串表示删除群名片
     * @return 结果
     */
    fun setGroupCard(group_id: Long, user_id: Long, card: String?): SetGroupCardResp? {
        val reqBuilder = SetGroupCardReq.newBuilder()
        reqBuilder.groupId = group_id
        reqBuilder.userId = user_id
        reqBuilder.card = card
        return apiSender.setGroupCard(botSession, selfId, reqBuilder.build())
    }

    /**
     * 设置群名称
     *
     * @param group_id 群号
     * @param group_name  群名称
     * @return 结果
     */
    fun setGroupName(group_id: Long, group_name: String?): SetGroupNameResp? {
        val reqBuilder = SetGroupNameReq.newBuilder()
        reqBuilder.groupId = group_id
        reqBuilder.groupName = group_name
        return apiSender.setGroupName(botSession, selfId, reqBuilder.build())
    }

    /**
     * @param group_id   群号
     * @param is_dismiss 是否解散，如果登录号是群主，则仅在此项为 true 时能够解散
     * @return 结果
     */
    fun setGroupLeave(group_id: Long, is_dismiss: Boolean): SetGroupLeaveResp? {
        val reqBuilder = SetGroupLeaveReq.newBuilder()
        reqBuilder.groupId = group_id
        reqBuilder.isDismiss = is_dismiss
        return apiSender.setGroupLeave(botSession, selfId, reqBuilder.build())
    }

    /**
     * 设置群组专属头衔
     *
     * @param group_id      群号
     * @param user_id       要设置的 QQ 号
     * @param special_title 专属头衔，不填或空字符串表示删除专属头衔
     * @param duration      专属头衔有效期，单位秒，-1 表示永久，不过此项似乎没有效果，可能是只有某些特殊的时间长度有效，有待测试
     * @return 结果
     */
    fun setGroupSpecialTitle(
        group_id: Long,
        user_id: Long,
        special_title: String?,
        duration: Long
    ): SetGroupSpecialTitleResp? {
        val reqBuilder = SetGroupSpecialTitleReq.newBuilder()
        reqBuilder.groupId = group_id
        reqBuilder.userId = user_id
        reqBuilder.specialTitle = special_title
        reqBuilder.duration = duration
        return apiSender.setGroupSpecialTitle(botSession, selfId, reqBuilder.build())
    }


    /**
     * 处理加好友请求
     *
     * @param flag    加好友请求的 flag（需从上报的数据中获得）
     * @param approve 是否同意请求
     * @param remark  添加后的好友备注（仅在同意时有效）
     * @return 结果
     */
    fun setFriendAddRequest(flag: String, approve: Boolean, remark: String?): SetFriendAddRequestResp? {
        val reqBuilder = SetFriendAddRequestReq.newBuilder()
        reqBuilder.flag = flag
        reqBuilder.approve = approve
        reqBuilder.remark = remark
        return apiSender.setFriendAddRequest(botSession, selfId, reqBuilder.build())
    }

    /**
     * 处理加群请求／邀请
     *
     * @param flag     加群请求的 flag（需从上报的数据中获得）
     * @param sub_type add 或 invite，请求类型（需要和上报消息中的 sub_type 字段相符）
     * @param approve  是否同意请求／邀请
     * @param reason   拒绝理由（仅在拒绝时有效）
     * @return 结果
     */
    fun setGroupAddRequest(
        flag: String,
        sub_type: String?,
        approve: Boolean,
        reason: String?
    ): SetGroupAddRequestResp? {
        val reqBuilder = SetGroupAddRequestReq.newBuilder()
        reqBuilder.flag = flag
        reqBuilder.subType = sub_type
        reqBuilder.approve = approve
        reqBuilder.reason = reason
        return apiSender.setGroupAddRequest(botSession, selfId, reqBuilder.build())
    }

    /**
     * 获取登录号信息
     *
     * @return 结果
     */
    fun getLoginInfo(): GetLoginInfoResp? {
        val reqBuilder = GetLoginInfoReq.newBuilder()
        return apiSender.getLoginInfo(botSession, selfId, reqBuilder.build())
    }

    /**
     * 获取陌生人信息
     *
     * @param user_id   QQ号
     * @return 结果
     */
    fun getStrangerInfo(user_id: Long): GetStrangerInfoResp? {
        val reqBuilder = GetStrangerInfoReq.newBuilder()
        reqBuilder.userId = user_id
        return apiSender.getStrangerInfo(botSession, selfId, reqBuilder.build())
    }

    /**
     * 获取好友列表
     *
     * @return 结果
     */
    fun getFriendList(): GetFriendListResp? {
        val reqBuilder = GetFriendListReq.newBuilder()
        return apiSender.getFriendList(botSession, selfId, reqBuilder.build())
    }

    /**
     * 获取群列表
     *
     * @return 结果
     */
    fun getGroupList(): GetGroupListResp? {
        val reqBuilder = GetGroupListReq.newBuilder()
        return apiSender.getGroupList(botSession, selfId, reqBuilder.build())
    }

    /**
     * 获取群信息
     *
     * @param group_id 群号
     * @param no_cache 是否不使用缓存（使用缓存可能更新不及时，但响应更快）
     * @return 结果
     */
    fun getGroupInfo(group_id: Long, no_cache: Boolean): GetGroupInfoResp? {
        val reqBuilder = GetGroupInfoReq.newBuilder()
        reqBuilder.groupId = group_id
        reqBuilder.noCache = no_cache
        return apiSender.getGroupInfo(botSession, selfId, reqBuilder.build())
    }

    /**
     * 获取群成员信息
     *
     * @param group_id 群号
     * @param user_id  QQ 号
     * @param no_cache 是否不使用缓存（使用缓存可能更新不及时，但响应更快）
     * @return 结果
     */
    fun getGroupMemberInfo(group_id: Long, user_id: Long, no_cache: Boolean): GetGroupMemberInfoResp? {
        val reqBuilder = GetGroupMemberInfoReq.newBuilder()
        reqBuilder.groupId = group_id
        reqBuilder.userId = user_id
        reqBuilder.noCache = no_cache
        return apiSender.getGroupMemberInfo(botSession, selfId, reqBuilder.build())
    }

    /**
     * 获取群成员列表
     *
     *
     * 响应内容为 JSON 数组，每个元素的内容和上面的 /get_group_member_info 接口相同，但对于同一个群组的同一个成员，获取列表时和获取单独的成员信息时，某些字段可能有所不同，例如 area、title 等字段在获取列表时无法获得，具体应以单独的成员信息为准。
     *
     * @param group_id 群号
     * @return 结果
     */
    fun getGroupMemberList(group_id: Long): GetGroupMemberListResp? {
        val reqBuilder = GetGroupMemberListReq.newBuilder()
        reqBuilder.groupId = group_id
        return apiSender.getGroupMemberList(botSession, selfId, reqBuilder.build())
    }
}