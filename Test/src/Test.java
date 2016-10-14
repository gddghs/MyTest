import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import classloader.te.BaseClassLoader;



/**
* @author tangjifu
* @version 2015年11月5日
*/
public class Test {
	private long tiem;
	public long getTiem() {
		return tiem;
	}

	public void setTiem(long tiem) {
		this.tiem = tiem;
	}

	private static List<Integer> list = new ArrayList<>();
	private static TreeMap<Integer, Integer> tree = new TreeMap<>();
	private static HashMap<Integer, Integer> map = new HashMap<>();
	private static ConcurrentHashMap<Integer, Integer> chmap = new ConcurrentHashMap<Integer, Integer>();
	private static ConcurrentSkipListMap<Integer, Integer> sikpMap = new ConcurrentSkipListMap<>();
	private static ConcurrentSkipListSet<Integer> sikpSet = new ConcurrentSkipListSet<>();
	private static ReadWriteLock lock = new ReentrantReadWriteLock();
	private static Queue<Integer> queue = new LinkedList<>();
	
	
	public static void main(String [] args){
		/*ByteBuffer buff = ByteBuffer.allocate(4);
		String str = "abcd";
		buff.put(str.getBytes());
		buff.flip();
		byte [] bytearr = new byte[buff.remaining()];
		buff.get(bytearr);
		System.out.println(new String(bytearr));*/
		
		
	}
	
	public void stateControl(){
		for(int i=0;i<5;i++){
			int s = 1<<i;
			list.add(s);
			System.out.println("状态："+s);
		}
		for(int i = 0; i<10;i++){
			String str = "";
			for(int s:list){
				if( i == (i&s))
					str += s;
			}
			System.out.println("类型"+i+":"+str);
		}
	}
	
	public void testmaplist(){
		for(int i=0; i<10;i++){
			map.put(i, i*10);
			list.add(i);
		}
		Iterator<Integer> iter = map.keySet().iterator();
		while(iter.hasNext()){
			int key = iter.next();
			if(key == 1)
				map.put(key, 222);
			if(key == 2)
				map.remove(key);
				iter.remove();
		}
//		for(int i=0;i<map.size();i++){
//			if(map.get(i)==10)
//				map.remove(i);
//		}
		System.out.println(map);
		
//		int size = list.size();
//		for(int i=0; i<size; i++){
//			if(list.get(i) == 5){
//				list.remove(i);
//				System.out.println(list);
//			}
//		}
		for(Integer i:list){
			if(i == 5)
				list.remove(i);
		}
		System.out.println("xxx"+list);
	}
	
	public static void classLoader(){
		String className = "classloader.te.TestClassLoader";
		try {
			BaseClassLoader b = (BaseClassLoader) Class.forName(className).newInstance();
			b.start();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void testQueue(){
		try{
			LinkedBlockingQueue<Test> queue = new LinkedBlockingQueue<>();
			Test t = new Test();
			int currTime = (int)(System.currentTimeMillis()/1000);
			t.setTiem(currTime);
			queue.add(t);
			for(int i=0; i<5; i++){
				queue.add(t);
				System.out.println("+++++++++"+t.getTiem());
				Thread.sleep(1000);
			}
			System.out.println(queue.size());
			for(int i=0; i<queue.size(); i++){
				System.out.println(queue.take().getTiem());
				Thread.sleep(1000);
			}
		}catch(Exception e){
			
		}
	}
	
	public static void getlength(){
		String s = "我";
		byte[] b = null;
		try {
			b = s.getBytes();
			System.out.println(b.length);//3
			b = s.getBytes("UTF-8");
			System.out.println(b.length);//3
			b = s.getBytes("ISO8859-1");
			System.out.println(b.length);//1
			b = s.getBytes("UTF-16");
			System.out.println(b.length);//4
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	//测试请求
	public static void testRequest(){
		for(int i=0; i<10000; i++){
			handle(i);
		}
	}
	
	public static void handle(int i){
		Object obj = new Object();
		System.out.println("来了："+i);
		synchronized (obj) {
			try {
				Thread.sleep(10000);
				System.out.println("处理请求："+i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
