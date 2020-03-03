package com.chenkh.vchat.server.util;

import java.sql.Connection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 连接池，提供对连接的管理，
 * 
 * @author Administrator
 * 
 */
public class ConnPool {
	// 用一个大小为20的队列装Connection，此队列为堵塞式，
	private int maxSize = 20;
	private BlockingQueue<Connection> conns = new ArrayBlockingQueue<Connection>(
			maxSize);
	private static ConnPool instance = new ConnPool();
	private boolean closeAll = false;

	/**
	 * 获得连接池对象实例
	 * 
	 * @return
	 */
	public static ConnPool getInstance() {

		return instance;
	}

	// 构造方法总初始化所有连接
	private ConnPool() {
		for (int i = 0; i < maxSize; i++) {
			Connection conn = DB.getConnnection();
			//System.out.println(i);
			conns.add(conn);
		}

	}

	/**
	 * 获得连接，从队列中取出第一个
	 * 
	 * @return
	 */
	public Connection getConn() {
		return conns.poll();
	}

	/**
	 * 返还连接，加入队列
	 * 
	 * @param conn
	 */
	public void backConn(Connection conn) {
		if(conn != null)
		conns.add(conn);
	}

	/**
	 * 得到最大连接数
	 * 
	 * @return 最大连接数
	 */
	public int getMaxSize() {
		return maxSize;
	}

}
