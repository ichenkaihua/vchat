package com.chenkh.vchat.server.dao

import com.chenkh.vchat.base.msg.ContenMsg
import com.chenkh.vchat.server.dao.DB.closePareparedStmt
import com.chenkh.vchat.server.dao.DB.closeResultSet
import com.chenkh.vchat.server.dao.DB.executeQuery
import com.chenkh.vchat.server.dao.DB.executeUpdate
import com.chenkh.vchat.server.dao.DB.getPareparedStmt
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Timestamp
import java.util.*

class Mysql_MsgDAO : MsgDAO {
    private val conns = ConnPool.instance
    override fun getOfflineMsg(id: Int): List<ContenMsg> {
        val conn = conns.conn
        var rs: ResultSet? = null
        val msgs: MutableList<ContenMsg> = LinkedList()
        try {
            val sql = "select * from offlinemsg where toid = $id"
            rs = executeQuery(conn!!, sql)
            val sql2 = "delete from offlinemsg where toid =  $id"
            while (rs!!.next()) {
                val fromid = rs.getInt("fromid")
                val strmsg = rs.getString("msg")
                val date = rs.getTimestamp("mdate")
                val msg = ContenMsg(fromid, strmsg, date)
                msgs.add(msg)
            }
            //得到所有信息后删除信息
            executeUpdate(conn, sql2)
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResultSet(rs)
            conns.backConn(conn)
        }
        return msgs
    }

    override fun addOfflineMsg(fromId: Int, toId: Int, msg: String?, date: Timestamp?) {
        val conn = conns.conn
        var pstmt: PreparedStatement? = null
        val sql = "insert into offlinemsg values(?,?,?,?)"
        pstmt = getPareparedStmt(conn!!, sql)
        try {
            pstmt!!.setInt(1, fromId)
            pstmt.setInt(2, toId)
            pstmt.setString(3, msg)
            pstmt.setTimestamp(4, date)
            pstmt.executeUpdate()
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closePareparedStmt(pstmt)
            conns.backConn(conn)
        }
    }
}