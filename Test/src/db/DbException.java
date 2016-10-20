package db;
/**
* @author tangjifu
* @date 2016年10月20日 下午8:46:49
*/
public class DbException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DbException() {
		super();
	}

	public DbException(String msg) {
		super(msg);
	}

	public DbException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
