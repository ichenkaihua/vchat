package com.chenkh.vchat.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.chenkh.vchat.base.msg.ContenMsg;
import com.chenkh.vchat.server.util.ConnPool;
import com.chenkh.vchat.server.util.DB;

public class Mysql_MsgDAO implements MsgDAO {
	private ConnPool conns = ConnPool.getInstance();

	@Override
	public List<ContenMsg> getOfflineMsg(int id) {
		Connection conn = conns.getConn();
		ResultSet rs = null;

		List<ContenMsg> msgs = new LinkedList<ContenMsg>();

		try {
			String sql = "select * from offlinemsg where toid = " + id;
			rs = DB.executeQuery(conn, sql);
			String sql2 = "delete from offlinemsg where toid =  " + id;

			while (rs.next()) {
				int fromid = rs.getInt("fromid");
				String strmsg = rs.getString("msg");
				Timestamp date = rs.getTimestamp("mdate");
				ContenMsg msg = new ContenMsg(fromid, strmsg, date);
				msgs.add(msg);
			}
			//得到所有信息后删除信息
			DB.executeUpdate(conn, sql2);

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			
			DB.closeResultSet(rs);
			conns.backConn(conn);
		}
		return msgs;
	}

	@Override
	public void addOfflineMsg(int fromId, int toId, String msg, Timestamp date) {
		Connection conn = conns.getConn();
		PreparedStatement pstmt = null;
		String sql = "insert into offlinemsg values(?,?,?,?)";
		pstmt = DB.getPareparedStmt(conn, sql);
		try {
			pstmt.setInt(1, fromId);
			pstmt.setInt(2, toId);
			pstmt.setString(3, msg);
			pstmt.setTimestamp(4, date);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			DB.closePareparedStmt(pstmt);
			conns.backConn(conn);
		}

	}

}
