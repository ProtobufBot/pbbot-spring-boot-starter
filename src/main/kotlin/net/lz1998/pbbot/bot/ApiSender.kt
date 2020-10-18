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
                frameBuilder.sendPrivateMsgReq = apiReq;frameBuilder.frameType = FrameType.TSendPrivateMsgReq
            }
            is SendGroupMsgReq -> {
                frameBuilder.sendGroupMsgReq = apiReq;frameBuilder.frameType = FrameType.TSendGroupMsgReq
            }
            is SendMsgReq -> {
                frameBuilder.sendMsgReq = apiReq;frameBuilder.frameType = FrameType.TSendMsgReq
            }
            is DeleteMsgReq -> {
                frameBuilder.deleteMsgReq = apiReq;frameBuilder.frameType = FrameType.TDeleteMsgReq
            }
            is GetMsgReq -> {
                frameBuilder.getMsgReq = apiReq;frameBuilder.frameType = FrameType.TGetMsgReq
            }
            is GetForwardMsgReq -> {
                frameBuilder.getForwardMsgReq = apiReq;frameBuilder.frameType = FrameType.TGetForwardMsgReq
            }
            is SendLikeReq -> {
                frameBuilder.sendLikeReq = apiReq;frameBuilder.frameType = FrameType.TSendLikeReq
            }
            is SetGroupKickReq -> {
                frameBuilder.setGroupKickReq = apiReq;frameBuilder.frameType = FrameType.TSetGroupKickReq
            }
            is SetGroupBanReq -> {
                frameBuilder.setGroupBanReq = apiReq;frameBuilder.frameType = FrameType.TSetGroupBanReq
            }
            is SetGroupAnonymousBanReq -> {
                frameBuilder.setGroupAnonymousBanReq = apiReq;frameBuilder.frameType =
                    FrameType.TSetGroupAnonymousBanReq
            }
            is SetGroupWholeBanReq -> {
                frameBuilder.setGroupWholeBanReq = apiReq;frameBuilder.frameType = FrameType.TSetGroupWholeBanReq
            }
            is SetGroupAdminReq -> {
                frameBuilder.setGroupAdminReq = apiReq;frameBuilder.frameType = FrameType.TSetGroupAdminReq
            }
            is SetGroupAnonymousReq -> {
                frameBuilder.setGroupAnonymousReq = apiReq;frameBuilder.frameType = FrameType.TSetGroupAnonymousReq
            }
            is SetGroupCardReq -> {
                frameBuilder.setGroupCardReq = apiReq;frameBuilder.frameType = FrameType.TSetGroupCardReq
            }
            is SetGroupNameReq -> {
                frameBuilder.setGroupNameReq = apiReq;frameBuilder.frameType = FrameType.TSetGroupNameReq
            }
            is SetGroupLeaveReq -> {
                frameBuilder.setGroupLeaveReq = apiReq;frameBuilder.frameType = FrameType.TSetGroupLeaveReq
            }
            is SetGroupSpecialTitleReq -> {
                frameBuilder.setGroupSpecialTitleReq = apiReq;frameBuilder.frameType =
                    FrameType.TSetGroupSpecialTitleReq
            }
            is SetFriendAddRequestReq -> {
                frameBuilder.setFriendAddRequestReq = apiReq;frameBuilder.frameType = FrameType.TSetFriendAddRequestReq
            }
            is SetGroupAddRequestReq -> {
                frameBuilder.setGroupAddRequestReq = apiReq;frameBuilder.frameType = FrameType.TSetGroupAddRequestReq
            }
            is GetLoginInfoReq -> {
                frameBuilder.getLoginInfoReq = apiReq;frameBuilder.frameType = FrameType.TGetLoginInfoReq
            }
            is GetStrangerInfoReq -> {
                frameBuilder.getStrangerInfoReq = apiReq;frameBuilder.frameType = FrameType.TGetStrangerInfoReq
            }
            is GetFriendListReq -> {
                frameBuilder.getFriendListReq = apiReq;frameBuilder.frameType = FrameType.TGetFriendListReq
            }
            is GetGroupInfoReq -> {
                frameBuilder.getGroupInfoReq = apiReq;frameBuilder.frameType = FrameType.TGetGroupInfoReq
            }
            is GetGroupListReq -> {
                frameBuilder.getGroupListReq = apiReq;frameBuilder.frameType = FrameType.TGetGroupListReq
            }
            is GetGroupMemberInfoReq -> {
                frameBuilder.getGroupMemberInfoReq = apiReq;frameBuilder.frameType = FrameType.TGetGroupMemberInfoReq
            }
            is GetGroupMemberListReq -> {
                frameBuilder.getGroupMemberListReq = apiReq;frameBuilder.frameType = FrameType.TGetGroupMemberListReq
            }
            is GetGroupHonorInfoReq -> {
                frameBuilder.getGroupHonorInfoReq = apiReq;frameBuilder.frameType = FrameType.TGetGroupHonorInfoReq
            }
            is GetCookiesReq -> {
                frameBuilder.getCookiesReq = apiReq;frameBuilder.frameType = FrameType.TGetCookiesReq
            }
            is GetCsrfTokenReq -> {
                frameBuilder.getCsrfTokenReq = apiReq;frameBuilder.frameType = FrameType.TGetCsrfTokenReq
            }
            is GetCredentialsReq -> {
                frameBuilder.getCredentialsReq = apiReq;frameBuilder.frameType = FrameType.TGetCredentialsReq
            }
            is GetRecordReq -> {
                frameBuilder.getRecordReq = apiReq;frameBuilder.frameType = FrameType.TGetRecordReq
            }
            is GetImageReq -> {
                frameBuilder.getImageReq = apiReq;frameBuilder.frameType = FrameType.TGetImageReq
            }
            is CanSendImageReq -> {
                frameBuilder.canSendImageReq = apiReq;frameBuilder.frameType = FrameType.TCanSendImageReq
            }
            is CanSendRecordReq -> {
                frameBuilder.canSendRecordReq = apiReq;frameBuilder.frameType = FrameType.TCanSendRecordReq
            }
            is GetStatusReq -> {
                frameBuilder.getStatusReq = apiReq;frameBuilder.frameType = FrameType.TGetStatusReq
            }
            is GetVersionInfoReq -> {
                frameBuilder.getVersionInfoReq = apiReq;frameBuilder.frameType = FrameType.TGetVersionInfoReq
            }
            is SetRestartReq -> {
                frameBuilder.setRestartReq = apiReq;frameBuilder.frameType = FrameType.TSetRestartReq
            }
            is CleanCacheReq -> {
                frameBuilder.cleanCacheReq = apiReq;frameBuilder.frameType = FrameType.TCleanCacheReq
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
            FrameType.TSendPrivateMsgResp -> respFrame.sendPrivateMsgResp
            FrameType.TSendGroupMsgResp -> respFrame.sendGroupMsgResp
            FrameType.TSendMsgResp -> respFrame.sendMsgResp
            FrameType.TDeleteMsgResp -> respFrame.deleteMsgResp
            FrameType.TGetMsgResp -> respFrame.getMsgResp
            FrameType.TGetForwardMsgResp -> respFrame.getForwardMsgResp
            FrameType.TSendLikeResp -> respFrame.sendLikeResp
            FrameType.TSetGroupKickResp -> respFrame.setGroupKickResp
            FrameType.TSetGroupBanResp -> respFrame.setGroupBanResp
            FrameType.TSetGroupAnonymousResp -> respFrame.setGroupAnonymousResp
            FrameType.TSetGroupWholeBanResp -> respFrame.setGroupWholeBanResp
            FrameType.TSetGroupAdminResp -> respFrame.setGroupAdminResp
            FrameType.TSetGroupAnonymousBanResp -> respFrame.setGroupAnonymousBanResp
            FrameType.TSetGroupCardResp -> respFrame.setGroupCardResp
            FrameType.TSetGroupNameResp -> respFrame.setGroupNameResp
            FrameType.TSetGroupLeaveResp -> respFrame.setGroupLeaveResp
            FrameType.TSetGroupSpecialTitleResp -> respFrame.setGroupSpecialTitleResp
            FrameType.TSetFriendAddRequestResp -> respFrame.setFriendAddRequestResp
            FrameType.TSetGroupAddRequestResp -> respFrame.setGroupAddRequestResp
            FrameType.TGetLoginInfoResp -> respFrame.getLoginInfoResp
            FrameType.TGetStrangerInfoResp -> respFrame.getStrangerInfoResp
            FrameType.TGetFriendListResp -> respFrame.getFriendListResp
            FrameType.TGetGroupInfoResp -> respFrame.getGroupInfoResp
            FrameType.TGetGroupListResp -> respFrame.getGroupListResp
            FrameType.TGetGroupMemberInfoResp -> respFrame.getGroupMemberInfoResp
            FrameType.TGetGroupMemberListResp -> respFrame.getGroupMemberListResp
            FrameType.TGetGroupHonorInfoResp -> respFrame.getGroupHonorInfoResp
            FrameType.TGetCookiesResp -> respFrame.getCookiesResp
            FrameType.TGetCsrfTokenResp -> respFrame.getCsrfTokenResp
            FrameType.TGetCredentialsResp -> respFrame.getCredentialsResp
            FrameType.TGetRecordResp -> respFrame.getRecordResp
            FrameType.TGetImageResp -> respFrame.getImageResp
            FrameType.TCanSendImageResp -> respFrame.canSendImageResp
            FrameType.TCanSendRecordResp -> respFrame.canSendRecordResp
            FrameType.TGetStatusResp -> respFrame.getStatusResp
            FrameType.TGetVersionInfoResp -> respFrame.getVersionInfoResp
            FrameType.TSetRestartResp -> respFrame.setRestartResp
            FrameType.TCleanCacheResp -> respFrame.cleanCacheResp
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

    fun setGroupAddRequest(session: WebSocketSession, botId: Long, apiReq: SetGroupAddRequestReq) =
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