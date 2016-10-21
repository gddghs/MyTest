import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 阻塞队列-生产者消费者
* @author tangjifu
* @version 2015年12月29日
*/
public class TestBlockingQueue {
	private static final Logger logger = LoggerFactory.getLogger(TestBlockingQueue.class);
	private static final TestBlockingQueue instance = new TestBlockingQueue();
	
	private static BlockingQueue<Thread> taskQueue = new ArrayBlockingQueue<Thread>(5);
	
	public static void main(String [] args){
		
		try {
			LogServer.initLogBack();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//接收任务线程
		new Thread(new Runnable() {
			public void run() {
				int i=0;
				while(true){
					if(i%2==0)
						acceptTask(instance.new TaskA((++i)));
					else
						acceptTask(instance.new TaskB((++i)));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			}
		}).start();
		
		//执行任务线程
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
			logger.info("-----获取["+t.getName()+"]准备执行");
			t.run();
		} catch (InterruptedException e) {
		}
	}
	
	private static void acceptTask(Thread t){
		if(taskQueue.offer(t))
			logger.info("++接收["+t.getName()+"]成功.");
		else
			logger.info("??接收["+t.getName()+"]失败.");
	}
	
	
	class TaskA extends Thread{
		public TaskA(int taskId){
			super.setName("A"+taskId);
		}
		
		
		@Override
		public void run() {
			logger.info("========开始执行["+getName()+"]");
			try {
				Thread.sleep(10000);
			} catch (Exception e) {
				logger.error(getName()+"异常:", e);
				return;
			}
			logger.info("========完成["+getName()+"]");
		}
	}
	
	
	class TaskB extends Thread{
		public TaskB(int taskId){
			super.setName("B"+taskId);
		}
		
		@Override
		public void run() {
			logger.info("========开始执行["+getName()+"]");
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				logger.error(getName()+"异常:", e);
				return;
			}
			logger.info("========完成["+getName()+"]");
		}
	}
}


