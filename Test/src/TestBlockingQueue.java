import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
* @author tangjifu
* @version 2015年12月29日
*/
public class TestBlockingQueue {

	private static BlockingQueue<Thread> taskQueue = new ArrayBlockingQueue<Thread>(10);
	
	public static void main(String [] args){
		new Thread(new Runnable() {
			public void run() {
				int i=0;
				while(true){
					if(i%2==0)
						acceptTask(new Task((++i)+""));
					else
						acceptTask(new Task2((++i)+"_222"));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					execute();
				}
			}
		}).start();
	}
	
	private static void execute(){
		try {
			Thread t = taskQueue.take();
			System.out.println("-----获取task准备执行"+t.getName());
			t.run();
		} catch (InterruptedException e) {
		}
	}
	
	
	
	private static void acceptTask(Thread t){
		/*try {
			taskQueue.put(t);
			System.out.println("queue size:"+taskQueue.size());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		if(taskQueue.offer(t))
			System.out.println("队列接收任务succ."+t.getName());
		else
			System.out.println("队列接收任务失败."+t.getName());
	}
	
	
}

class Task extends Thread{
	private static List<String> list = null;
	public Task(String name){
		super.setName(name);
	}
	
	public static void handle(){
		System.out.println(list.get(1));
	}
	
	
	@Override
	public void run() {
		System.out.println("+++++开始执行task "+getName());
		try {
			Thread.sleep(10000);
			handle();
		} catch (Exception e) {
			System.out.println("exception:"+e);
			return;
		}
		System.out.println("+++++task "+getName()+"执行结束");
	}
}

class Task2 extends Thread{
	private static List<String> list = null;
	public Task2(String name){
		super.setName(name);
	}
	
	@Override
	public void run() {
		System.out.println("+++++开始执行task2222 "+getName());
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println("exception222:"+e);
			return;
		}
		System.out.println("+++++task2222 "+getName()+"执行结束");
	}
}
