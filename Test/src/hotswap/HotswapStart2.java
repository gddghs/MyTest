package hotswap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * 参考http://blog.csdn.net/z69183787/article/details/29234905
 * @author Administrator
 */
public class HotswapStart2 {
	public static boolean isHotswap = false;//是否执行过热更新
	
	private static Map<Integer, String> classStrMap = new HashMap<Integer, String>();
	private static Map<Integer, Object> classObjMap = new HashMap<Integer, Object>();
	
	static{
		classStrMap.put(1, "hotswap.TestBeanImpl");
		classStrMap.put(2, "hotswap.TestBeanImpl2");
		
		classObjMap.put(1, TestBeanImpl.getInstance());
		classObjMap.put(2, TestBeanImpl2.getInstance());
	}
	
	public static Object getNewInstance(int clsKey){
		return classObjMap.get(clsKey);
	}

	public static void main(String[] args) {
		
		// 热部署测试代码
		Thread t = new Thread(new Multirun());
		t.start();
		
		while (true) {
			BufferedReader br = null;
			try {
				System.out.println("请输入：");
				br = new BufferedReader(new InputStreamReader(System.in));
				String inputStr = br.readLine();
				if (inputStr.equals("hotswap")) {
					// 每次都创建出一个新的类加载器
					// class需要放在自己package名字的文件夹下
					String url = System.getProperty("user.dir")+"/bin";
					MyClassLoader loader = new MyClassLoader(url, classStrMap.values().toArray(new String[]{}));
					Iterator<Integer> iter = classStrMap.keySet().iterator();
					while(iter.hasNext()){
						int clsKey = iter.next();
						String clsStr = classStrMap.get(clsKey);
						Class cls = loader.loadClass(clsStr);
						Object foo = cls.newInstance();
						classObjMap.put(clsKey, foo);
					}
					isHotswap = true;
					System.out.println("----热更完成----");
				} else if (inputStr.equals("exit")) {
					System.out.println("----退出jvm----");
					System.exit(1);
				}
			} catch (Exception e) {
				System.err.println("readline err." + e);
				return;
			}
		}
	}
	
	
	/*
	 * 每隔2000ms运行一次，不断加载class
	 */
	static class Multirun implements Runnable {
		@SuppressWarnings("rawtypes")
		public void run() {
			try {
				while (true) {
					for(Object foo : classObjMap.values()){
						// 被调用函数的参数
						Method m = foo.getClass().getMethod("p", new Class[] {});
						m.invoke(foo, new Object[] {});
					}
					Thread.sleep(5000);
					TestBeanImpl.getInstance().p();
					//new TestBeanImpl().p();
					//System.out.println("--------");
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}

/**
 * 实现热部署，自定义ClassLoader，加载的是.class
 */
class MyClassLoader extends ClassLoader {
	private String basedir; // 需要该类加载器直接加载的类文件的基目录
	private HashSet<String> dynaclazns; // 需要由该类加载器直接加载的类名

	public MyClassLoader(String basedir, String[] clazns) {
		super(null); // 指定父类加载器为 null
		this.basedir = basedir;
		dynaclazns = new HashSet<String>();
		loadClassByMe(clazns);
	}

	private void loadClassByMe(String[] clazns) {
		for (int i = 0; i < clazns.length; i++) {
			loadDirectly(clazns[i]);
			dynaclazns.add(clazns[i]);
		}
	}

	@SuppressWarnings("rawtypes")
	private Class loadDirectly(String name) {
		Class cls = null;
		StringBuffer sb = new StringBuffer(basedir);
		String classname = name.replace('.', File.separatorChar) + ".class";
		sb.append(File.separator + classname);
		File classF = new File(sb.toString());
		try {
			cls = findClass(name, new FileInputStream(classF), classF.length());
			System.out.println("load "+cls.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return cls;
	}

	@SuppressWarnings("rawtypes")
	private Class findClass(String name, InputStream fin, long len) {
		byte[] raw = new byte[(int) len];
		try {
			fin.read(raw);
			fin.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//将一个 byte 数组转换为 Class 类的实例
		return defineClass(name, raw, 0, raw.length);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
		Class cls = null;
		cls = findLoadedClass(name);
		if (!this.dynaclazns.contains(name) && cls == null)
			cls = getSystemClassLoader().loadClass(name);
		if (cls == null)
			throw new ClassNotFoundException(name);
		if (resolve)
			resolveClass(cls);
		return cls;
	}
}

