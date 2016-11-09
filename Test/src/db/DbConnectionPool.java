package db;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
* @author tangjifu
* @date 2016年10月27日 下午8:02:00
*/
public class DbConnectionPool {
	//c3p0连接池
	private static ComboPooledDataSource pool = null;
	
	protected static void init(){
		pool = createConnectionPool();
		System.out.println("init db connection pool succ");
	}
	
	public static Connection getConnection() throws SQLException {
		return pool.getConnection();
	}
	
	private static ComboPooledDataSource createConnectionPool() {
		ComboPooledDataSource ds = new ComboPooledDataSource();

		try {
			ds.setDriverClass(DbConfig.driver);
		} catch (Exception ex) {
			String errorMsg = "Invalid jdbc driver class : " + DbConfig.driver;
			throw new DbException(errorMsg);
		}

		String connStr = String.format("jdbc:mysql://%s/%s?user=%s&password=%s&autoReconnect=true", DbConfig.ip,
				DbConfig.dbName, DbConfig.user, DbConfig.password);
		boolean batchSqlConfigOptimize = false;
		if (batchSqlConfigOptimize) {
			connStr += "&useServerPreparedStmts=false&rewriteBatchedStatements=true";
		}

		ds.setJdbcUrl(connStr);
		ds.setMinPoolSize(DbConfig.poolMin);
		ds.setMaxPoolSize(DbConfig.poolMax);
		ds.setCheckoutTimeout(DbConfig.checkoutTimeout);
		ds.setIdleConnectionTestPeriod(DbConfig.idleConnectionTestPeriod);
		ds.setMaxIdleTime(DbConfig.maxIdleTime);
		ds.setPreferredTestQuery(DbConfig.preferredTestQuery);
		ds.setTestConnectionOnCheckin(DbConfig.testConnectionOnCheckin);
		return ds;
	}
}


/**
 * 连接池配置 
 * 详情参考http://www.cnblogs.com/shqblogs/p/5582177.html
 */
class DbConfig {
	public static String driver = "com.mysql.jdbc.Driver";
	public static String ip = "127.0.0.1";
	public static String dbName = "test";
	public static String user = "root";
	public static String password = "tjf";
	public static int poolMin = 5; //连接池中保留的最小连接数
	public static int poolMax = 100; //连接池中保留的最大连接数
	/*当连接池用完时客户端调用getConnection()后等待获取新连接的时间，超时后将抛出
	SQLException,如设为0则无限期等待。单位毫秒。Default: 0 */
	public static int checkoutTimeout = 1000;
	public static int idleConnectionTestPeriod = 60;//每60秒检查所有连接池中的空闲连接。Default: 0 
	public static int maxIdleTime = 1800;//最大空闲时间,1800秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0
	/*定义所有连接测试都执行的测试语句。在使用连接测试的情况下这个一显著提高测试速度。注意：
	测试的表必须在初始数据源的时候就存在。Default: null*/
	public static String preferredTestQuery = null;
	/*因性能消耗大请只在需要的时候使用它。如果设为true那么在每个connection提交的
	时候都将校验其有效性。建议使用idleConnectionTestPeriod或automaticTestTable
	等方法来提升连接测试的性能。Default: false */
	public static boolean testConnectionOnCheckout = false;
	//如果设为true那么在取得连接的同时将校验连接的有效性。Default: false
	public static boolean testConnectionOnCheckin = false;
}