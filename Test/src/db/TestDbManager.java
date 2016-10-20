package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDbManager {
	private static final Logger logger = LoggerFactory.getLogger(TestDbManager.class);
	
	public static final String url = "jdbc:mysql://localhost:3306/test";  
    public static final String name = "com.mysql.jdbc.Driver";  
    public static final String user = "root";  
    public static final String password = "tjf";
	
	
	private int executeCommand(String sql, Object[] params, Connection tranConn, boolean throwEx) {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rowCount = -1;
		try {
			if (tranConn != null) {
				conn = tranConn;
			} else {
				conn = getConnection();
			}

			//stmt = this.prepareStatement(conn, cmd, params);
			stmt = conn.prepareStatement(sql);
			rowCount = stmt.executeUpdate();
		} catch (Exception e) {
			logQueryError("executeCommand", sql, params, e, throwEx);
		} finally {
			if (tranConn == null) {
				releaseDbResource(null, stmt, conn);
			} else {
				releaseDbResource(null, stmt, null);
			}
		}
		return rowCount;
	}
	
	protected Connection getConnection() {
		//return this.dbGroup.getConnection(false);
		try {
			return DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			logger.error("getConnection err. url="+url+", user="+user+", e=",e);
		}
		return null;
	}
	
	protected static void releaseDbResource(ResultSet rs, Statement stmt, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException ex) {
			logError(ex.getMessage());
		}

		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException ex) {
			logError(ex.getMessage());
		}

		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException ex) {
			logError(ex.getMessage());
		}
	}
	
	
	
	
	
	private static void logError(String msg) {
		logger.error("releaseDbResource err. e=", msg);
	}

	private static void logError(String msg, Throwable t) {
		try {
			logger.error("releaseDbResource err. e=", msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void logQueryError(String method, String sql, Object[] params, Exception e, boolean reThrow) {
		String msg = new StringBuilder("[DbManager]").append(method).append(" error [").
				append(sql).append(Arrays.toString(params)).append("]").toString();
		if (reThrow) {
			throw new DbException(msg, e);
		} else {
			logError(msg, e);
		}
	}

}
