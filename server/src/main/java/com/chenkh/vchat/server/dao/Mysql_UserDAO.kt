package com.chenkh.vchat.server.dao

import com.chenkh.vchat.base.bean.Friend
import com.chenkh.vchat.base.bean.Group
import com.chenkh.vchat.base.bean.Stranger
import com.chenkh.vchat.base.bean.User
import com.chenkh.vchat.server.dao.DB.closePareparedStmt
import com.chenkh.vchat.server.dao.DB.closeResultSet
import com.chenkh.vchat.server.dao.DB.executeQuery
import com.chenkh.vchat.server.dao.DB.getAutoPareparedStmt
import com.chenkh.vchat.server.dao.DB.getPareparedStmt
import com.chenkh.vchat.server.exception.PasswordNotCorrect
import com.chenkh.vchat.server.exception.RegiserUserFailedException
import com.chenkh.vchat.server.exception.UserNotFoundException
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

class Mysql_UserDAO : UserDAO {
    private val conns = ConnPool.instance

    @Throws(UserNotFoundException::class, PasswordNotCorrect::class)
    override fun getUser(id: Int, password: String?): User? {
        var conn: Connection? = null
        var rs: ResultSet? = null
        var rs2: ResultSet? = null
        var rsGroup: ResultSet? = null
        var rsFriend: ResultSet? = null
        var sql = ""
        var u: User? = null
        try {
            // 得到Connection
            conn = conns.conn
            // 查找指定ID的用户
            sql = "select * from user where id = $id"
            rs = executeQuery(conn!!, sql)
            if (!rs!!.next()) {
                // 如果没找到，抛出用户没找到异常
                throw UserNotFoundException("用户名没有找到")
            } else {
                // 找到了，继续判断密码时候正确
                sql += " and password = '$password'"
                // System.out.println("sql:" + sql);
                rs2 = executeQuery(conn, sql)
                if (!rs2!!.next()) {
                    // 密码不正确，抛出异常
                    throw PasswordNotCorrect("密码不正确")
                } else {
                    // 分别给用户赋值
                    u = User()
                    val friendCount = 0
                    u.id = rs2.getInt("id")
                    u.userName = rs2.getString("username")
                    u.password = rs2.getString("password")
                    u.phone = rs2.getString("phone")
                    u.addr = rs2.getString("addr")
                    u.sex = rs2.getString("sex")
                    u.sign = rs2.getString("sign")
                    u.setrDate(rs2.getTimestamp("rdate"))
                    val groups: MutableList<Group> = ArrayList()
                    val sql2 = ("select * from usergroup where userid = "
                            + id)
                    rsGroup = executeQuery(conn, sql2)
                    // 查找此用户的所有分组，并添加到用户中
                    while (rsGroup!!.next()) {
                        val g = Group()
                        g.userId = id
                        g.name = rsGroup.getString("groupname")
                        g.friendCount = rsGroup.getInt("friendcount")
                        val groupid = rsGroup.getInt("groupid")
                        g.groupId = groupid
                        val sql3 = ("select * from friend join user on(friendid=user.id) where groupid ="
                                + groupid)
                        // System.out.println("sql3:" + sql3);
                        rsFriend = executeQuery(conn, sql3)
                        val friends: MutableList<Friend> = ArrayList()
                        // 查找在组下的朋友，并添加到组中
                        while (rsFriend!!.next()) {
                            val f = Friend()
                            f.id = rsFriend.getInt("id")
                            f.usernmae = rsFriend.getString("username")
                            f.phone = rsFriend.getString("phone")
                            f.setrDate(rsFriend.getTimestamp("rdate"))
                            f.addr = rsFriend.getString("addr")
                            f.sex = rsFriend.getString("sex")
                            f.sign = rsFriend.getString("sign")
                            f.noteName = rsFriend.getString("notename")
                            friends.add(f)
                        }
                        g.friends = friends
                        groups.add(g)
                    }
                    u.groups = groups
                    u.friendCount = friendCount
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResultSet(rsFriend)
            closeResultSet(rsGroup)
            closeResultSet(rs2)
            closeResultSet(rs)
            conns.backConn(conn)
        }
        return u
    }

    override fun addFriend(myId: Int, friendId: Int, groupid: Int, noteName: String?): Friend? {
        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        var f: Friend? = null
        var rs: ResultSet? = null
        return try {
            conn = conns.conn
            val sql = "insert into friend values(?,?,?)"
            pstmt = getPareparedStmt(conn!!, sql)
            pstmt!!.setInt(1, groupid)
            pstmt.setInt(2, friendId)
            pstmt.setString(3, noteName)
            pstmt.execute()
            val sql2 = ("select * from friend join user on(friendid=user.id) where groupid ="
                    + groupid + " and friendid = " + friendId)
            // System.out.println("sql2:" + sql2);
            rs = executeQuery(conn, sql2)
            if (rs!!.next()) {
                // System.out.println("找到好友--server");
                f = Friend()
                f.id = rs.getInt("id")
                f.usernmae = rs.getString("username")
                f.phone = rs.getString("phone")
                f.setrDate(rs.getTimestamp("rdate"))
                f.addr = rs.getString("addr")
                f.sex = rs.getString("sex")
                f.sign = rs.getString("sign")
                f.noteName = rs.getString("notename")
            }
            f
        } catch (e: SQLException) {
            println("添加失败!")
            e.printStackTrace()
            f
        } finally {
            closeResultSet(rs)
            closePareparedStmt(pstmt)
            conns.backConn(conn)
        }
    }

    override fun deleteFriend(myId: Int, friendId: Int, groupid: Int): Boolean {
        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        conn = conns.conn
        return if (!isContainerGroup(conn, myId, groupid)) {
            false
        } else try {
            val sql = "delete from friend where friendid = ? and groupid = ?"
            pstmt = getPareparedStmt(conn!!, sql)
            pstmt!!.setInt(1, friendId)
            pstmt.setInt(2, groupid)
            pstmt.execute()
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        } finally {
            closePareparedStmt(pstmt)
            conns.backConn(conn)
        }
    }

    private fun isContainerGroup(conn: Connection?, userid: Int, groupId: Int): Boolean {
        val sql = ("select * from usergroup where userid = " + userid
                + " and groupid =" + groupId)
        val rs = executeQuery(conn!!, sql)
        try {
            if (rs!!.next()) {
                return true
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return false
    }

    override fun modifyFriendNoteName(myId: Int, friendId: Int,
                                      noteName: String?, groupId: Int): Boolean {
        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        conn = conns.conn
        if (!isContainerGroup(conn, myId, groupId)) {
            return false
        }
        try {
            val sql = "update friend set notename= ? where groupid = ? and friendid = ?"
            pstmt = getPareparedStmt(conn!!, sql)
            pstmt!!.setString(1, noteName)
            pstmt.setInt(2, groupId)
            pstmt.setInt(3, friendId)
            pstmt.execute()
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        } finally {
            closePareparedStmt(pstmt)
            conns.backConn(conn)
        }
        return true
    }

    override fun modifyUser(u: User?): Boolean {
        //
        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        try {
            val sql = ("update user set username=?,phone=?,sex=?, addr=? where id = "
                    + u!!.id)
            conn = conns.conn
            pstmt = getPareparedStmt(conn!!, sql)
            pstmt!!.setString(1, u.userName)
            pstmt.setString(2, u.phone)
            pstmt.setString(3, u.sex)
            pstmt.setString(4, u.addr)
            pstmt.execute()
        } catch (e: SQLException) {
            e.printStackTrace()
            return false
        } finally {
            closePareparedStmt(pstmt)
            conns.backConn(conn)
        }
        return true
    }

    @Throws(RegiserUserFailedException::class)
    override fun addUser(u: User?): Int {
        var conn: Connection? = null
        var pstmt: PreparedStatement? = null
        var id = -1
        try {
            conn = conns.conn
            conn!!.autoCommit = false
            val sql = "insert into user values(null,?,?,?,?,?,?,now())"
            pstmt = getAutoPareparedStmt(conn, sql)
            pstmt!!.setString(1, u!!.userName)
            pstmt.setString(2, u.sign)
            pstmt.setString(3, u.password)
            pstmt.setString(4, u.phone)
            pstmt.setString(5, u.sex)
            pstmt.setString(6, u.addr)
            pstmt.execute()
            conn.commit()
            val rs = pstmt.generatedKeys
            if (rs != null && rs.next()) {
                id = rs.getInt(1)
            }
            conn.autoCommit = true
        } catch (e: SQLException) {
            e.printStackTrace()
            throw RegiserUserFailedException("用户注册失败")
        }
        return id
    }

    override fun queryUser(toId: Int, keyword: String?): List<Stranger?>? {
        var conn: Connection? = null
        val strangers: MutableList<Stranger?> = ArrayList()
        var rs: ResultSet? = null
        try {
            conn = conns.conn
            val sql = ("select * from user where id like '%" + keyword
                    + "%' or username like '%" + keyword + "%' and id <> "
                    + toId)
            rs = executeQuery(conn!!, sql)
            while (rs!!.next()) {
                val u = Stranger()
                u.id = rs.getInt("id")
                u.setUsernmae(rs.getString("username"))
                u.phone = rs.getString("phone")
                u.addr = rs.getString("addr")
                u.sex = rs.getString("sex")
                u.sign = rs.getString("sign")
                u.setrDate(rs.getTimestamp("rdate"))
                strangers.add(u)
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResultSet(rs)
            conns.backConn(conn)
        }
        return strangers
    }

    override fun getStranger(id: Int): Stranger? {
        var conn: Connection? = null
        var u: Stranger? = null
        var rs: ResultSet? = null
        try {
            conn = conns.conn
            val sql = "select * from user where id =$id"
            rs = executeQuery(conn!!, sql)
            if (rs!!.next()) {
                u = Stranger()
                u.id = rs.getInt("id")
                u.setUsernmae(rs.getString("username"))
                u.phone = rs.getString("phone")
                u.addr = rs.getString("addr")
                u.sex = rs.getString("sex")
                u.sign = rs.getString("sign")
                u.setrDate(rs.getTimestamp("rdate"))
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            closeResultSet(rs)
            conns.backConn(conn)
        }
        return u
    }
}