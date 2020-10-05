package net.lz1998.pbbot.bot

import com.fasterxml.jackson.databind.util.LRUMap
import com.google.protobuf.MessageLite
import kotlinx.coroutines.*
import net.lz1998.pbbot.alias.*
import org.springframework.web.socket.BinaryMessage
import org.springframework.web.socket.WebSocketSession
import java.util.*


open class ApiSender(open val apiTimeout: Long) {
    open val echoFutureMap = LRUMap<String, CompletableDeferred<Frame>>(128, 1024)

    private fun callApi(session: WebSocketSession, botId: Long, apiReq: MessageLite): MessageLite? {
        val echo = UUID.randomUUID().toString()
        val futureResp = CompletableDeferred<Frame>()
        echoFutureMap.put(echo, futureResp)
        val frameBuilder = Frame.newBuilder()
        frameBuilder.echo = echo
        frameBuilder.botId = botId
        when (apiReq) {
            is SendPrivateMsgReq -> {
                frameBuilder.sendPrivateMsgReq = apiReq;frameBuilder.frameType = FrameType.SendPrivateMsgReq
            }
            is SendGroupMsgReq -> {
                frameBuilder.sendGroupMsgReq = apiReq;frameBuilder.frameType = FrameType.SendGroupMsgReq
            }
            is SendMsgReq -> {
                frameBuilder.sendMsgReq = apiReq;frameBuilder.frameType = FrameType.SendMsgReq
            }
            is DeleteMsgReq -> {
                frameBuilder.deleteMsgReq = apiReq;frameBuilder.frameType = FrameType.DeleteMsgReq
            }
            is GetMsgReq -> {
                frameBuilder.getMsgReq = apiReq;frameBuilder.frameType = FrameType.GetMsgReq
            }
            is GetForwardMsgReq -> {
                frameBuilder.getForwardMsgReq = apiReq;frameBuilder.frameType = FrameType.GetForwardMsgReq
            }
            is SendLikeReq -> {
                frameBuilder.sendLikeReq = apiReq;frameBuilder.frameType = FrameType.SendLikeReq
            }
            is SetGroupKickReq -> {
                frameBuilder.setGroupKickReq = apiReq;frameBuilder.frameType = FrameType.SetGroupKickReq
            }
            is SetGroupBanReq -> {
                frameBuilder.setGroupBanReq = apiReq;frameBuilder.frameType = FrameType.SetGroupBanReq
            }
            is SetGroupAnonymousBanReq -> {
                frameBuilder.setGroupAnonymousBanReq = apiReq;frameBuilder.frameType =
                    FrameType.SetGroupAnonymousBanReq
            }
            is SetGroupWholeBanReq -> {
                frameBuilder.setGroupWholeBanReq = apiReq;frameBuilder.frameType = FrameType.SetGroupWholeBanReq
            }
            is SetGroupAdminReq -> {
                frameBuilder.setGroupAdminReq = apiReq;frameBuilder.frameType = FrameType.SetGroupAdminReq
            }
            is SetGroupAnonymousReq -> {
                frameBuilder.setGroupAnonymousReq = apiReq;frameBuilder.frameType = FrameType.SetGroupAnonymousReq
            }
            is SetGroupCardReq -> {
                frameBuilder.setGroupCardReq = apiReq;frameBuilder.frameType = FrameType.SetGroupCardReq
            }
            is SetGroupNameReq -> {
                frameBuilder.setGroupNameReq = apiReq;frameBuilder.frameType = FrameType.SetGroupNameReq
            }
            is SetGroupLeaveReq -> {
                frameBuilder.setGroupLeaveReq = apiReq;frameBuilder.frameType = FrameType.SetGroupLeaveReq
            }
            is SetGroupSpecialTitleReq -> {
                frameBuilder.setGroupSpecialTitleReq = apiReq;frameBuilder.frameType =
                    FrameType.SetGroupSpecialTitleReq
            }
            is SetFriendAddRequestReq -> {
                frameBuilder.setFriendAddRequestReq = apiReq;frameBuilder.frameType = FrameType.SetFriendAddRequestReq
            }
            is SetGroupAddRequestReq -> {
                frameBuilder.setGroupAddRequestReq = apiReq;frameBuilder.frameType = FrameType.SetGroupAddRequestReq
            }
            is GetLoginInfoReq -> {
                frameBuilder.getLoginInfoReq = apiReq;frameBuilder.frameType = FrameType.GetLoginInfoReq
            }
            is GetStrangerInfoReq -> {
                frameBuilder.getStrangerInfoReq = apiReq;frameBuilder.frameType = FrameType.GetStrangerInfoReq
            }
            is GetFriendListReq -> {
                frameBuilder.getFriendListReq = apiReq;frameBuilder.frameType = FrameType.GetFriendListReq
            }
            is GetGroupInfoReq -> {
                frameBuilder.getGroupInfoReq = apiReq;frameBuilder.frameType = FrameType.GetGroupInfoReq
            }
            is GetGroupListReq -> {
                frameBuilder.getGroupListReq = apiReq;frameBuilder.frameType = FrameType.GetGroupListReq
            }
            is GetGroupMemberInfoReq -> {
                frameBuilder.getGroupMemberInfoReq = apiReq;frameBuilder.frameType = FrameType.GetGroupMemberInfoReq
            }
            is GetGroupMemberListReq -> {
                frameBuilder.getGroupMemberListReq = apiReq;frameBuilder.frameType = FrameType.GetGroupMemberListReq
            }
            is GetGroupHonorInfoReq -> {
                frameBuilder.getGroupHonorInfoReq = apiReq;frameBuilder.frameType = FrameType.GetGroupHonorInfoReq
            }
            is GetCookiesReq -> {
                frameBuilder.getCookiesReq = apiReq;frameBuilder.frameType = FrameType.GetCookiesReq
            }
            is GetCsrfTokenReq -> {
                frameBuilder.getCsrfTokenReq = apiReq;frameBuilder.frameType = FrameType.GetCsrfTokenReq
            }
            is GetCredentialsReq -> {
                frameBuilder.getCredentialsReq = apiReq;frameBuilder.frameType = FrameType.GetCredentialsReq
            }
            is GetRecordReq -> {
                frameBuilder.getRecordReq = apiReq;frameBuilder.frameType = FrameType.GetRecordReq
            }
            is GetImageReq -> {
                frameBuilder.getImageReq = apiReq;frameBuilder.frameType = FrameType.GetImageReq
            }
            is CanSendImageReq -> {
                frameBuilder.canSendImageReq = apiReq;frameBuilder.frameType = FrameType.CanSendImageReq
            }
            is CanSendRecordReq -> {
                frameBuilder.canSendRecordReq = apiReq;frameBuilder.frameType = FrameType.CanSendRecordReq
            }
            is GetStatusReq -> {
                frameBuilder.getStatusReq = apiReq;frameBuilder.frameType = FrameType.GetStatusReq
            }
            is GetVersionInfoReq -> {
                frameBuilder.getVersionInfoReq = apiReq;frameBuilder.frameType = FrameType.GetVersionInfoReq
            }
            is SetRestartReq -> {
                frameBuilder.setRestartReq = apiReq;frameBuilder.frameType = FrameType.SetRestartReq
            }
            is CleanCacheReq -> {
                frameBuilder.cleanCacheReq = apiReq;frameBuilder.frameType = FrameType.CleanCacheReq
            }
            else -> return null
        }
        frameBuilder.ok = true
        val reqFrame = frameBuilder.build()
        val msg = BinaryMessage(reqFrame.toByteArray())
        session.sendMessage(msg)
        val respFrame = runBlocking {
            try {
                withTimeout(apiTimeout) {
                    futureResp.await()
                }
            } catch (e: Exception) {
                return@runBlocking null
            }
        } ?: return null
        return when (respFrame.frameType) {
            FrameType.SendPrivateMsgResp -> respFrame.sendPrivateMsgResp
            FrameType.SendGroupMsgResp -> respFrame.sendGroupMsgResp
            FrameType.SendMsgResp -> respFrame.sendMsgResp
            FrameType.DeleteMsgResp -> respFrame.deleteMsgResp
            FrameType.GetMsgResp -> respFrame.getMsgResp
            FrameType.GetForwardMsgResp -> respFrame.getForwardMsgResp
            FrameType.SendLikeResp -> respFrame.sendLikeResp
            FrameType.SetGroupKickResp -> respFrame.setGroupKickResp
            FrameType.SetGroupBanResp -> respFrame.setGroupBanResp
            FrameType.SetGroupAnonymousResp -> respFrame.setGroupAnonymousResp
            FrameType.SetGroupWholeBanResp -> respFrame.setGroupWholeBanResp
            FrameType.SetGroupAdminResp -> respFrame.setGroupAdminResp
            FrameType.SetGroupAnonymousBanResp -> respFrame.setGroupAnonymousBanResp
            FrameType.SetGroupCardResp -> respFrame.setGroupCardResp
            FrameType.SetGroupNameResp -> respFrame.setGroupNameResp
            FrameType.SetGroupLeaveResp -> respFrame.setGroupLeaveResp
            FrameType.SetGroupSpecialTitleResp -> respFrame.setGroupSpecialTitleResp
            FrameType.SetFriendAddRequestResp -> respFrame.setFriendAddRequestResp
            FrameType.SetGroupAddRequestResp -> respFrame.setGroupAddRequestResp
            FrameType.GetLoginInfoResp -> respFrame.getLoginInfoResp
            FrameType.GetStrangerInfoResp -> respFrame.getStrangerInfoResp
            FrameType.GetFriendListResp -> respFrame.getFriendListResp
            FrameType.GetGroupInfoResp -> respFrame.getGroupInfoResp
            FrameType.GetGroupListResp -> respFrame.getGroupListResp
            FrameType.GetGroupMemberInfoResp -> respFrame.getGroupMemberInfoResp
            FrameType.GetGroupMemberListResp -> respFrame.getGroupMemberListResp
            FrameType.GetGroupHonorInfoResp -> respFrame.getGroupHonorInfoResp
            FrameType.GetCookiesResp -> respFrame.getCookiesResp
            FrameType.GetCsrfTokenResp -> respFrame.getCsrfTokenResp
            FrameType.GetCredentialsResp -> respFrame.getCredentialsResp
            FrameType.GetRecordResp -> respFrame.getRecordResp
            FrameType.GetImageResp -> respFrame.getImageResp
            FrameType.CanSendImageResp -> respFrame.canSendImageResp
            FrameType.CanSendRecordResp -> respFrame.canSendRecordResp
            FrameType.GetStatusResp -> respFrame.getStatusResp
            FrameType.GetVersionInfoResp -> respFrame.getVersionInfoResp
            FrameType.SetRestartResp -> respFrame.setRestartResp
            FrameType.CleanCacheResp -> respFrame.cleanCacheResp
            else -> null
        }
    }

    fun sendPrivateMsg(session: WebSocketSession, botId: Long, apiReq: SendPrivateMsgReq) =
        callApi(session, botId, apiReq) as SendPrivateMsgResp?

    fun sendGroupMsg(session: WebSocketSession, botId: Long, apiReq: SendGroupMsgReq) =
        callApi(session, botId, apiReq) as SendGroupMsgResp?

    fun sendMsg(session: WebSocketSession, botId: Long, apiReq: SendMsgReq) =
        callApi(session, botId, apiReq) as SendMsgResp?

    fun deleteMsg(session: WebSocketSession, botId: Long, apiReq: DeleteMsgReq) =
        callApi(session, botId, apiReq) as DeleteMsgResp?

    fun getMsg(session: WebSocketSession, botId: Long, apiReq: GetMsgReq) =
        callApi(session, botId, apiReq) as GetMsgResp?

    fun getForwardMsg(session: WebSocketSession, botId: Long, apiReq: GetForwardMsgReq) =
        callApi(session, botId, apiReq) as GetForwardMsgResp?

    fun sendLike(session: WebSocketSession, botId: Long, apiReq: SendLikeReq) =
        callApi(session, botId, apiReq) as SendLikeResp?

    fun setGroupKick(session: WebSocketSession, botId: Long, apiReq: SetGroupKickReq) =
        callApi(session, botId, apiReq) as SetGroupKickResp?

    fun setGroupBan(session: WebSocketSession, botId: Long, apiReq: SetGroupBanReq) =
        callApi(session, botId, apiReq) as SetGroupBanResp?

    fun setGroupAnonymousBan(session: WebSocketSession, botId: Long, apiReq: SetGroupAnonymousBanReq) =
        callApi(session, botId, apiReq) as SetGroupAnonymousBanResp?

    fun setGroupWholeBan(session: WebSocketSession, botId: Long, apiReq: SetGroupWholeBanReq) =
        callApi(session, botId, apiReq) as SetGroupWholeBanResp?

    fun setGroupAdmin(session: WebSocketSession, botId: Long, apiReq: SetGroupAdminReq) =
        callApi(session, botId, apiReq) as SetGroupAdminResp?

    fun setGroupAnonymous(session: WebSocketSession, botId: Long, apiReq: SetGroupAnonymousReq) =
        callApi(session, botId, apiReq) as SetGroupAnonymousResp?

    fun setGroupCard(session: WebSocketSession, botId: Long, apiReq: SetGroupCardReq) =
        callApi(session, botId, apiReq) as SetGroupCardResp?

    fun setGroupName(session: WebSocketSession, botId: Long, apiReq: SetGroupNameReq) =
        callApi(session, botId, apiReq) as SetGroupNameResp?

    fun setGroupLeave(session: WebSocketSession, botId: Long, apiReq: SetGroupLeaveReq) =
        callApi(session, botId, apiReq) as SetGroupLeaveResp?

    fun setGroupSpecialTitle(session: WebSocketSession, botId: Long, apiReq: SetGroupSpecialTitleReq) =
        callApi(session, botId, apiReq) as SetGroupSpecialTitleResp?

    fun setFriendAddRequest(session: WebSocketSession, botId: Long, apiReq: SetFriendAddRequestReq) =
        callApi(session, botId, apiReq) as SetFriendAddRequestResp?

    fun setFriendAdd(session: WebSocketSession, botId: Long, apiReq: SetFriendAddRequestReq) =
        callApi(session, botId, apiReq) as SetFriendAddRequestResp?

    fun setGroupAddRequest(session: WebSocketSession, botId: Long, apiReq: SetGroupAddRequestReq) =
        callApi(session, botId, apiReq) as SetGroupAddRequestResp?

    fun setGroupAdd(session: WebSocketSession, botId: Long, apiReq: SetGroupAddRequestReq) =
        callApi(session, botId, apiReq) as SetGroupAddRequestResp?

    fun getLoginInfo(session: WebSocketSession, botId: Long, apiReq: GetLoginInfoReq) =
        callApi(session, botId, apiReq) as GetLoginInfoResp?

    fun getStrangerInfo(session: WebSocketSession, botId: Long, apiReq: GetStrangerInfoReq) =
        callApi(session, botId, apiReq) as GetStrangerInfoResp?

    fun getFriendList(session: WebSocketSession, botId: Long, apiReq: GetFriendListReq) =
        callApi(session, botId, apiReq) as GetFriendListResp?

    fun getGroupInfo(session: WebSocketSession, botId: Long, apiReq: GetGroupInfoReq) =
        callApi(session, botId, apiReq) as GetGroupInfoResp?

    fun getGroupList(session: WebSocketSession, botId: Long, apiReq: GetGroupListReq) =
        callApi(session, botId, apiReq) as GetGroupListResp?

    fun getGroupMemberInfo(session: WebSocketSession, botId: Long, apiReq: GetGroupMemberInfoReq) =
        callApi(session, botId, apiReq) as GetGroupMemberInfoResp?

    fun getGroupMemberList(session: WebSocketSession, botId: Long, apiReq: GetGroupMemberListReq) =
        callApi(session, botId, apiReq) as GetGroupMemberListResp?

    fun getGroupHonorInfo(session: WebSocketSession, botId: Long, apiReq: GetGroupHonorInfoReq) =
        callApi(session, botId, apiReq) as GetGroupHonorInfoResp?

    fun getCookies(session: WebSocketSession, botId: Long, apiReq: GetCookiesReq) =
        callApi(session, botId, apiReq) as GetCookiesResp?

    fun getCsrfToken(session: WebSocketSession, botId: Long, apiReq: GetCsrfTokenReq) =
        callApi(session, botId, apiReq) as GetCsrfTokenResp?

    fun getCredentials(session: WebSocketSession, botId: Long, apiReq: GetCredentialsReq) =
        callApi(session, botId, apiReq) as GetCredentialsResp?

    fun getRecord(session: WebSocketSession, botId: Long, apiReq: GetRecordReq) =
        callApi(session, botId, apiReq) as GetRecordResp?

    fun getImage(session: WebSocketSession, botId: Long, apiReq: GetImageReq) =
        callApi(session, botId, apiReq) as GetImageResp?

    fun canSendImage(session: WebSocketSession, botId: Long, apiReq: CanSendImageReq) =
        callApi(session, botId, apiReq) as CanSendImageResp?

    fun canSendRecord(session: WebSocketSession, botId: Long, apiReq: CanSendRecordReq) =
        callApi(session, botId, apiReq) as CanSendRecordResp?

    fun getStatus(session: WebSocketSession, botId: Long, apiReq: GetStatusReq) =
        callApi(session, botId, apiReq) as GetStatusResp?

    fun getVersionInfo(session: WebSocketSession, botId: Long, apiReq: GetVersionInfoReq) =
        callApi(session, botId, apiReq) as GetVersionInfoResp?

    fun setRestart(session: WebSocketSession, botId: Long, apiReq: SetRestartReq) =
        callApi(session, botId, apiReq) as SetRestartResp?

    fun cleanCache(session: WebSocketSession, botId: Long, apiReq: CleanCacheReq) =
        callApi(session, botId, apiReq) as CleanCacheResp?
}