package com.chenkh.vchat.server.dao

import com.chenkh.vchat.server.dao.DB.connnection
import java.sql.Connection
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue

/**
 * 连接池，提供对连接的管理，
 *
 * @author Administrator
 */
class ConnPool private constructor() {
    /**
     * 得到最大连接数
     *
     * @return 最大连接数
     */
    // 用一个大小为20的队列装Connection，此队列为堵塞式，
    val maxSize = 20
    private val conns: BlockingQueue<Connection?> = ArrayBlockingQueue(
            maxSize)
    private val closeAll = false
    /**
     * 获得连接，从队列中取出第一个
     *
     * @return
     */
    val conn: Connection?
        get() = conns.poll()

    /**
     * 返还连接，加入队列
     *
     * @param conn
     */
    fun backConn(conn: Connection?) {
        if (conn != null) conns.add(conn)
    }

    companion object {
        /**
         * 获得连接池对象实例
         *
         * @return
         */
		@JvmStatic
		val instance = ConnPool()
    }

    // 构造方法总初始化所有连接
    init {
        for (i in 0 until maxSize) {
            val conn = connnection
            //System.out.println(i);
            conns.add(conn)
        }
    }
}