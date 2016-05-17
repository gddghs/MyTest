package hotswap;
/**
* @author tangjifu
* @version 2015年12月2日
* 自定义加载器
*/
public class DynamicClassLoader extends ClassLoader{

	protected Class<?> findClass(byte[] b) throws ClassNotFoundException {
		//将一个 byte 数组转换为 Class 类的实例
		return defineClass(null, b, 0, b.length);
	}
}
