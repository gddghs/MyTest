package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDbManager {
	private static final Logger logger = LoggerFactory.getLogger(TestDbManager.class);
	private static TestDbManager manager = new TestDbManager(); 
	
	public static final String url = "jdbc:mysql://localhost:3306/test";  
    public static final String name = "com.mysql.jdbc.Driver";  
    public static final String user = "root";  
    public static final String password = "tjf";
	
    public static void main(String [] args){
    	DbConnectionPool.init();
    	//manager.testBatch();
    	manager.test();
    }
    
    private void test(){
    	for(int i=0; i<20; i++){
    		try{
    			Thread.sleep(2000);
    		}catch(Exception e){
    		}
    		String sql = "insert into test values (?, ?);";
    		Object[] params = new Object[]{i, "测试"+i};
    		int rowCount = manager.executeCommand(sql, params, null, true);
    		logger.info("受影响的行："+rowCount);
    	}
    }
    
    private void testBatch(){
    	String sql = "insert into test values (?, ?);";
    	List<Object[]> params = new ArrayList<>();
    	for(int i=0; i<5; i++){
    		params.add(new Object[]{i*10, "测试"});
    	}
    	int[] rowCount = manager.executeBatchCommand(sql, params, null, true);
    	logger.info("受影响的行："+Arrays.toString(rowCount));
    }
	
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

			stmt = prepareStatement(conn, sql, params);
			//stmt = conn.prepareStatement(sql);
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
	
	private int[] executeBatchCommand(String sql, Collection<Object[]> paramsList, Connection tranConn, boolean throwEx) {

		Connection conn = null;
		PreparedStatement stmt = null;
		int[] rowCounts = null;
		try {
			if (tranConn != null) {
				conn = tranConn;
			} else {
				conn = getConnection();
				//为了批量操作时，保持数据完整性，设置不自动提交
				conn.setAutoCommit(false);
			}

			stmt = prepareBatchStatement(conn, sql, paramsList);
			//stmt = conn.prepareStatement(sql);
			rowCounts = stmt.executeBatch();
			if (tranConn == null) {
				conn.commit();
			}
		} catch (Exception e) {
			try {
				//遇到异常时回滚
				conn.rollback();
			} catch (Exception e1) {
			}
			System.out.println(stmt);
			//logQueryError("executeBatchCommand", sql, paramsList, e, throwEx);
		} finally {
			if (tranConn == null) {
				releaseDbResource(null, stmt, conn);
			} else {
				releaseDbResource(null, stmt, null);
			}
		}
		return rowCounts;
	}
	
	protected Connection getConnection() {
		try {
			//return DriverManager.getConnection(url, user, password);
			return DbConnectionPool.getConnection();
		} catch (SQLException e) {
			logger.error("getConnection err. url="+url+", user="+user+", e=",e);
		}
		return null;
	}
	
	private PreparedStatement prepareStatement(Connection conn, String sql, Object[] params) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(sql);
		if (params != null) {
			setParams(stmt, params);
		}
		return stmt;
	}
	
	private PreparedStatement prepareBatchStatement(Connection conn, String sql, Collection<Object[]> paramsList)
			throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(sql);
		if (paramsList != null && paramsList.size() != 0) {
			for (Object[] params : paramsList) {
				setParams(stmt, params);
				stmt.addBatch();//
			}
		}
		return stmt;
	}
	
	private void setParams(PreparedStatement stmt, Object[] params) throws SQLException {
		Object o;
		for (int i = 0; i < params.length; i++) {
			o = params[i];
			if (o instanceof Integer) {
				stmt.setInt(i + 1, (Integer) o);
			} else if (o instanceof Short) {
				stmt.setShort(i + 1, (Short) o);
			} else if (o instanceof Long) {
				stmt.setLong(i + 1, (Long) o);
			} else if (o instanceof String) {
				stmt.setString(i + 1, (String) o);
			} else if (o instanceof Date) {
				stmt.setObject(i + 1, o);
			} else if (o instanceof Boolean) {
				stmt.setBoolean(i + 1, (Boolean) o);
			} else if (o instanceof byte[]) {
				stmt.setBytes(i + 1, (byte[]) o);
			} else if (o instanceof Double) {
				stmt.setDouble(i + 1, (Double) o);
			} else if (o instanceof Float) {
				stmt.setFloat(i + 1, (Float) o);
			} else if (o == null) {
				stmt.setNull(i + 1, java.sql.Types.OTHER);
			} else {
				throw new SQLException("Not allowed dataBase data type");
			}
		}
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
	
	
	
	/******************************打印日志****************************/
	
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
