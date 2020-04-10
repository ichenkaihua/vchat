package com.chenkh.vchat.server.coder

import com.chenkh.vchat.base.IDecoder
import com.chenkh.vchat.base.bean.MsgType.Client2Server
import com.chenkh.vchat.base.msg.ChatMsg
import com.chenkh.vchat.base.msg.ClientMsg
import com.chenkh.vchat.base.msg.both.UserStateChangeMsg
import com.chenkh.vchat.base.msg.client.AddFriendMsg
import com.chenkh.vchat.base.msg.client.LoginMsg
import com.chenkh.vchat.base.msg.client.QueryMsg
import com.chenkh.vchat.base.msg.client.RegisterMsg
import com.google.gson.Gson
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.nio.charset.Charset


class JsonDecoder : IDecoder {

    companion object{
        val log:Logger=LoggerFactory.getLogger(JsonDecoder.javaClass)
    }

    override fun support(bytes: ByteArray): Boolean {
        return true
    }

    override fun decode(bytes: ByteArray): Any {
        val msgSource = String(bytes, Charset.forName("utf-8")).trim { it <= ' ' }
        JsonDecoder.log.debug("正在解析客户端消息:{}", msgSource)
        var cmd: String? = null
        var body: String? = null
        if (msgSource.contains(" ")) {
            val split= msgSource.split(' ',ignoreCase = true,limit = 2)

           // val split = msgSource.split(" ", 2.toBoolean()).toTypedArray()
            cmd = split[0]
            body = split[1].trim { it <= ' ' }
        } else {
            cmd = msgSource
        }
        val msgType = Client2Server.valueOf(cmd)
        var clzz: Class<*>? = null
        when (msgType) {
            Client2Server.LOGIN -> clzz = LoginMsg::class.java
            Client2Server.CHAT -> clzz = ChatMsg::class.java
            Client2Server.REGISTER -> clzz = RegisterMsg::class.java
            Client2Server.USER_STATE_CHANGED -> clzz = UserStateChangeMsg::class.java
            Client2Server.QUERY -> clzz = QueryMsg::class.java
            Client2Server.ADD_FRIEND -> clzz = AddFriendMsg::class.java
        }
        val o = Gson().fromJson(body, clzz)
        JsonDecoder.log.debug("客户端消息解析成功:{}->{}", msgType, o)
        return ClientMsg(msgType, o)
    }
}