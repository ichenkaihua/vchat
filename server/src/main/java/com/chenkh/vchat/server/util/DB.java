package com.chenkh.vchat.server.util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


/**
 * 数据库工具类，提供一些工具供数据库使用，比如Connecton,resultset,查询等
 * @author Administrator
 *
 */

public class DB {

	private static String MYSQL_DRIVER_URL;
	private static  String MYSQL_URL;
	private static String MYSQL_USER_NAME;
	private static String MYSQL_USER_PASS;

	static {
		try {
			Properties properties = new Properties();
			InputStream in= DB.class.getClassLoader().getResourceAsStream("config.properties");
			//FileInputStream in = new FileInputStream("config.properties");
			properties.load(in);
			MYSQL_DRIVER_URL=properties.getProperty("mysql_driver_url");
			MYSQL_URL=properties.getProperty("mysql_url");
			MYSQL_USER_NAME=properties.getProperty("mysql_user_name");
			MYSQL_USER_PASS=properties.getProperty("mysql_user_pass");
			in.close();
			Class.forName(MYSQL_DRIVER_URL);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnnection() {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(MYSQL_URL, MYSQL_USER_NAME, MYSQL_USER_PASS);
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return conn;
	}

	public static Statement getStatement(Connection conn) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return stmt;
	}

	public static PreparedStatement getPareparedStmt(Connection conn, String sql) {
		PreparedStatement pStmt = null;
		try {
			pStmt = conn.prepareStatement(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return pStmt;
	}
	
	public static PreparedStatement getAutoPareparedStmt(Connection conn, String sql) {
		PreparedStatement pStmt = null;
		try {
			pStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return pStmt;
	}
	
	
	
	
	

	public static ResultSet executeQuery(Statement stmt, String sql) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static ResultSet executeQuery(Connection conn, String sql) {
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = DB.executeQuery(stmt, sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public static void executeUpdate(Statement stmt, String sql) {
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public static void executeUpdate(Connection conn, String sql) {
		Statement stmt = DB.getStatement(conn);
		DB.executeUpdate(stmt, sql);
		DB.closeStatement(stmt);
	}

	public static void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeStatement(Statement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closePareparedStmt(PreparedStatement pstmt) {
		try {
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeResultSet(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
