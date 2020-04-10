package com.chenkh.vchat.server.dao

import com.chenkh.vchat.base.msg.ContenMsg
import java.sql.Timestamp

interface MsgDAO {
    fun getOfflineMsg(id: Int): List<ContenMsg>
    fun addOfflineMsg(fromId: Int, toId: Int, msg: String?, date: Timestamp?)
}