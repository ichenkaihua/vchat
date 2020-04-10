package com.chenkh.vchat.server.dao

import java.io.FileNotFoundException
import java.io.IOException
import java.sql.*
import java.util.*

/**
 * 数据库工具类，提供一些工具供数据库使用，比如Connecton,resultset,查询等
 * @author Administrator
 */
object DB {
    private var MYSQL_DRIVER_URL: String? = null
    private var MYSQL_URL: String? = null
    private var MYSQL_USER_NAME: String? = null
    private var MYSQL_USER_PASS: String? = null
    @JvmStatic
    val connnection: Connection?
        get() {
            var conn: Connection? = null
            try {
                conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER_NAME, MYSQL_USER_PASS)
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            return conn
        }

    fun getStatement(conn: Connection): Statement? {
        var stmt: Statement? = null
        try {
            stmt = conn.createStatement()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return stmt
    }

    @JvmStatic
    fun getPareparedStmt(conn: Connection, sql: String?): PreparedStatement? {
        var pStmt: PreparedStatement? = null
        try {
            pStmt = conn.prepareStatement(sql)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return pStmt
    }

    @JvmStatic
    fun getAutoPareparedStmt(conn: Connection, sql: String?): PreparedStatement? {
        var pStmt: PreparedStatement? = null
        try {
            pStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return pStmt
    }

    fun executeQuery(stmt: Statement?, sql: String?): ResultSet? {
        var rs: ResultSet? = null
        try {
            rs = stmt!!.executeQuery(sql)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return rs
    }

    @JvmStatic
    fun executeQuery(conn: Connection, sql: String?): ResultSet? {
        var rs: ResultSet? = null
        var stmt: Statement? = null
        try {
            stmt = conn.createStatement()
            rs = executeQuery(stmt, sql)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return rs
    }

    fun executeUpdate(stmt: Statement?, sql: String?) {
        try {
            stmt!!.executeUpdate(sql)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun executeUpdate(conn: Connection, sql: String?) {
        val stmt = getStatement(conn)
        executeUpdate(stmt, sql)
        closeStatement(stmt)
    }

    fun closeConnection(conn: Connection?) {
        var conn = conn
        try {
            if (conn != null) {
                conn.close()
                conn = null
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    fun closeStatement(stmt: Statement?) {
        var stmt = stmt
        try {
            if (stmt != null) {
                stmt.close()
                stmt = null
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun closePareparedStmt(pstmt: PreparedStatement?) {
        var pstmt = pstmt
        try {
            if (pstmt != null) {
                pstmt.close()
                pstmt = null
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun closeResultSet(rs: ResultSet?) {
        var rs = rs
        try {
            if (rs != null) {
                rs.close()
                rs = null
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    init {
        try {
            val properties = Properties()
            val `in` = DB::class.java.classLoader.getResourceAsStream("config.properties")
            //FileInputStream in = new FileInputStream("config.properties");
            //com.chenkh.vchat.server.dao.properties.load(com.chenkh.vchat.server.dao.`in`)
            properties.load(`in`)
            MYSQL_DRIVER_URL = properties.getProperty("mysql_driver_url")
            MYSQL_URL = properties.getProperty("mysql_url")
            MYSQL_USER_NAME = properties.getProperty("mysql_user_name")
            MYSQL_USER_PASS = properties.getProperty("mysql_user_pass")
            println("driver:$MYSQL_DRIVER_URL")
            println("url:$MYSQL_URL")
            println("userName:$MYSQL_USER_NAME")
            println("password:$MYSQL_USER_PASS")

            `in`.close()
            Class.forName(MYSQL_DRIVER_URL)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}