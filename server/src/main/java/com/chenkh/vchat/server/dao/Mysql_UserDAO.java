package com.chenkh.vchat.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.chenkh.vchat.server.exception.PasswordNotCorrect;
import com.chenkh.vchat.server.exception.RegiserUserFailedException;
import com.chenkh.vchat.server.exception.UserNotFoundException;
import com.chenkh.vchat.server.util.ConnPool;
import com.chenkh.vchat.server.util.DB;
import com.chenkh.vchat.base.bean.Friend;
import com.chenkh.vchat.base.bean.Group;
import com.chenkh.vchat.base.bean.Stranger;
import com.chenkh.vchat.base.bean.User;

public class Mysql_UserDAO implements UserDAO {
	private ConnPool conns = ConnPool.getInstance();

	@Override
	public User getUser(int id, String password) throws UserNotFoundException,
			PasswordNotCorrect {
		Connection conn = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rsGroup = null;
		ResultSet rsFriend = null;
		String sql = "";
		User u = null;
		try {
			// 得到Connection
			conn = conns.getConn();
			// 查找指定ID的用户
			sql = "select * from user where id = " + id;
			rs = DB.executeQuery(conn, sql);
			if (!rs.next()) {
				// 如果没找到，抛出用户没找到异常
				throw new UserNotFoundException("用户名没有找到");
			} else {
				// 找到了，继续判断密码时候正确
				sql += " and password = '" + password + "'";
				// System.out.println("sql:" + sql);
				rs2 = DB.executeQuery(conn, sql);
				if (!rs2.next()) {
					// 密码不正确，抛出异常
					throw new PasswordNotCorrect("密码不正确");
				} else {
					// 分别给用户赋值
					u = new User();
					int friendCount = 0;

					u.setId(rs2.getInt("id"));
					u.setUserName(rs2.getString("username"));
					u.setPassword(rs2.getString("password"));
					u.setPhone(rs2.getString("phone"));
					u.setAddr(rs2.getString("addr"));
					u.setSex(rs2.getString("sex"));
					u.setSign(rs2.getString("sign"));
					u.setrDate(rs2.getTimestamp("rdate"));
					List<Group> groups = new ArrayList<Group>();
					String sql2 = "select * from usergroup where userid = "
							+ id;
					rsGroup = DB.executeQuery(conn, sql2);
					// 查找此用户的所有分组，并添加到用户中
					while (rsGroup.next()) {
						Group g = new Group();

						g.setUserId(id);
						g.setName(rsGroup.getString("groupname"));
						g.setFriendCount(rsGroup.getInt("friendcount"));
						int groupid = rsGroup.getInt("groupid");
						g.setGroupId(groupid);

						String sql3 = "select * from friend join user on(friendid=user.id) where groupid ="
								+ groupid;
						// System.out.println("sql3:" + sql3);
						rsFriend = DB.executeQuery(conn, sql3);
						List<Friend> friends = new ArrayList<Friend>();
						// 查找在组下的朋友，并添加到组中
						while (rsFriend.next()) {
							Friend f = new Friend();
							f.setId(rsFriend.getInt("id"));
							f.setUsernmae(rsFriend.getString("username"));
							f.setPhone(rsFriend.getString("phone"));
							f.setrDate(rsFriend.getTimestamp("rdate"));
							f.setAddr(rsFriend.getString("addr"));
							f.setSex(rsFriend.getString("sex"));
							f.setSign(rsFriend.getString("sign"));
							f.setGroup(g);

							f.setNoteName(rsFriend.getString("notename"));
							friends.add(f);

						}
						g.setFriends(friends);

						groups.add(g);

					}
					u.setGroups(groups);
					u.setFriendCount(friendCount);

				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rsFriend);
			DB.closeResultSet(rsGroup);
			DB.closeResultSet(rs2);
			DB.closeResultSet(rs);
			conns.backConn(conn);
		}

		return u;
	}

	@Override
	public Friend addFriend(int myId, int friendId, int groupid, String noteName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Friend f = null;
		ResultSet rs = null;

		try {
			conn = conns.getConn();
			String sql = "insert into friend values(?,?,?)";
			pstmt = DB.getPareparedStmt(conn, sql);
			pstmt.setInt(1, groupid);
			pstmt.setInt(2, friendId);
			pstmt.setString(3, noteName);
			pstmt.execute();

			String sql2 = "select * from friend join user on(friendid=user.id) where groupid ="
					+ groupid + " and friendid = " + friendId;
			// System.out.println("sql2:" + sql2);
			rs = DB.executeQuery(conn, sql2);
			if (rs.next()) {
				// System.out.println("找到好友--server");
				f = new Friend();
				f.setId(rs.getInt("id"));
				f.setUsernmae(rs.getString("username"));
				f.setPhone(rs.getString("phone"));
				f.setrDate(rs.getTimestamp("rdate"));
				f.setAddr(rs.getString("addr"));
				f.setSex(rs.getString("sex"));
				f.setSign(rs.getString("sign"));
				f.setNoteName(rs.getString("notename"));

			}

			return f;

		} catch (SQLException e) {
			System.out.println("添加失败!");
			e.printStackTrace();
			return f;
		} finally {
			DB.closeResultSet(rs);
			DB.closePareparedStmt(pstmt);
			conns.backConn(conn);
		}

	}

	@Override
	public boolean deleteFriend(int myId, int friendId, int groupid) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = conns.getConn();

		if (!this.isContainerGroup(conn, myId, groupid)) {

			return false;

		}

		try {

			String sql = "delete from friend where friendid = ? and groupid = ?";
			pstmt = DB.getPareparedStmt(conn, sql);
			pstmt.setInt(1, friendId);
			pstmt.setInt(2, groupid);
			pstmt.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DB.closePareparedStmt(pstmt);
			conns.backConn(conn);
		}

	}

	private boolean isContainerGroup(Connection conn, int userid, int groupId) {
		String sql = "select * from usergroup where userid = " + userid
				+ " and groupid =" + groupId;
		ResultSet rs = DB.executeQuery(conn, sql);
		try {
			if (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public boolean modifyFriendNoteName(int myId, int friendId,
			String noteName, int groupId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = conns.getConn();
		if (!this.isContainerGroup(conn, myId, groupId)) {
			return false;
		}

		try {
			String sql = "update friend set notename= ? where groupid = ? and friendid = ?";
			pstmt = DB.getPareparedStmt(conn, sql);
			pstmt.setString(1, noteName);
			pstmt.setInt(2, groupId);
			pstmt.setInt(3, friendId);
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DB.closePareparedStmt(pstmt);
			conns.backConn(conn);
		}
		return true;
	}

	@Override
	public boolean modifyUser(User u) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String sql = "update user set username=?,phone=?,sex=?, addr=? where id = "
					+ u.getId();
			conn = conns.getConn();
			pstmt = DB.getPareparedStmt(conn, sql);
			pstmt.setString(1, u.getUserName());
			pstmt.setString(2, u.getPhone());
			pstmt.setString(3, u.getSex());
			pstmt.setString(4, u.getAddr());
			pstmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DB.closePareparedStmt(pstmt);
			conns.backConn(conn);
		}

		return true;
	}

	@Override
	public int addUser(User u) throws RegiserUserFailedException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		int id = -1;
		try {
			conn = conns.getConn();
			conn.setAutoCommit(false);
			String sql = "insert into user values(null,?,?,?,?,?,?,now())";
			pstmt = DB.getAutoPareparedStmt(conn, sql);
			pstmt.setString(1, u.getUserName());
			pstmt.setString(2, u.getSign());

			pstmt.setString(3, u.getPassword());
			pstmt.setString(4, u.getPhone());
			pstmt.setString(5, u.getSex());
			pstmt.setString(6, u.getAddr());
			pstmt.execute();
			conn.commit();
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				id = rs.getInt(1);
			}

			conn.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RegiserUserFailedException("用户注册失败");

		}

		return id;
	}

	@Override
	public List<Stranger> queryUser(int toId, String keyword) {
		Connection conn = null;
		List<Stranger> strangers = new ArrayList<Stranger>();

		ResultSet rs = null;
		try {

			conn = conns.getConn();
			String sql = "select * from user where id like '%" + keyword
					+ "%' or username like '%" + keyword + "%' and id <> "
					+ toId;
			rs = DB.executeQuery(conn, sql);
			while (rs.next()) {
				Stranger u = new Stranger();
				u.setId(rs.getInt("id"));
				u.setUsernmae(rs.getString("username"));

				u.setPhone(rs.getString("phone"));
				u.setAddr(rs.getString("addr"));
				u.setSex(rs.getString("sex"));
				u.setSign(rs.getString("sign"));
				u.setrDate(rs.getTimestamp("rdate"));
				strangers.add(u);

			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			DB.closeResultSet(rs);
			conns.backConn(conn);
		}

		return strangers;
	}

	@Override
	public Stranger getStranger(int id) {

		Connection conn = null;
		Stranger u = null;

		ResultSet rs = null;
		try {

			conn = conns.getConn();
			String sql = "select * from user where id =" + id;
			rs = DB.executeQuery(conn, sql);
			if (rs.next()) {
				u = new Stranger();
				u.setId(rs.getInt("id"));
				u.setUsernmae(rs.getString("username"));
				u.setPhone(rs.getString("phone"));
				u.setAddr(rs.getString("addr"));
				u.setSex(rs.getString("sex"));
				u.setSign(rs.getString("sign"));
				u.setrDate(rs.getTimestamp("rdate"));
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {
			DB.closeResultSet(rs);
			conns.backConn(conn);
		}

		return u;
	}

}
