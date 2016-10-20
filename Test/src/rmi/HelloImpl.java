package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author tangjifu
 * @date 2016年5月11日 上午11:53:18 实现类继承UnicastRemoteObject时，lookup出来的对象类型是$Proxy0，
 *       而不继承UnicastRemoteObject时类，对象类型是com.guojje.Hello。
 *       我们把继承UnicastRemoteObject类的对象叫做远程对象，我们lookup出来的对象，只是该远程对象的存根(Stub)对象,
 *       而远程对象永远在远方。客户端每一次的方法调用，最后都是仅有的那一个远程对象的方法调用。
 *       没有继UnicastRemoteObject类的对象，同样可以bind到Registry，但lookup出来了对象却是远程对象
 *       经过序列化，然后到客户端反序列化出来的新的对象，后续的方法调用与远程对象再无关系。
 */
public class HelloImpl extends UnicastRemoteObject implements IHello {

	private static final long serialVersionUID = -2585742137155364394L;

	public HelloImpl() throws RemoteException {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String hello() {
		System.out.println("hello world!!!");
		return reply();
	}

	@Override
	public String reply() {
		return "hahaha~";
	}

}
