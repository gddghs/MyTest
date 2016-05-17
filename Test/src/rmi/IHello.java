package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
* @author tangjifu
* @date 2016年5月11日 上午11:47:55
* 
*/
public interface IHello extends Remote {

	//每个方法都要throws Exception
	/**
	 * 问候
	 */
	public String hello() throws RemoteException;
	
	/**
	 * 回复
	 */
	public String reply() throws RemoteException;
	
}
