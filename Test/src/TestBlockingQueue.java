import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 * 阻塞队列-生产者消费者
* @author tangjifu
* @version 2015年12月29日
*/
public class TestBlockingQueue {

	private static BlockingQueue<Thread> taskQueue = new ArrayBlockingQueue<Thread>(5);
	
	public static void main(String [] args){
		new Thread(new Runnable() {
			public void run() {
				int i=0;
				while(true){
					if(i%2==0)
						acceptTask(new Task((++i)));
					else
						acceptTask(new Task2((++i)));
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
			System.out.println("-----获取["+t.getName()+"]准备执行");
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
			System.out.println("++接收["+t.getName()+"]成功.");
		else
			System.out.println("??接收["+t.getName()+"]失败.");
	}
	
	
}

class Task extends Thread{
	private static List<String> list = null;
	public Task(int taskId){
		super.setName("A"+taskId);
	}
	
	public static void handle(){
		System.out.println(list.get(1));
	}
	
	
	@Override
	public void run() {
		System.out.println("========开始执行["+getName()+"]");
		try {
			Thread.sleep(10000);
			handle();
		} catch (Exception e) {
			System.out.println(getName()+"异常:"+e);
			return;
		}
		System.out.println("========完成["+getName()+"]");
	}
}

class Task2 extends Thread{
	public Task2(int taskId){
		super.setName("B"+taskId);
	}
	
	@Override
	public void run() {
		System.out.println("========开始执行["+getName()+"]");
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			System.out.println(getName()+"异常:"+e);
			return;
		}
		System.out.println("========完成["+getName()+"]");
	}
}
